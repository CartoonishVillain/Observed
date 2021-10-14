package com.cartoonishvillain.observed.events;

import com.cartoonishvillain.ImmortuosCalyx.Infection.InfectionManagerCapability;
import com.cartoonishvillain.observed.ObserveEffect;
import com.cartoonishvillain.observed.Observed;
import com.cartoonishvillain.observed.Register;
import com.cartoonishvillain.observed.capabilities.PlayerCapability;
import com.cartoonishvillain.observed.capabilities.PlayerCapabilityManager;
import com.cartoonishvillain.observed.commands.SetObservedLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Observed.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeBusEvents {


    @SubscribeEvent
    public static void commands(RegisterCommandsEvent event){
        SetObservedLevel.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void playerRegister(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof Player) {
            PlayerCapabilityManager provider = new PlayerCapabilityManager();
            event.addCapability(new ResourceLocation(Observed.MODID, "observationlevels"), provider);
        }
    }


    @SubscribeEvent
    public static void playerObserveTick(TickEvent.PlayerTickEvent event){
        if(!event.player.level.isClientSide() && event.phase.equals(TickEvent.Phase.END) && event.player.tickCount % 20 == 0){
            event.player.getCapability(PlayerCapability.INSTANCE).ifPresent(h->{
                float levelRemoved;
                if(h.getObserveLevel() >= 60){levelRemoved = Observed.config.HIGHDRAINRATE.get().floatValue() * -1f;}
                else if(h.getObserveLevel() >= 20){levelRemoved = Observed.config.MIDDRAINRATE.get().floatValue() * -1f;}
                else {levelRemoved = Observed.config.LOWDRAINRATE.get().floatValue() * -1f;}
                h.changeObserveLevel(levelRemoved);



                if(h.getObserveLevel() >= 10){
                    event.player.addEffect(new MobEffectInstance(ObserveEffect.observed, 1000000, 0, !Observed.config.EFFECTPARTICLES.get(), Observed.config.EFFECTPARTICLES.get(), true));
                }else if(event.player.hasEffect(ObserveEffect.observed) && event.player.getEffect(ObserveEffect.observed).getAmplifier() == 0){event.player.removeEffect(ObserveEffect.observed);}


                if(h.getObserveLevel() >= 50){
                    event.player.addEffect(new MobEffectInstance(ObserveEffect.observed, 1000000, 1, !Observed.config.EFFECTPARTICLES.get(), true));
                    event.player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 1000000, 0, !Observed.config.EFFECTPARTICLES.get(), true));
                }else{
                    if(event.player.hasEffect(MobEffects.HUNGER) && event.player.getEffect(MobEffects.HUNGER).getDuration() > 12000){event.player.removeEffect(MobEffects.HUNGER);}
                    if(event.player.hasEffect(ObserveEffect.observed) && event.player.getEffect(ObserveEffect.observed).getAmplifier() == 1){event.player.removeEffect(ObserveEffect.observed);}
                }

                if(h.getObserveLevel() >= 75){
                    event.player.addEffect(new MobEffectInstance(ObserveEffect.observed, 1000000, 2, !Observed.config.EFFECTPARTICLES.get(), true));
                    event.player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 1000000, 0, !Observed.config.EFFECTPARTICLES.get(), true));
                }else{
                    if(event.player.hasEffect(MobEffects.WEAKNESS) && event.player.getEffect(MobEffects.WEAKNESS).getDuration() > 12000){event.player.removeEffect(MobEffects.WEAKNESS);}
                    if(event.player.hasEffect(ObserveEffect.observed) && event.player.getEffect(ObserveEffect.observed).getAmplifier() == 2){event.player.removeEffect(ObserveEffect.observed);}
                }

                if (h.getObserveLevel() >= 90){
                    event.player.addEffect(new MobEffectInstance(ObserveEffect.observed, 1000000, 3, !Observed.config.EFFECTPARTICLES.get(), true));
                    event.player.addEffect(new MobEffectInstance(MobEffects.WITHER, 1000000, 1, !Observed.config.EFFECTPARTICLES.get(), true));
                } else{
                    if(event.player.hasEffect(MobEffects.WITHER) && event.player.getEffect(MobEffects.WITHER).getDuration() > 12000){event.player.removeEffect(MobEffects.WITHER);}
                    if(event.player.hasEffect(ObserveEffect.observed) && event.player.getEffect(ObserveEffect.observed).getAmplifier() == 3){event.player.removeEffect(ObserveEffect.observed);}

                }
            });
        }
    }

    @SubscribeEvent
    public static void eatEyeball(LivingEntityUseItemEvent.Finish event){
        if(!event.getEntityLiving().level.isClientSide() && event.getItem().getItem().equals(Register.OBSERVEREYE.get()) && Observed.isCalyxLoaded){
            int rand = event.getEntityLiving().getRandom().nextInt(10);
            if(rand == 5) {
            event.getEntityLiving().getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                    h.setInfectionProgressIfLower(1);
            });
        }
        }
    }
}
