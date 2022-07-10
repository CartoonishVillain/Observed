package com.cartoonishvillain.observed;

import com.cartoonishvillain.observed.config.CommonConfig;
import com.cartoonishvillain.observed.config.ConfigHelper;
import com.cartoonishvillain.observed.events.Spawns;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(Constants.MOD_ID)
public class ForgeObserved {

    public static boolean tormentInstalled = false;
    public static CommonConfig config;

    static DeferredRegister<Codec<? extends BiomeModifier>> serializers = DeferredRegister
            .create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Constants.MOD_ID);

    public static RegistryObject<Codec<Spawns.SpawnModifiers>> SPAWNCODEC = serializers.register("spawnmodifiers", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    // declare fields
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(Spawns.SpawnModifiers::biomes),
                    MobSpawnSettings.SpawnerData.CODEC.fieldOf("spawn").forGetter(Spawns.SpawnModifiers::spawn)
                    // declare constructor
            ).apply(builder, Spawns.SpawnModifiers::new)));
    
    public ForgeObserved() {
        ObservedCommon.init();
        config = ConfigHelper.register(ModConfig.Type.COMMON, CommonConfig::new);
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        serializers.register(modEventBus);
        ObserveEffect.init();
        Register.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        tormentInstalled = (ModList.get().isLoaded("torment") && config.TORMENTCOMPAT.get());
    }
}