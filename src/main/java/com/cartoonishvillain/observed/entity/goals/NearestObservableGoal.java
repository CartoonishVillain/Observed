package com.cartoonishvillain.observed.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class NearestObservableGoal extends NearestAttackableTargetGoal {
    public NearestObservableGoal(MobEntity p_26053_, Class p_26054_, int p_26055_, boolean p_26056_, boolean p_26057_, @Nullable Predicate<LivingEntity> p_26058_) {
        super(p_26053_, p_26054_, p_26055_, p_26056_, p_26057_, p_26058_);
        this.targetConditions = this.targetConditions.allowUnseeable();
    }
}
