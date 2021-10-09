package com.cartoonishvillain.observed.entity;

import com.cartoonishvillain.ImmortuosCalyx.Infection.InfectionManagerCapability;
import com.cartoonishvillain.observed.Observed;
import com.cartoonishvillain.observed.Register;
import com.cartoonishvillain.observed.capabilities.PlayerCapability;
import com.cartoonishvillain.observed.entity.goals.NearestObservableGoal;
import com.cartoonishvillain.observed.entity.goals.ObservationGoal;
import com.cartoonishvillain.observed.entity.goals.ObserverMovementGoal;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potions;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ObserverEntity extends MonsterEntity implements IRangedAttackMob {
    public ObserverEntity(EntityType<? extends MonsterEntity> p_33002_, World p_33003_) {
        super(p_33002_, p_33003_);
    }

    protected PlayerEntity target;
    protected BlockPos lastLoc;

    protected int PanicTicks = 0;


    public static AttributeModifierMap.MutableAttribute customAttributes(){
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20D).add(Attributes.MOVEMENT_SPEED, 0.4d).add(Attributes.ARMOR, 5);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, PlayerEntity.class, 10f, 1.0D, 1.2D, this::avoid ));
        this.goalSelector.addGoal(2, new ObservationGoal(this, 1.25D, 20, 20));
        this.goalSelector.addGoal(3, new ObserverMovementGoal<>(this));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1));
        this.targetSelector.addGoal(1, new NearestObservableGoal(this, PlayerEntity.class, 16, false, false, this::shouldAttack));
    }

    private boolean avoid(@Nullable LivingEntity entity){
        return PanicTicks > 0;
    }

    public boolean shouldAttack(@Nullable LivingEntity entity){
        return entity != null;
    }


    @Override
    public void tick() {
        if(hasEffect(Effects.POISON)) removeEffect(Effects.POISON);
        super.tick();
        if(getTarget() != null && getTarget() instanceof PlayerEntity) target = (PlayerEntity) getTarget();

        if(getTarget() == null && target != null) {lastLoc = target.blockPosition(); target = null;}

        if(getTarget() != null && lastLoc != null) resetLastLoc();

        if(PanicTicks > 0) PanicTicks--;

    }

    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {

        if(!this.level.isClientSide() && PanicTicks < 20) {
            AreaEffectCloudEntity areaEffectCloud = new AreaEffectCloudEntity(EntityType.AREA_EFFECT_CLOUD, this.level);
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

    private void affectPlayer(PlayerEntity player){
        ArrayList<PlayerEntity> players = (ArrayList<PlayerEntity>) player.level.getEntitiesOfClass(PlayerEntity.class, player.getBoundingBox().inflate(5));
        players.remove(player);

        float distance = this.distanceTo(player);
        float effect;

        if(distance <= 5){effect = 1;}
        else if(distance <= 10){effect = 0.5f;}
        else {effect = 0.25f;}

        boolean calyxCheck = Observed.isCalyxLoaded;

        AtomicBoolean protectedByCalyx = new AtomicBoolean(false);

        if(calyxCheck){
            player.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                if(h.getInfectionProgress() > 25){
                    protectedByCalyx.set(true);
                }
            });
        }

        if(!protectedByCalyx.get()){
        player.getCapability(PlayerCapability.INSTANCE).ifPresent(h->{
            h.changeObserveLevel(effect);
        });}

        protectedByCalyx.set(false);
        for (PlayerEntity sideEffected : players){
            sideEffected.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                if(h.getInfectionProgress() > 25){
                    protectedByCalyx.set(true);
                }
            });
            if(!protectedByCalyx.get()) {
                sideEffected.getCapability(PlayerCapability.INSTANCE).ifPresent(h -> {
                    h.changeObserveLevel(effect / 2f);
                });
            }
            protectedByCalyx.set(false);
        }

        if(!player.level.isClientSide()){
            player.level.playSound(null, getOnPos(), Register.OBSERVERATTACK.get(), SoundCategory.HOSTILE, 1, 1);
        }

    }

    @Override
    public void performRangedAttack(LivingEntity p_33317_, float p_33318_) {
        if(p_33317_ instanceof PlayerEntity) {
            affectPlayer((PlayerEntity) p_33317_);
        }
    }
}
