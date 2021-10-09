package com.cartoonishvillain.observed.entity.goals;

import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.goal.RangedAttackGoal;


public class ObservationGoal extends RangedAttackGoal {
    public ObservationGoal(IRangedAttackMob p_25768_, double p_25769_, int p_25770_, float p_25771_) {
        super(p_25768_, p_25769_, p_25770_, p_25771_);
    }


    @Override
    public void tick() {
        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);

        if(this.mob.tickCount % 20 == 0) {
            float targetDistance = this.target.distanceTo(this.mob);
            //move closer to keep observing if target is almost out of range
            if(this.attackRadius - targetDistance < 8.5 && !this.mob.getNavigation().isInProgress()){
            this.mob.getNavigation().moveTo(this.target, 1);}
            //otherwise if we are close enough and are still moving, stop moving
            else if(this.attackRadius - targetDistance > 8.5 && this.mob.getNavigation().isInProgress()){
                this.mob.getNavigation().stop();
            }
        }

        if(--this.attackTime == 0){
            this.rangedAttackMob.performRangedAttack(this.target, 1);
            this.attackTime = attackIntervalMin;
        }else if (attackTime < 0) this.attackTime = attackIntervalMin;
    }
}
