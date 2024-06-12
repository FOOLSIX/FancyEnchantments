package com.foolsix.fancyenchantments.capability;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils.Element;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.Arrays;

@AutoRegisterCapability
public class ElementStatsCapability implements IElementStatsCapability {
    private final int[] stats;

    public ElementStatsCapability() {
        stats = new int[EnchUtils.ELEMENT_COUNT];
    }

    @Override
    public int getPoint(Element type) {
        return stats[type.ordinal()];
    }

    @Override
    public void addPoint(Element type, int val) {
        stats[type.ordinal()] += val;
    }

    @Override
    public void subPoint(Element type, int val) {
        stats[type.ordinal()] = Math.max(0, stats[type.ordinal()] - val);
    }

    @Override
    public void resetPoint() {
        Arrays.fill(stats, 0);
    }

    public void saveNBTData(CompoundTag nbt) {
        for (Element element : Element.values()) {
            nbt.putInt(element.name().toLowerCase(), stats[element.ordinal()]);
        }
    }

    public void loadNBTData(CompoundTag nbt) {
        for (Element element : Element.values()) {
            stats[element.ordinal()] = nbt.getInt(element.name().toLowerCase());
        }
    }
}
