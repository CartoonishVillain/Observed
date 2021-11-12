package com.cartoonishvillain.observed.entity.goals;

import com.cartoonishvillain.observed.Observed;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;

import static com.cartoonishvillain.observed.Observed.RANGEBLOCKINGITEMS;

public class ObservationGoal extends RangedAttackGoal {
    public ObservationGoal(RangedAttackMob p_25768_, double p_25769_, int p_25770_, float p_25771_) {
        super(p_25768_, p_25769_, p_25770_, p_25771_);
    }


    @Override
    public void tick() {
        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);

        if(this.mob.tickCount % 20 == 0) {
            float targetDistance = this.target.distanceTo(this.mob);

            //move closer to keep observing if target is almost out of range
            double followRange = Observed.config.OBSERVERFOLLOWPOINT.get();
            float range = this.attackRadius;

            if(RANGEBLOCKINGITEMS.contains(this.target.getItemBySlot(EquipmentSlot.HEAD).getItem())) {
                followRange = followRange/2f;
                range = range/2f;
            }
            if(range - targetDistance < followRange && !this.mob.getNavigation().isInProgress())
            {this.mob.getNavigation().moveTo(this.target, 1);}
            //otherwise if we are close enough and are still moving, stop moving
            else if(range - targetDistance > followRange && this.mob.getNavigation().isInProgress()){
                this.mob.getNavigation().stop();
            }
        }

        if(--this.attackTime == 0){
            this.rangedAttackMob.performRangedAttack(this.target, 1);
            this.attackTime = attackIntervalMin;
        }else if (attackTime < 0) this.attackTime = attackIntervalMin;
    }
}
