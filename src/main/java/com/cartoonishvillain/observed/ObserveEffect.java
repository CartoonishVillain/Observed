package com.cartoonishvillain.observed;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ObserveEffect {
    public static Effect observed;

    public static void init(){
        observed = new ModdedPotionEffects(EffectType.HARMFUL, 9606036, new ResourceLocation(Observed.MODID, "observedeffect"));
    }

    /*
        While code reusage is minimal would like to shout out the immersive engineering team and BluSunrize for having such a neat license to help me get through this bit in particular
     */
    public static class ModdedPotionEffects extends Effect {

        protected ModdedPotionEffects(EffectType p_19451_, int p_19452_, ResourceLocation location) {
            super(p_19451_, p_19452_);
            ForgeRegistries.POTIONS.register(this.setRegistryName(location));
        }
    }
}
