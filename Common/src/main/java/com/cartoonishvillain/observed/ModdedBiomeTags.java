package com.cartoonishvillain.observed;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class ModdedBiomeTags {
    public static final TagKey<Biome> MushroomBiomes = TagKey.create(Registries.BIOME, new ResourceLocation(Constants.MOD_ID, "land_spawnable"));
}
