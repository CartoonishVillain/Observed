package com.cartoonishvillain.observed.events;

import com.cartoonishvillain.observed.ObserveEffect;
import com.cartoonishvillain.observed.Observed;
import com.cartoonishvillain.observed.Register;
import com.cartoonishvillain.observed.capabilities.IPlayerCapability;
import com.cartoonishvillain.observed.capabilities.PlayerCapability;
import com.cartoonishvillain.observed.entity.ObserverEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
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

    @SubscribeEvent
    public static void entityRegister(final RegistryEvent.Register<EntityType<?>> event){
        Spawns.PlacementManager();
    }


    @SubscribeEvent
    public static void CapabilityRegister(final RegisterCapabilitiesEvent event){
        event.register(IPlayerCapability.class);

        PlayerCapability.INSTANCE = CapabilityManager.get(new CapabilityToken<IPlayerCapability>() {});
    }
}
