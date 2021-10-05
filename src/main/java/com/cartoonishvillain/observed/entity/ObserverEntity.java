package com.cartoonishvillain.observed.entity;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class ObserverEntity extends Monster implements RangedAttackMob {
    public ObserverEntity(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    public static AttributeSupplier.Builder customAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.KNOCKBACK_RESISTANCE, 20D).add(Attributes.MAX_HEALTH, 20D).add(Attributes.MOVEMENT_SPEED, 0.5d).add(Attributes.ARMOR, 5);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new ObservationGoal(this, 1.25D, 20, 20));
        this.targetSelector.addGoal(1, new NearestObservableGoal(this, Player.class, 16, false, false, this::shouldAttack));
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

    private void affectPlayer(Player player){
        player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 10, 10));
    }
}
