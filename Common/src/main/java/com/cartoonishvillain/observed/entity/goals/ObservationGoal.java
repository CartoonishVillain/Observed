package com.cartoonishvillain.observed.entity.goals;

import com.cartoonishvillain.observed.mixin.ObserverAccessor;
import com.cartoonishvillain.observed.platform.Services;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;

import static com.cartoonishvillain.observed.ObservedCommon.RANGEBLOCKINGITEMS;

public class ObservationGoal extends RangedAttackGoal {
    int newAttackTime = 0;
    public ObservationGoal(RangedAttackMob p_25768_, double p_25769_, int p_25770_, float p_25771_) {
        super(p_25768_, p_25769_, p_25770_, p_25771_);

    }

    @Override
    public void tick() {
        ((ObserverAccessor) this).Observedgetmob().getLookControl().setLookAt(((ObserverAccessor) this).Observedgettarget(), 30.0F, 30.0F);

        if(((ObserverAccessor) this).Observedgetmob().tickCount % 20 == 0) {
            float targetDistance = ((ObserverAccessor) this).Observedgettarget().distanceTo(((ObserverAccessor) this).Observedgetmob());
            //move closer to keep observing if target is almost out of range
            double followRange = Services.PLATFORM.observerFollowDistance();
            float range = ((ObserverAccessor)this).ObservedgetattackRadius();

            if(RANGEBLOCKINGITEMS.contains(((ObserverAccessor)this).Observedgettarget().getItemBySlot(EquipmentSlot.HEAD).getItem())) {
                followRange = followRange/2f;
                range = range/2f;
            }
            if(range - targetDistance < followRange && !((ObserverAccessor) this).Observedgetmob().getNavigation().isInProgress())
            {((ObserverAccessor) this).Observedgetmob().getNavigation().moveTo(((ObserverAccessor) this).Observedgettarget(), 1);}
            //otherwise if we are close enough and are still moving, stop moving
            else if(((ObserverAccessor) this).ObservedgetattackRadius() - targetDistance > Services.PLATFORM.observerFollowDistance() && ((ObserverAccessor) this).Observedgetmob().getNavigation().isInProgress()){
                ((ObserverAccessor) this).Observedgetmob().getNavigation().stop();
            }
        }

        if(--newAttackTime == 0){
            ((ObserverAccessor) this).ObservedgetrangedAttackMob().performRangedAttack(((ObserverAccessor) this).Observedgettarget(), 1);
            this.newAttackTime = ((ObserverAccessor) this).ObservedgetattackIntervalMin();
        }else if (newAttackTime < 0) this.newAttackTime = ((ObserverAccessor) this).ObservedgetattackIntervalMin();
    }
}
