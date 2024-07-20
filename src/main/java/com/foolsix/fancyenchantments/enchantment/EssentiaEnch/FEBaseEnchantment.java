package com.foolsix.fancyenchantments.enchantment.EssentiaEnch;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
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

    @Override
    public int getMaxCost(int pLevel) {
        return this.getMinCost(pLevel) + 50;
    }

    public boolean isSpecialLoot() {
        return CONFIG instanceof ModConfig.LootEnchantmentOptions;
    }

    public double getChestGenerationProbability() {
        if (CONFIG instanceof ModConfig.LootEnchantmentOptions loot) return loot.probabilityOfGeneration;
        return 0;
    }

    public int[] getChestGenerationCondition() {
        if (CONFIG instanceof ModConfig.LootEnchantmentOptions loot) return loot.elementalCondition;
        return EnchUtils.EMPTY_CONDITION;
    }

    public boolean tryGenerateOnce(int[] elementalStat) {
        int[] condition = getChestGenerationCondition();
        if (Math.random() > getChestGenerationProbability() || elementalStat.length != condition.length) return false;
        for (int i = 0; i < condition.length; ++i) {
            if (condition[i] > elementalStat[i]) {
                return false;
            }
        }
        return true;
    }

}
