package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;

public class AdvancedSharpness extends DamageEnchantment {
    public static final String NAME = "advanced_sharpness";
    private static final ModConfig.AdvancedSharpnessOptions CONFIG = FancyEnchantments.getConfig().advancedSharpnessOptions;

    public AdvancedSharpness() {
        super(Rarity.RARE, DamageEnchantment.ALL, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND);
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
    public int getMinCost(int pLevel) {
        return 7 + pLevel * 5;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 45;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem() instanceof AxeItem || super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public float getDamageBonus(int level, MobType mobType, ItemStack enchantedItem) {
        return (1.5f + 0.8f * level);
    }

}
