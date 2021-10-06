package com.cartoonishvillain.observed.entity;

import com.cartoonishvillain.observed.Register;
import com.cartoonishvillain.observed.capabilities.PlayerCapability;
import com.cartoonishvillain.observed.entity.goals.NearestObservableGoal;
import com.cartoonishvillain.observed.entity.goals.ObservationGoal;
import com.cartoonishvillain.observed.entity.goals.ObserverMovementGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class ObserverEntity extends Monster implements RangedAttackMob {
    public ObserverEntity(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    protected Player target;
    protected BlockPos lastLoc;

    protected int PanicTicks = 0;


    public static AttributeSupplier.Builder customAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20D).add(Attributes.MOVEMENT_SPEED, 0.4d).add(Attributes.ARMOR, 5);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 10f, 1.0D, 1.2D, this::avoid ));
        this.goalSelector.addGoal(2, new ObservationGoal(this, 1.25D, 20, 20));
        this.goalSelector.addGoal(3, new ObserverMovementGoal<>(this));
        this.targetSelector.addGoal(1, new NearestObservableGoal(this, Player.class, 16, false, false, this::shouldAttack));
    }

    private boolean avoid(@Nullable LivingEntity entity){
        return PanicTicks > 0;
    }

    public boolean shouldAttack(@Nullable LivingEntity entity){
        return entity != null;
    }

    @Override
    public void performRangedAttack(LivingEntity p_33317_, float p_33318_) {
        if(p_33317_ instanceof Player) {
            affectPlayer((Player) p_33317_);
        }
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

    public BlockPos getLastLoc(){
        return lastLoc;
    }

    public void resetLastLoc(){
        lastLoc = null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return Register.OBSERVERHURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Register.OBSERVERDEATH.get();
    }

    private void affectPlayer(Player player){
        ArrayList<Player> players = (ArrayList<Player>) player.level.getEntitiesOfClass(Player.class, player.getBoundingBox().inflate(5));
        players.remove(player);

        float distance = this.distanceTo(player);
        float effect;

        if(distance <= 5){effect = 1;}
        else if(distance <= 10){effect = 0.5f;}
        else {effect = 0.25f;}

        //TODO: IMMORTUOS CHECK

        player.getCapability(PlayerCapability.INSTANCE).ifPresent(h->{
            h.changeObserveLevel(effect);
        });

        for (Player sideEffected : players){
            //TODO: IMMORTUOS CHECK
            sideEffected.getCapability(PlayerCapability.INSTANCE).ifPresent(h->{
                h.changeObserveLevel(effect/2f);
            });
        }

        if(!player.level.isClientSide()){
            player.level.playSound(null, getOnPos(), Register.OBSERVERATTACK.get(), SoundSource.HOSTILE, 1, 1);
        }

    }
}
