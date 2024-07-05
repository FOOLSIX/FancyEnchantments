package com.foolsix.fancyenchantments.capability;

import net.minecraft.nbt.CompoundTag;

public interface ITimeToLiveCapability {
    int getTtl();

    void setTtl(int val);

    void subTtl(int sub);

    void saveNBTData(CompoundTag nbt);

    void loadNBTData(CompoundTag nbt);
}
