package com.cartoonishvillain.observed.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "observed")
public class ObservedConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public ObservedOptions observedOptions = new ObservedOptions();

    public static class ObservedOptions {
        public int observerRange = 32;
        public int observerSpawnWeight = 15;
        public double observerFollowDistance = 7.5f;
        public boolean observersSpawnInCaves = false;

        public double closeObserverGainRate = 1;
        public double nearButNotCloseObserverGainRate = 0.75;
        public double farObserverGainRate = 0.375;

        public double highValueDrainRate = 0.35;
        public double mediumValueDrainRate = 0.2;
        public double lowValueDrainRate = 0.1;

        @Comment("1 = Reduced visibility through walls (see next item), 2 = full visibility through walls, any other = no visibility through walls")
        public int wallVisionLevel = 0;
        @Comment("If WallVisionLevel is 1, what is the new range? Recommended to be lower than observerRange")
        public int wallVisionRange = 16;

    }
}
