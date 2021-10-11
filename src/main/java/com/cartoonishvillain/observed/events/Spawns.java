package com.cartoonishvillain.observed.events;

import com.cartoonishvillain.observed.Observed;
import com.cartoonishvillain.observed.Register;
import net.minecraft.entity.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

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
        if(Observed.config.CAVEOBSERVERS.get()){
            EntitySpawnPlacementRegistry.register(Register.OBSERVER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::checkMobSpawnRules);
        }
        else
        EntitySpawnPlacementRegistry.register(Register.OBSERVER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Spawns::spawnRules);

    }

    public static boolean spawnRules(EntityType<? extends MobEntity> p_223315_0_, IWorld p_223315_1_, SpawnReason p_223315_2_, BlockPos p_223315_3_, Random p_223315_4_){
        return p_223315_1_.canSeeSky(p_223315_3_) && MobEntity.checkMobSpawnRules(p_223315_0_, p_223315_1_, p_223315_2_, p_223315_3_, p_223315_4_);
    }
}
