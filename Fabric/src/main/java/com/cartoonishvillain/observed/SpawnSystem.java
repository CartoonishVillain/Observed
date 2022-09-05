package com.cartoonishvillain.observed;

import net.minecraft.world.entity.SpawnPlacements;
import com.cartoonishvillain.observed.ticker.ComponentTicker;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Predicate;

public class SpawnSystem {
    public static void initSpawns(){
        Predicate<BiomeSelectionContext> spawnPredicate = overWorldNoGoZones();
        BiomeModifications.addSpawn(spawnPredicate, MobCategory.MONSTER, Register.OBSERVERENTITY, FabricObserved.CONFIG.getOrDefault("observerSpawnWeight", 15), 1, 1);
        if(!FabricObserved.CONFIG.getOrDefault("observersSpawnInCaves", false))
            SpawnPlacements.register(Register.OBSERVERENTITY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ComponentTicker::spawnRules);
        else
            SpawnPlacements.register(Register.OBSERVERENTITY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,  Monster::checkMonsterSpawnRules);
    }

    public static Predicate<BiomeSelectionContext> overWorldNoGoZones() {
        return BiomeSelectors.tag(BiomeTags.IS_OVERWORLD).and(shroomExclusion());
    }

    public static Predicate<BiomeSelectionContext> shroomExclusion() {
        return Predicate.not(BiomeSelectors.tag(ModdedBiomeTags.MushroomBiomes));
    }

}
