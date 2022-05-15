package com.cartoonishvillain.observed.entity;


import com.cartoonishvillain.observed.ObservedCommon;
import com.cartoonishvillain.observed.entity.goals.NearestObservableGoal;
import com.cartoonishvillain.observed.entity.goals.ObservationGoal;
import com.cartoonishvillain.observed.entity.goals.ObserverMovementGoal;
import com.cartoonishvillain.observed.platform.Services;
import com.cartoonishvillain.observed.ticker.ComponentTicker;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.cartoonishvillain.observed.ObservedCommon.RANGEBLOCKINGITEMS;

public class ObserverEntity extends Monster implements RangedAttackMob {


    protected Player target;
    protected BlockPos lastLoc;

    protected int PanicTicks = 0;

    public ObserverEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.2D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 10f, 1.0D, 1.2D, this::avoid));
        this.goalSelector.addGoal(2, new ObservationGoal(this, 1.25D, 20, Services.PLATFORM.observerRange()));
        this.goalSelector.addGoal(3, new ObserverMovementGoal<>(this));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1));
        this.targetSelector.addGoal(1, new NearestObservableGoal(this, Player.class, 16, false, false, this::shouldAttack));
    }

    private boolean avoid(@Nullable LivingEntity entity){
        return PanicTicks > 0;
    }

    public boolean shouldAttack(@Nullable LivingEntity entity){
        return entity instanceof Player && !(RANGEBLOCKINGITEMS.contains(entity.getItemBySlot(EquipmentSlot.HEAD).getItem()) && entity.distanceTo(this) > Services.PLATFORM.observerRange()/2f);
    }

    public static AttributeSupplier.Builder customAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20D).add(Attributes.MOVEMENT_SPEED, 0.4d).add(Attributes.ARMOR, 5);
    }

    @Override
    public void performRangedAttack(LivingEntity livingEntity, float f) {
        if(livingEntity instanceof Player) {
            affectPlayer((Player) livingEntity);
        }
    }

    private void affectPlayer(Player player){
        if(!player.level.isClientSide){
            ArrayList<Player> players = (ArrayList<Player>) player.level.getEntitiesOfClass(Player.class, player.getBoundingBox().inflate(5));
            players.remove(player);

            float distance = this.distanceTo(player);
            float effect;

            float range = Services.PLATFORM.observerRange();
            float distanceDivided = distance/range;

            if(distanceDivided <= 0.3){effect = (float) Services.PLATFORM.closeObserverGainRate();}
            else if(distanceDivided <= 0.6){effect = (float) Services.PLATFORM.nearButNotCloseObserverGainRate();}
            else {effect = (float) Services.PLATFORM.farObserverGainRate();}

            boolean calyxCheck = ObservedCommon.isCalyxLoaded;

            AtomicBoolean protectedByCalyx = new AtomicBoolean(false);
            if(calyxCheck){
                    if(Services.PLATFORM.getImmortuosInfectionValue((ServerPlayer) player) > 25){
                        protectedByCalyx.set(true);
                    }
            }

            if(ComponentTicker.ValidPlayer(player) && ! protectedByCalyx.get()){
                Services.PLATFORM.addValue((ServerPlayer) player, effect);
            }

            protectedByCalyx.set(false);
            for (Player sideEffected : players){
                if(calyxCheck){
                    if(Services.PLATFORM.getImmortuosInfectionValue((ServerPlayer) player) > 25){
                        protectedByCalyx.set(true);
                    }
                }

                if(ComponentTicker.ValidPlayer(sideEffected) && !protectedByCalyx.get()) {
                    Services.PLATFORM.addValue((ServerPlayer) player, effect / 2f);
                }
                protectedByCalyx.set(false);
            }
            player.level.playSound(null, getOnPos(), Services.PLATFORM.getObserverAttackSound(), SoundSource.HOSTILE, 1, 1);

        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return Services.PLATFORM.getObserverHurtSound();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Services.PLATFORM.getObserverDeathSound();
    }

    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {

        if(!this.level.isClientSide() && PanicTicks < 20) {
            AreaEffectCloud areaEffectCloud = new AreaEffectCloud(EntityType.AREA_EFFECT_CLOUD, this.level);
            areaEffectCloud.setPotion(Potions.STRONG_POISON);
            areaEffectCloud.setDuration(80);
            areaEffectCloud.setRadius(3);
            areaEffectCloud.setRadiusPerTick(0.05f);
            areaEffectCloud.moveTo(this.getX(), this.getY(), this.getZ());
            this.level.addFreshEntity(areaEffectCloud);
        }
        PanicTicks = 100;
        return super.hurt(p_21016_, p_21017_);
    }

    @Override
    public void tick() {
        if(hasEffect(MobEffects.POISON)) removeEffect(MobEffects.POISON);
        super.tick();
        if(getTarget() != null && getTarget() instanceof Player) target = (Player) getTarget();

        if(getTarget() == null && target != null) {lastLoc = target.getOnPos(); target = null;}

        if(getTarget() != null && lastLoc != null) resetLastLoc();

        if(PanicTicks > 0) PanicTicks--;

    }

    public BlockPos getLastLoc(){
        return lastLoc;
    }

    public void resetLastLoc(){
        lastLoc = null;
    }
}
