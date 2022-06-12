package com.cartoonishvillain.observed;

import com.cartoonishvillain.observed.commands.SetObservedLevel;
import com.cartoonishvillain.observed.config.ObservedConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FabricObserved implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("observed");
	public static ObservedConfig config;
	public static String MODID = "observed";

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ObservedCommon.init();

		AutoConfig.register(ObservedConfig.class, JanksonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(ObservedConfig.class).getConfig();

		CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, dedicated) -> {
			SetObservedLevel.register(dispatcher);
		}));

		Register.init();

		SpawnSystem.initSpawns();
	}
}
