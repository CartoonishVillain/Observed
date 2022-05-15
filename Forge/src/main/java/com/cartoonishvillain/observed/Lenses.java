package com.cartoonishvillain.observed;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class Lenses extends Item implements ICurioItem {
    public Lenses(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        ICurioItem.super.curioTick(slotContext, stack);
        slotContext.entity().addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 12*20, 0));
    }
}
