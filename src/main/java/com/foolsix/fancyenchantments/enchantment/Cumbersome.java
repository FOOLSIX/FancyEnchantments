package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TerraEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import static com.foolsix.fancyenchantments.effect.EffectReg.CUMBERSOME;

public class Cumbersome extends TerraEnchantment {
    private static final ModConfig.CumbersomeOptions CONFIG = FancyEnchantments.getConfig().cumbersomeOptions;

    public Cumbersome() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        if (pLevel > 0 && Math.random() < CONFIG.probability) {
            pAttacker.addEffect(new MobEffectInstance(CUMBERSOME.get(), CONFIG.duration * 20));
        }
    }
}
