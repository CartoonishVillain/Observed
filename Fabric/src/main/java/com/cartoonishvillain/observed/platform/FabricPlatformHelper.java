package com.cartoonishvillain.observed.platform;

import com.cartoonishvillain.immortuoscalyx.component.InfectionComponent;
import com.cartoonishvillain.observed.FabricObserved;
import com.cartoonishvillain.observed.Register;
import com.cartoonishvillain.observed.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;

import static com.cartoonishvillain.immortuoscalyx.component.ComponentStarter.INFECTION;
import static com.cartoonishvillain.observed.component.ComponentStarter.OBSERVELEVEL;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public int observerRange() {
        return FabricObserved.CONFIG.getOrDefault("observerRange", 32);
    }

    @Override
    public int observerSpawnWeight() {
        return FabricObserved.CONFIG.getOrDefault("observerSpawnWeight", 15);
    }

    @Override
    public double observerFollowDistance() {
        return FabricObserved.CONFIG.getOrDefault("observerFollowDistance", 7.5);
    }

    @Override
    public double closeObserverGainRate() {
        return FabricObserved.CONFIG.getOrDefault("closeObserverGainRate", 1);
    }

    @Override
    public double nearButNotCloseObserverGainRate() {
        return FabricObserved.CONFIG.getOrDefault("nearButNotCloseObserverGainRate", 0.75);
    }

    @Override
    public double farObserverGainRate() {
        return FabricObserved.CONFIG.getOrDefault("farObserverGainRate", 0.375);
    }

    @Override
    public double highValueDrainRate() {
        return FabricObserved.CONFIG.getOrDefault("highValueDrainRate", 0.35);
    }

    @Override
    public double mediumValueDrainRate() {
        return FabricObserved.CONFIG.getOrDefault("mediumValueDrainRate", 0.2);
    }

    @Override
    public double lowValueDrainRate() {
        return FabricObserved.CONFIG.getOrDefault("lowValueDrainRate", 0.1);
    }

    @Override
    public int getWallVisionLevel() {
        return FabricObserved.CONFIG.getOrDefault("wallVisionLevel", 0);
    }

    @Override
    public int getWallVisionRange() {
        return FabricObserved.CONFIG.getOrDefault("wallVisionRange", 16);
    }

    @Override
    public void setValue(ServerPlayer player, double value) {
        OBSERVELEVEL.get(player).setObserveLevel((float) value);
    }

    @Override
    public void addValue(ServerPlayer player, double value) {
        OBSERVELEVEL.get(player).changeObserveLevel((float) value);
    }

    @Override
    public double getValue(ServerPlayer player) {
        return OBSERVELEVEL.get(player).getObserveLevel();
    }

    @Override
    public double getImmortuosInfectionValue(ServerPlayer player) {
        InfectionComponent h = INFECTION.get(player);
        return h.getInfectionProgress();
    }

    @Override
    public SoundEvent getObserverAttackSound() {
        return Register.ATTACKSOUNDEVENT;
    }

    @Override
    public SoundEvent getObserverHurtSound() {
        return Register.HURTSOUNDEVENT;
    }

    @Override
    public SoundEvent getObserverDeathSound() {
        return Register.DEATHSOUNDEVENT;
    }

    @Override
    public MobEffect getObservedEffect() {
        return Register.OBSERVE_EFFECT;
    }
}
