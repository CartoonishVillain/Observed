package com.cartoonishvillain.observed;

import com.cartoonishvillain.observed.capabilities.PlayerCapability;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("observed")
public class Observed
{
    // Directly reference a log4j logger.
    public static final String MODID = "observed";
    public static boolean isCalyxLoaded;
    private static final Logger LOGGER = LogManager.getLogger();

    public Observed() {
        // Register the setup method for modloading
        Register.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        PlayerCapability.register();
        isCalyxLoaded = ModList.get().isLoaded("immortuoscalyx");
    }

}
