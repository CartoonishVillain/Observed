package com.cartoonishvillain.observed;

import com.cartoonishvillain.observed.platform.Services;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Arrays;

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