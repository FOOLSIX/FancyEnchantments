package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;

public class AdvancedProtection extends FEBaseEnchantment {
    public static final String NAME = "advanced_protection";
    private static final ModConfig.AdvancedProtectionOptions CONFIG = FancyEnchantments.getConfig().advancedProtectionOptions;

    public AdvancedProtection() {
        super(CONFIG, EnchantmentCategory.WEARABLE, new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
    }

    @Override
    public int getDamageProtection(int pLevel, DamageSource pSource) {
        if (pSource.isBypassInvul()) {
            return 0;
        }
        return (int) (pLevel * CONFIG.EPFMultiplier);
    }

    @Override
    public boolean checkCompatibility(Enchantment pOther) {
        return super.checkCompatibility(pOther) && !(pOther instanceof ProtectionEnchantment) && pOther.isCompatibleWith(Enchantments.ALL_DAMAGE_PROTECTION);
    }
}
