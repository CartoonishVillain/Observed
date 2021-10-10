package com.cartoonishvillain.observed.events;

import com.cartoonishvillain.observed.Observed;
import com.cartoonishvillain.observed.Register;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.MobEntity;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Observed.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Spawns {
    @SubscribeEvent
    public static void NaturalSpawner(BiomeLoadingEvent event){
        MobSpawnInfo.Spawners spawners = new MobSpawnInfo.Spawners(Register.OBSERVER.get(), Observed.config.OBSERVERWEIGHT.get(), 1, 1);

        if(!(event.getCategory() == Biome.Category.NETHER) && !(event.getCategory() == Biome.Category.THEEND)){
            event.getSpawns().addSpawn(EntityClassification.MONSTER, spawners);
        }
    }

    public static void PlacementManager(){
        EntitySpawnPlacementRegistry.register(Register.OBSERVER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::checkMobSpawnRules);

    }
}
