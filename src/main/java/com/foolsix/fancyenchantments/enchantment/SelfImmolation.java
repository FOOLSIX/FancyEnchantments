package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.IgnisEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SelfImmolation extends IgnisEnchantment {
    private static final ModConfig.SelfImmolationOptions CONFIG = FancyEnchantments.getConfig().selfImmolationOptions;

    public SelfImmolation() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        double rand = Math.random();
        if (pTarget.isOnFire() && rand < CONFIG.probabilityWithFire || rand < CONFIG.baseProbability) {
            pAttacker.setSecondsOnFire(CONFIG.fireTime);
        }
    }
}
