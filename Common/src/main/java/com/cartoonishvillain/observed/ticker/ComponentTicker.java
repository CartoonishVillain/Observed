package com.cartoonishvillain.observed.ticker;

import com.cartoonishvillain.observed.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;

import java.util.Random;

public class ComponentTicker {
    public static void tickObservation(ServerPlayer player){
        float observe = (float) Services.PLATFORM.getValue(player);
        if(!player.level.isClientSide && player.tickCount % 20 == 0){

            float levelRemoved;
            if(observe >= 60){levelRemoved = (float) -Services.PLATFORM.highValueDrainRate();}
            else if (observe >= 20){levelRemoved = (float) -Services.PLATFORM.mediumValueDrainRate();}
            else {levelRemoved = (float) -Services.PLATFORM.lowValueDrainRate();}

            if(ValidPlayer(player)){
                Services.PLATFORM.addValue(player, levelRemoved);
            }

            if(observe >= 10 && ValidPlayer(player)){
                player.addEffect(new MobEffectInstance(Services.PLATFORM.getObservedEffect(), 1000000, 0, true, false, true));
            }else if(player.hasEffect(Services.PLATFORM.getObservedEffect()) && player.getEffect(Services.PLATFORM.getObservedEffect()).getAmplifier() == 0){player.removeEffect(Services.PLATFORM.getObservedEffect());}

            if(observe >= 50 && ValidPlayer(player)){
                player.addEffect(new MobEffectInstance(Services.PLATFORM.getObservedEffect(), 1000000, 1, true, false, true));
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 1000000, 0, true, false, true));
            }else{
                if(player.hasEffect(MobEffects.HUNGER) && player.getEffect(MobEffects.HUNGER).getDuration() > 12000){player.removeEffect(MobEffects.HUNGER);}
                if(player.hasEffect(Services.PLATFORM.getObservedEffect()) && player.getEffect(Services.PLATFORM.getObservedEffect()).getAmplifier() == 1){player.removeEffect(Services.PLATFORM.getObservedEffect());}
            }

            if(observe >= 75 && ValidPlayer(player)){
                player.addEffect(new MobEffectInstance(Services.PLATFORM.getObservedEffect(), 1000000, 2, true, true));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 1000000, 0, true, true));
            }else{
                if(player.hasEffect(MobEffects.WEAKNESS) && player.getEffect(MobEffects.WEAKNESS).getDuration() > 12000){player.removeEffect(MobEffects.WEAKNESS);}
                if(player.hasEffect(Services.PLATFORM.getObservedEffect()) && player.getEffect(Services.PLATFORM.getObservedEffect()).getAmplifier() == 2){player.removeEffect(Services.PLATFORM.getObservedEffect());}
            }

            if (observe >= 90 && ValidPlayer(player)){
                player.addEffect(new MobEffectInstance(Services.PLATFORM.getObservedEffect(), 1000000, 3, true, true));
                player.addEffect(new MobEffectInstance(MobEffects.WITHER, 1000000, 1, true, true));
            } else{
                if(player.hasEffect(MobEffects.WITHER) && player.getEffect(MobEffects.WITHER).getDuration() > 12000){player.removeEffect(MobEffects.WITHER);}
                if(player.hasEffect(Services.PLATFORM.getObservedEffect()) && player.getEffect(Services.PLATFORM.getObservedEffect()).getAmplifier() == 3){player.removeEffect(Services.PLATFORM.getObservedEffect());}

            }
        }

    }

    public static boolean ValidPlayer(Player player){
        return !player.isCreative() && !player.isSpectator();
    }

    public static boolean spawnRules(EntityType<? extends Monster> p_21401_, LevelAccessor p_21402_, MobSpawnType p_21403_, BlockPos p_21404_, Random p_21405_){
        return p_21402_.canSeeSky(p_21404_) && Monster.checkMonsterSpawnRules(p_21401_, (ServerLevelAccessor) p_21402_, p_21403_, p_21404_, p_21405_);
    }
}
