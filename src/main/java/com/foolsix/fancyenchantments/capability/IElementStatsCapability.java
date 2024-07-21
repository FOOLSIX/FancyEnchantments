package com.foolsix.fancyenchantments.capability;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils.Element;
import net.minecraft.nbt.CompoundTag;

public interface IElementStatsCapability {
    int getPoint(Element type);

    void addPoint(Element type, int val);

    void subPoint(Element type, int val);

    void resetPoint();

    int[] getStats();

    void saveNBTData(CompoundTag nbt);

    void loadNBTData(CompoundTag nbt);

}
