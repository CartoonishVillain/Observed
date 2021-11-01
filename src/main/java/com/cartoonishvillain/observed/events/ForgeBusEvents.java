package com.cartoonishvillain.observed.events;

import com.cartoonishvillain.ImmortuosCalyx.Infection.InfectionManagerCapability;
import com.cartoonishvillain.observed.ObserveEffect;
import com.cartoonishvillain.observed.Observed;
import com.cartoonishvillain.observed.Register;
import com.cartoonishvillain.observed.capabilities.PlayerCapability;
import com.cartoonishvillain.observed.capabilities.PlayerCapabilityManager;
import com.cartoonishvillain.observed.commands.SetObservedLevel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
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
        if(event.getObject() instanceof PlayerEntity) {
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

                if(ValidPlayer(event.player)) h.changeObserveLevel(levelRemoved);



                if(h.getObserveLevel() >= 10 && ValidPlayer(event.player)){
                    event.player.addEffect(new EffectInstance(ObserveEffect.observed, 1000000, 0, !Observed.config.EFFECTPARTICLES.get(), Observed.config.EFFECTPARTICLES.get(), true));
                }else if(event.player.hasEffect(ObserveEffect.observed) && event.player.getEffect(ObserveEffect.observed).getAmplifier() == 0){event.player.removeEffect(ObserveEffect.observed);}


                if(h.getObserveLevel() >= 50 && ValidPlayer(event.player)){
                    event.player.addEffect(new EffectInstance(ObserveEffect.observed, 1000000, 1, !Observed.config.EFFECTPARTICLES.get(), Observed.config.EFFECTPARTICLES.get(), true));
                    event.player.addEffect(new EffectInstance(Effects.HUNGER, 1000000, 0, !Observed.config.EFFECTPARTICLES.get(), Observed.config.EFFECTPARTICLES.get(), true));
                }else{
                    if(event.player.hasEffect(Effects.HUNGER) && event.player.getEffect(Effects.HUNGER).getDuration() > 12000){event.player.removeEffect(Effects.HUNGER);}
                    if(event.player.hasEffect(ObserveEffect.observed) && event.player.getEffect(ObserveEffect.observed).getAmplifier() == 1){event.player.removeEffect(ObserveEffect.observed);}
                }

                if(h.getObserveLevel() >= 75 && ValidPlayer(event.player)){
                    event.player.addEffect(new EffectInstance(ObserveEffect.observed, 1000000, 2, !Observed.config.EFFECTPARTICLES.get(), Observed.config.EFFECTPARTICLES.get(), true));
                    event.player.addEffect(new EffectInstance(Effects.WEAKNESS, 1000000, 0, !Observed.config.EFFECTPARTICLES.get(), Observed.config.EFFECTPARTICLES.get(), true));
                }else{
                    if(event.player.hasEffect(Effects.WEAKNESS) && event.player.getEffect(Effects.WEAKNESS).getDuration() > 12000){event.player.removeEffect(Effects.WEAKNESS);}
                    if(event.player.hasEffect(ObserveEffect.observed) && event.player.getEffect(ObserveEffect.observed).getAmplifier() == 2){event.player.removeEffect(ObserveEffect.observed);}
                }

                if (h.getObserveLevel() >= 90 && ValidPlayer(event.player)){
                    event.player.addEffect(new EffectInstance(ObserveEffect.observed, 1000000, 3, !Observed.config.EFFECTPARTICLES.get(), Observed.config.EFFECTPARTICLES.get(), true));
                    event.player.addEffect(new EffectInstance(Effects.WITHER, 1000000, 1, !Observed.config.EFFECTPARTICLES.get(), Observed.config.EFFECTPARTICLES.get(), true));
                } else{
                    if(event.player.hasEffect(Effects.WITHER) && event.player.getEffect(Effects.WITHER).getDuration() > 12000){event.player.removeEffect(Effects.WITHER);}
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


    public static boolean ValidPlayer(PlayerEntity player){
        return !player.isCreative() && !player.isSpectator();
    }
}
