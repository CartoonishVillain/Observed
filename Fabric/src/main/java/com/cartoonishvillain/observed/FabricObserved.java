package com.cartoonishvillain.observed;

import com.cartoonishvillain.observed.commands.SetObservedLevel;
import com.cartoonishvillain.observed.config.ObservedConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

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

		AutoConfig.register(ObservedConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(ObservedConfig.class).getConfig();

		CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> {
			SetObservedLevel.register(dispatcher);
		}));

		Register.init();

		SpawnSystem.initSpawns();
	}
}
