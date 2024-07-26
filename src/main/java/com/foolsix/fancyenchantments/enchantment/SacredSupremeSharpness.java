package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.HolyEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class SacredSupremeSharpness extends HolyEnchantment {
    public static final String NAME = "sacred_supreme_sharpness";
    private static final ModConfig.SacredSupremeSharpnessOptions CONFIG = FancyEnchantments.getConfig().sacredSupremeSharpnessOptions;

    public SacredSupremeSharpness() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem() instanceof AxeItem || super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean checkCompatibility(Enchantment pEnch) {
        return !(pEnch instanceof DamageEnchantment) && pEnch.isCompatibleWith(Enchantments.SHARPNESS) && super.checkCompatibility(pEnch);
    }

    @Override
    public float getDamageBonus(int level, MobType mobType, ItemStack enchantedItem) {
        return CONFIG.baseDamage + level * (mobType == MobType.UNDEAD ? CONFIG.damageMultiplierToUndead : CONFIG.damageMultiplier);
    }
}
