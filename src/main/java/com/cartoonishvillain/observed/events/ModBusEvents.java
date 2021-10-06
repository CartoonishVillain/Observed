package com.cartoonishvillain.observed.events;

import com.cartoonishvillain.observed.ObserveEffect;
import com.cartoonishvillain.observed.Observed;
import com.cartoonishvillain.observed.Register;
import com.cartoonishvillain.observed.entity.ObserverEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Observed.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEvents {
    @SubscribeEvent
    public static void Attributes(final EntityAttributeCreationEvent event){
        event.put(Register.OBSERVER.get(), ObserverEntity.customAttributes().build());
    }

    @SubscribeEvent
    public static void effect(final RegistryEvent.Register<MobEffect> event){
        ObserveEffect.init();
    }
}
