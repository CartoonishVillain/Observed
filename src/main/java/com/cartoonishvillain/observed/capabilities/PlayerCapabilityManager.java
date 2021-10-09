package com.cartoonishvillain.observed.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerCapabilityManager implements IPlayerCapability, ICapabilityProvider, INBTSerializable<CompoundNBT> {
    public final LazyOptional<IPlayerCapability> holder = LazyOptional.of(()->this);
    protected float ObserveLevel = 0;
    @Override
    public float getObserveLevel() {
        return ObserveLevel;
    }

    @Override
    public void changeObserveLevel(float change) {
        ObserveLevel += change;
        if(ObserveLevel > 100) ObserveLevel = 100;
        if(ObserveLevel < 0) ObserveLevel = 0;
    }

    @Override
    public void setObserveLevel(float level) {
        ObserveLevel = level;
        if(ObserveLevel > 100) ObserveLevel = 100;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == PlayerCapability.INSTANCE){return PlayerCapability.INSTANCE.orEmpty(cap, this.holder);}
        else return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putFloat("observe", ObserveLevel);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        ObserveLevel = nbt.getFloat("observe");
    }
}
