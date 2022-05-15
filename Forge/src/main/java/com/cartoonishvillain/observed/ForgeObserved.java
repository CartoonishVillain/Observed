package com.cartoonishvillain.observed;

import com.cartoonishvillain.observed.capabilities.PlayerCapabilityManager;
import com.cartoonishvillain.observed.commands.SetObservedLevel;
import com.cartoonishvillain.observed.config.CommonConfig;
import com.cartoonishvillain.observed.config.ConfigHelper;
import com.cartoonishvillain.observed.events.Spawns;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class ForgeObserved {

    public static boolean tormentInstalled = false;
    public static CommonConfig config;
    
    public ForgeObserved() {
        Register.init();
        ObservedCommon.init();
        config = ConfigHelper.register(ModConfig.Type.COMMON, CommonConfig::new);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        tormentInstalled = (ModList.get().isLoaded("torment") && config.TORMENTCOMPAT.get());
        Spawns.PlacementManager();
    }
}