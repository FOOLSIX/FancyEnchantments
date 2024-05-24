package com.foolsix.fancyenchantments.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TimeToLiveCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<TimeToLiveCapability> PLAYER_TTL = CapabilityManager.get(new CapabilityToken<TimeToLiveCapability>() {
    });
    private TimeToLiveCapability ttl = null;
    private final LazyOptional<TimeToLiveCapability> optional = LazyOptional.of(this::createTimeToLive);

    private TimeToLiveCapability createTimeToLive() {
        if (this.ttl == null) {
            this.ttl = new TimeToLiveCapability();
        }
        return this.ttl;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return optional.cast();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createTimeToLive().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createTimeToLive().loadNBTData(nbt);
    }
}
