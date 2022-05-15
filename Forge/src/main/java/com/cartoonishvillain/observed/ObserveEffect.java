package com.cartoonishvillain.observed;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.ForgeRegistries;

public class ObserveEffect {
    public static MobEffect observed;

    public static void init(){
        observed = new ModdedPotionEffects( MobEffectCategory.HARMFUL, 9606036, new ResourceLocation(Constants.MOD_ID, "observedeffect"));
    }

    /*
        While code reusage is minimal would like to shout out the immersive engineering team and BluSunrize for having such a neat license to help me get through this bit in particular
     */
    public static class ModdedPotionEffects extends MobEffect {

        protected ModdedPotionEffects(MobEffectCategory p_19451_, int p_19452_, ResourceLocation location) {
            super(p_19451_, p_19452_);
            ForgeRegistries.MOB_EFFECTS.register(this.setRegistryName(location));
        }
    }
}
