package com.foolsix.fancyenchantments.enchantment.EssentiaEnch;

import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FEBaseEnchantment extends Enchantment {
    protected final ModConfig.BaseOptions CONFIG;

    protected FEBaseEnchantment(ModConfig.BaseOptions options, EnchantmentCategory category, EquipmentSlot[] equipmentSlots) {
        super(options.rarity, category, equipmentSlots);
        CONFIG = options;
    }

    @Override
    public int getMaxLevel() {
        return CONFIG.level;
    }

    @Override
    public boolean isTreasureOnly() {
        return CONFIG.isTreasure;
    }

    @Override
    public boolean isTradeable() {
        return CONFIG.level > 0 && CONFIG.isTradeable;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return CONFIG.level > 0 && CONFIG.isAllowedOnBooks;
    }

    @Override
    public boolean isDiscoverable() {
        return CONFIG.level > 0 && CONFIG.isDiscoverable;
    }
}
