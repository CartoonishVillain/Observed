package com.cartoonishvillain.observed;

import com.cartoonishvillain.observed.config.ObservedConfig;
import com.cartoonishvillain.observed.ticker.ComponentTicker;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Predicate;

public class SpawnSystem {

    private static final ObservedConfig config = FabricObserved.config;

    public static void initSpawns(){
        Predicate<BiomeSelectionContext> spawnPredicate = overWorldNoGoZones();
        BiomeModifications.addSpawn(spawnPredicate, MobCategory.MONSTER, Register.OBSERVERENTITY, config.observedOptions.observerSpawnWeight, 1, 1);
        if(!config.observedOptions.observersSpawnInCaves)
            SpawnRestrictionAccessor.callRegister(Register.OBSERVERENTITY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ComponentTicker::spawnRules);
        else
            SpawnRestrictionAccessor.callRegister(Register.OBSERVERENTITY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,  Monster::checkMonsterSpawnRules);
    }

    public static Predicate<BiomeSelectionContext> overWorldNoGoZones() {
        return BiomeSelectors.tag(BiomeTags.IS_OVERWORLD).and(shroomExclusion());
    }

    public static Predicate<BiomeSelectionContext> shroomExclusion() {
        return Predicate.not(BiomeSelectors.tag(ModdedBiomeTags.MushroomBiomes));
    }

}
