package com.cartoonishvillain.observed.platform.services;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    int observerRange();
    int observerSpawnWeight();
    double observerFollowDistance();

    double closeObserverGainRate();
    double nearButNotCloseObserverGainRate();
    double farObserverGainRate();

    double highValueDrainRate();
    double mediumValueDrainRate();
    double lowValueDrainRate();

    int getWallVisionLevel();
    int getWallVisionRange();

    void setValue(ServerPlayer player, double value);
    void addValue(ServerPlayer player, double value);
    double getValue(ServerPlayer player);

    double getImmortuosInfectionValue(ServerPlayer player);

    SoundEvent getObserverAttackSound();
    SoundEvent getObserverHurtSound();
    SoundEvent getObserverDeathSound();
    MobEffect getObservedEffect();

}
