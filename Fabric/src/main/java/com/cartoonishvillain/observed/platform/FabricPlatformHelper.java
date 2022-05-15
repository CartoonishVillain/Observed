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
        return FabricObserved.config.observedOptions.observerRange;
    }

    @Override
    public int observerSpawnWeight() {
        return FabricObserved.config.observedOptions.observerSpawnWeight;
    }

    @Override
    public double observerFollowDistance() {
        return FabricObserved.config.observedOptions.observerFollowDistance;
    }

    @Override
    public double closeObserverGainRate() {
        return FabricObserved.config.observedOptions.closeObserverGainRate;
    }

    @Override
    public double nearButNotCloseObserverGainRate() {
        return FabricObserved.config.observedOptions.nearButNotCloseObserverGainRate;
    }

    @Override
    public double farObserverGainRate() {
        return FabricObserved.config.observedOptions.farObserverGainRate;
    }

    @Override
    public double highValueDrainRate() {
        return FabricObserved.config.observedOptions.highValueDrainRate;
    }

    @Override
    public double mediumValueDrainRate() {
        return FabricObserved.config.observedOptions.mediumValueDrainRate;
    }

    @Override
    public double lowValueDrainRate() {
        return FabricObserved.config.observedOptions.lowValueDrainRate;
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
