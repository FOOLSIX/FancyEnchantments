package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.IgnisEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class AdvancedFireAspect extends IgnisEnchantment {
    private static final ModConfig.AdvancedFireAspectOptions CONFIG = FancyEnchantments.getConfig().advancedFireAspectOptions;

    public AdvancedFireAspect() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        pTarget.setRemainingFireTicks(pTarget.getRemainingFireTicks() + CONFIG.increasedSecondPerHit * pLevel * 20);
    }
}
