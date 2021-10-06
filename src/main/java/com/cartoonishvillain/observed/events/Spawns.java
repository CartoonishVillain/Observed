package com.cartoonishvillain.observed.events;

import com.cartoonishvillain.observed.Observed;
import com.cartoonishvillain.observed.Register;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Observed.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Spawns {
    @SubscribeEvent
    public static void NaturalSpawner(BiomeLoadingEvent event){
        MobSpawnSettings.SpawnerData spawners = new MobSpawnSettings.SpawnerData(Register.OBSERVER.get(), 6, 1, 1);

        if(!(event.getCategory() == Biome.BiomeCategory.NETHER) && !(event.getCategory() == Biome.BiomeCategory.THEEND)){
            event.getSpawns().addSpawn(MobCategory.MONSTER, spawners);
        }
    }

    public static void PlacementManager(){
        SpawnPlacements.register(Register.OBSERVER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);

    }
}
