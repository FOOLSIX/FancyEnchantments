package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AquaEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class Drowning extends AquaEnchantment {
    public static final String NAME = "drowning";
    private static final ModConfig.DrowningOptions CONFIG = FancyEnchantments.getConfig().drowningOptions;

    public Drowning() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        if (pAttacker.getAirSupply() <= 0 && !MobEffectUtil.hasWaterBreathing(pAttacker)) {
            pAttacker.hurt(pAttacker.damageSources().drown(), CONFIG.damage);
        }
        pAttacker.setAirSupply(CONFIG.airSupplyValue);
    }
}
