package com.cartoonishvillain.observed;

import com.cartoonishvillain.observed.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObservedCommon {

    // This method serves as an initialization hook for the mod. The vanilla
    // game has no mechanism to load tooltip listeners so this must be
    // invoked from a mod loader specific project like Forge or Fabric.
    public static ArrayList<Item> RANGEBLOCKINGITEMS = new ArrayList<>(Arrays.asList(Items.SKELETON_SKULL, Items.WITHER_SKELETON_SKULL, Items.CARVED_PUMPKIN, Items.CREEPER_HEAD, Items.ZOMBIE_HEAD));
    public static boolean isCalyxLoaded;
    public static void init() {
        isCalyxLoaded = Services.PLATFORM.isModLoaded("immortuoscalyx");
    }
}