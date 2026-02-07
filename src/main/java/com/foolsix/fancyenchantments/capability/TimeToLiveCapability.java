package com.foolsix.fancyenchantments.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class TimeToLiveCapability implements ITimeToLiveCapability {
    private int ttl;

    public TimeToLiveCapability() {
        ttl = -1;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int val) {
        ttl = Math.max(val, -1);
    }

    public void subTtl(int sub) {
        ttl = Math.max(ttl - sub, -1);
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("time_to_live", ttl);
    }

    public void loadNBTData(CompoundTag nbt) {
        ttl = nbt.getInt("time_to_live");
    }

}
