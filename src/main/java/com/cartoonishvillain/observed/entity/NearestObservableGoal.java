package com.cartoonishvillain.observed.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class NearestObservableGoal extends NearestAttackableTargetGoal {
    public NearestObservableGoal(Mob p_26053_, Class p_26054_, int p_26055_, boolean p_26056_, boolean p_26057_, @Nullable Predicate<LivingEntity> p_26058_) {
        super(p_26053_, p_26054_, p_26055_, p_26056_, p_26057_, p_26058_);
        this.targetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(p_26058_).ignoreLineOfSight();
    }
}
