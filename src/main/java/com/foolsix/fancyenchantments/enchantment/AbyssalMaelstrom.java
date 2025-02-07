package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.effect.EffectReg;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AquaEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class AbyssalMaelstrom extends AquaEnchantment {
    private static final ModConfig.AbyssalMaelstromOptions CONFIG = FancyEnchantments.getConfig().abyssalMaelstromOptions;
    public AbyssalMaelstrom() {
        super(CONFIG, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        if (pTarget instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(EffectReg.MAELSTROM.get(), CONFIG.durationPerLevel * pLevel * 20));
        }
    }
}
