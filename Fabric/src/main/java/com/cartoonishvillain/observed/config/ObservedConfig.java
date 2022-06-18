package com.cartoonishvillain.observed.config;

public class ObservedConfig {
    public static String provider(String filename) {
        return "observerRange=32\n" +
                "observerSpawnWeight=15\n" +
                "observerFollowDistance=7.5\n" +
                "observersSpawnInCaves=false\n" +
                "closeObserverGainRate=1\n" +
                "nearButNotCloseObserverGainRate=0.75\n" +
                "farObserverGainRate=0.375\n" +
                "highValueDrainRate=0.35\n" +
                "mediumValueDrainRate=0.2\n" +
                "lowValueDrainRate=0.1\n" +
                "#1 = Reduced visibility through walls (see next item), 2 = full visibility through walls, any other = no visibility through walls\n" +
                "wallVisionLevel=0\n" +
                "#If WallVisionLevel is 1, what is the new range? Recommended to be lower than observerRange\n" +
                "wallVisionRange=16";
    }
}
