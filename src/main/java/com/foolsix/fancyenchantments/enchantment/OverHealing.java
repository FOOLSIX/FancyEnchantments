package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.HolyEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHealEvent;

public class OverHealing extends HolyEnchantment {
    private static final ModConfig.OverHealingOptions CONFIG = FancyEnchantments.getConfig().overHealingOptions;
    public OverHealing() {
        super(CONFIG, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    public void overHeal(LivingHealEvent e) {
        LivingEntity living = e.getEntity();
        float val = e.getAmount();
        if (living != null) {
            int cap = EnchantmentHelper.getEnchantmentLevel(this, living) * CONFIG.cap;
            float maxHealth = living.getMaxHealth();
            float currentHealth = living.getHealth();
            float currentAbsorption = living.getAbsorptionAmount();
            if (currentHealth + val > maxHealth && currentAbsorption < cap) {
                living.setAbsorptionAmount(Math.min(cap, currentAbsorption + val));
            }
        }
    }
}


