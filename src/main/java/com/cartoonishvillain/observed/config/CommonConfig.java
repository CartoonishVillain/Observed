package com.cartoonishvillain.observed.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommonConfig {

    public static final String CATEGORY = "Observer Properties";

    public ConfigHelper.ConfigValueListener<Integer> OBSERVERRANGE;
    public ConfigHelper.ConfigValueListener<Integer> OBSERVERWEIGHT;
    public ConfigHelper.ConfigValueListener<Double> OBSERVERFOLLOWPOINT;
    public ConfigHelper.ConfigValueListener<Boolean> CAVEOBSERVERS;

    public ConfigHelper.ConfigValueListener<Double> CLOSEOBSERVERATE;
    public ConfigHelper.ConfigValueListener<Double> NEAROBSERVERATE;
    public ConfigHelper.ConfigValueListener<Double> FAROBSERVERATE;

    public ConfigHelper.ConfigValueListener<Double> HIGHDRAINRATE;
    public ConfigHelper.ConfigValueListener<Double> MIDDRAINRATE;
    public ConfigHelper.ConfigValueListener<Double> LOWDRAINRATE;

    public ConfigHelper.ConfigValueListener<Boolean> EFFECTPARTICLES;
    public ConfigHelper.ConfigValueListener<Boolean> TORMENTCOMPAT;
    public ConfigHelper.ConfigValueListener<Double> TORMENTCOMPATVALUE;




    public CommonConfig(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber){
        builder.comment("Modify the rules for Observers").push(CATEGORY);
        this.OBSERVERRANGE = subscriber.subscribe(builder.comment("Changes how far an observer can notice a player from. Lower values make observers less expensive on system resources.").defineInRange("observerRange", 32, 10, 64));
        this.OBSERVERWEIGHT = subscriber.subscribe(builder.comment("Changes the spawn weight of the observer. Higher is more common. 100 is as common as zombies and the like.").defineInRange("observerWeight", 15, 0, 100));
        this.OBSERVERFOLLOWPOINT = subscriber.subscribe(builder.comment("How close to the edge of the range can a target get before the observer tries to follow. Higher values make observers move more often towards targets.").defineInRange("observerFollowRange", 7.5, 0, 60));
        this.CAVEOBSERVERS = subscriber.subscribe(builder.comment("Attempts to prevent observers from spawning in caves when set to false.").define("observerCaves", false));

        this.CLOSEOBSERVERATE = subscriber.subscribe(builder.comment("Rate of observation point gain per second when the target is within initial 30% of the observer's range. (for 32, this would be within 9.6 blocks)").defineInRange("closeObserveRate", 1, 0.25, 2));
        this.NEAROBSERVERATE = subscriber.subscribe(builder.comment("Rate of observation point gain per second when the target is out of 30% range but in 60% range. (for 32, this would be further than 9.6 blocks, but within 19.2 blocks)").defineInRange("nearObserveRate",0.75, 0.1, 1));
        this.FAROBSERVERATE = subscriber.subscribe(builder.comment("Rate of observation point gain per second when the target is out of 60% range").defineInRange("farObserveRate", 0.375, 0.05, 0.75));

        this.HIGHDRAINRATE = subscriber.subscribe(builder.comment("Rate of observation point drain per second when above 60% observed").defineInRange("highDrainRate", 0.35, 0, 1));
        this.MIDDRAINRATE = subscriber.subscribe(builder.comment("Rate of observation point drain per second when below 60% observed, but above 20%").defineInRange("midDrainRate", 0.2, 0, 1));
        this.LOWDRAINRATE = subscriber.subscribe(builder.comment("Rate of observation point drain per second below 20% observed").defineInRange("lowDrainRate", 0.1, 0, 1));

        this.EFFECTPARTICLES = subscriber.subscribe(builder.comment("Does the Observed effect show particles?").define("observedEffectParticles", false));

        this.TORMENTCOMPAT = subscriber.subscribe(builder.comment("Enables or disables compat with Torment, a fellow SpookyJam 2021 mod. Players with higher level of torment get their observed points gained multiplied by the rate determined in tormentCompatRate per point of torment. This does nothing without the mod installed.").define("observedTormentCompat", true));
        this.TORMENTCOMPATVALUE = subscriber.subscribe(builder.comment("The multiplier each point of torment provides to the strength of the observed effect.").defineInRange("tormentCompatRate", 0.0125, 0, 1));
        
    }
}