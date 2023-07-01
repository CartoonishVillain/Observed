package com.cartoonishvillain.observed.events;

import com.cartoonishvillain.observed.*;
import com.cartoonishvillain.observed.capabilities.IPlayerCapability;
import com.cartoonishvillain.observed.capabilities.PlayerCapability;
import com.cartoonishvillain.observed.entity.ObserverEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import static com.cartoonishvillain.observed.Register.*;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEvents {

    @SubscribeEvent
    public static void interModComms(InterModEnqueueEvent e){
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());
    }

    @SubscribeEvent
    public static void Attributes(final EntityAttributeCreationEvent event){
        event.put(Register.OBSERVER.get(), ObserverEntity.customAttributes().build());
    }

    @SubscribeEvent
    public static void effect(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.ENTITY_TYPES, helper -> {
            SpawnPlacements.register(Register.OBSERVER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Spawns::caveSpawnRules);
        });
    }

    @SubscribeEvent
    public static void CapabilityRegister(final RegisterCapabilitiesEvent event){
        event.register(IPlayerCapability.class);
        PlayerCapability.INSTANCE = CapabilityManager.get(new CapabilityToken<IPlayerCapability>() {});
    }

    @SubscribeEvent
    public static void registerTabs(BuildCreativeModeTabContentsEvent buildContents) {
        if (buildContents.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            buildContents.accept(OBSERVEREYE);
        }

        if (buildContents.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            buildContents.accept(LENS);
        }

        if (buildContents.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            buildContents.accept(OBSERVERSPAWN);
        }
    }
}
