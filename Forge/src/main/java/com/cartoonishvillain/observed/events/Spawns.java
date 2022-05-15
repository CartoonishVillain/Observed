package com.cartoonishvillain.observed.events;

import com.cartoonishvillain.observed.Constants;
import com.cartoonishvillain.observed.ForgeObserved;
import com.cartoonishvillain.observed.Register;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Spawns {
    @SubscribeEvent
    public static void NaturalSpawner(BiomeLoadingEvent event){
        MobSpawnSettings.SpawnerData spawners = new MobSpawnSettings.SpawnerData(Register.OBSERVER.get(), ForgeObserved.config.OBSERVERWEIGHT.get(), 1, 1);

        if(!(event.getCategory() == Biome.BiomeCategory.NETHER) && !(event.getCategory() == Biome.BiomeCategory.THEEND) && !(event.getCategory() == Biome.BiomeCategory.MUSHROOM)){
            event.getSpawns().addSpawn(MobCategory.MONSTER, spawners);
        }
    }

    public static void PlacementManager(){
        if(ForgeObserved.config.CAVEOBSERVERS.get()) {
            SpawnPlacements.register(Register.OBSERVER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        }else
            SpawnPlacements.register(Register.OBSERVER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Spawns::spawnRules);

    }

    public static boolean spawnRules(EntityType<? extends Monster> p_21401_, LevelAccessor p_21402_, MobSpawnType p_21403_, BlockPos p_21404_, Random p_21405_){
        return p_21402_.canSeeSky(p_21404_) && Monster.checkMonsterSpawnRules(p_21401_, (ServerLevelAccessor) p_21402_, p_21403_, p_21404_, p_21405_);
    }
}
