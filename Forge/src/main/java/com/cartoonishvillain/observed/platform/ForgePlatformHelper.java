package com.cartoonishvillain.observed.platform;

import com.cartoonishvillain.immortuoscalyx.infection.InfectionManagerCapability;
import com.cartoonishvillain.observed.ForgeObserved;
import com.cartoonishvillain.observed.ObserveEffect;
import com.cartoonishvillain.observed.Register;
import com.cartoonishvillain.observed.capabilities.PlayerCapability;
import com.cartoonishvillain.observed.platform.services.IPlatformHelper;
import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public int observerRange() {
        return ForgeObserved.config.OBSERVERRANGE.get();
    }

    @Override
    public int observerSpawnWeight() {
        return ForgeObserved.config.OBSERVERWEIGHT.get();
    }

    @Override
    public double observerFollowDistance() {
        return ForgeObserved.config.OBSERVERFOLLOWPOINT.get();
    }

    @Override
    public double closeObserverGainRate() {
        return ForgeObserved.config.CLOSEOBSERVERATE.get();
    }

    @Override
    public double nearButNotCloseObserverGainRate() {
        return ForgeObserved.config.NEAROBSERVERATE.get();
    }

    @Override
    public double farObserverGainRate() {
        return ForgeObserved.config.FAROBSERVERATE.get();
    }

    @Override
    public double highValueDrainRate() {
        return ForgeObserved.config.HIGHDRAINRATE.get();
    }

    @Override
    public double mediumValueDrainRate() {
        return ForgeObserved.config.MIDDRAINRATE.get();
    }

    @Override
    public double lowValueDrainRate() {
        return ForgeObserved.config.LOWDRAINRATE.get();
    }

    @Override
    public int getWallVisionLevel() {
        return ForgeObserved.config.WALLVISIONLEVEL.get();
    }

    @Override
    public int getWallVisionRange() {
        return ForgeObserved.config.WALLVISIONRANGE.get();
    }

    @Override
    public void setValue(ServerPlayer player, double value) {
        player.getCapability(PlayerCapability.INSTANCE).ifPresent(h -> {
            h.setObserveLevel((float) value);
        });
    }

    @Override
    public void addValue(ServerPlayer player, double value) {
        player.getCapability(PlayerCapability.INSTANCE).ifPresent(h -> {
            h.changeObserveLevel((float) value);
        });
    }

    @Override
    public double getValue(ServerPlayer player) {
        AtomicDouble atomicDouble = new AtomicDouble(0);
        player.getCapability(PlayerCapability.INSTANCE).ifPresent(h -> {
            atomicDouble.set(h.getObserveLevel());
        });
        return atomicDouble.get();
    }

    @Override
    public double getImmortuosInfectionValue(ServerPlayer player) {
        AtomicDouble atomicDouble = new AtomicDouble(0);
        player.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
            atomicDouble.set(h.getInfectionProgress());
        });
        return atomicDouble.get();
    }

    @Override
    public SoundEvent getObserverAttackSound() {
        return Register.OBSERVERATTACK.get();
    }

    @Override
    public SoundEvent getObserverHurtSound() {
        return Register.OBSERVERHURT.get();
    }

    @Override
    public SoundEvent getObserverDeathSound() {
        return Register.OBSERVERDEATH.get();
    }

    @Override
    public MobEffect getObservedEffect() {
        return ObserveEffect.observed;
    }
}
