package com.cartoonishvillain.observed.entity;

import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class ObservationGoal extends RangedAttackGoal {
    public ObservationGoal(RangedAttackMob p_25768_, double p_25769_, int p_25770_, float p_25771_) {
        super(p_25768_, p_25769_, p_25770_, p_25771_);
    }


    @Override
    public void tick() {
        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
        if(--this.attackTime == 0){
            this.rangedAttackMob.performRangedAttack(this.target, 1);
            this.attackTime = attackIntervalMin;
        }else if (attackTime < 0) this.attackTime = attackIntervalMin;
    }
}
