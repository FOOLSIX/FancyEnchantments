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

public class ElementStatsCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<ElementStatsCapability> PLAYER_ELEMENT_STATS = CapabilityManager.get(new CapabilityToken<>() {
    });
    private ElementStatsCapability elementStats = null;
    private final LazyOptional<ElementStatsCapability> optional = LazyOptional.of(this::createElementStats);


    private ElementStatsCapability createElementStats() {
        if (this.elementStats == null) {
            this.elementStats = new ElementStatsCapability();
        }
        return this.elementStats;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return PLAYER_ELEMENT_STATS.orEmpty(cap, optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createElementStats().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createElementStats().loadNBTData(nbt);
    }
}
