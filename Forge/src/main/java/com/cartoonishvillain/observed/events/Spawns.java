package com.cartoonishvillain.observed.events;

import com.cartoonishvillain.observed.Constants;
import com.cartoonishvillain.observed.ForgeObserved;
import com.cartoonishvillain.observed.ModdedBiomeTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Spawns {

    public record SpawnModifiers(HolderSet<Biome> biomes, MobSpawnSettings.SpawnerData spawn) implements BiomeModifier {
        @Override
        public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if (phase == Phase.ADD && this.biomes.contains(biome)) {
                builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER, this.spawn);
                //Logging statement, in a vague attempt to see if anything shows up.
            }
        }

        @Override
        public Codec<? extends BiomeModifier> codec() {
            return ForgeObserved.SPAWNCODEC.get();
        }
    }

//    public static void PlacementManager(){
//        if(ForgeObserved.config.CAVEOBSERVERS.get()) {
//            SpawnPlacements.register(Register.OBSERVER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
//        }else
//            SpawnPlacements.register(Register.OBSERVER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Spawns::spawnRules);
//
//    }

    public static boolean spawnRules(EntityType<? extends Monster> p_21401_, LevelAccessor p_21402_, MobSpawnType p_21403_, BlockPos p_21404_, RandomSource p_21405_){
        return p_21402_.canSeeSky(p_21404_) && Monster.checkMonsterSpawnRules(p_21401_, (ServerLevelAccessor) p_21402_, p_21403_, p_21404_, p_21405_) && !p_21402_.getBiome(p_21404_).is(ModdedBiomeTags.MushroomBiomes);
    }

    public static boolean caveSpawnRules(EntityType<? extends Monster> p_21401_, LevelAccessor p_21402_, MobSpawnType p_21403_, BlockPos p_21404_, RandomSource p_21405_){
        return Monster.checkMonsterSpawnRules(p_21401_, (ServerLevelAccessor) p_21402_, p_21403_, p_21404_, p_21405_) && !p_21402_.getBiome(p_21404_).is(ModdedBiomeTags.MushroomBiomes);
    }
}
