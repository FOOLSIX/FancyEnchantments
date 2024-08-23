package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TerraEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class Eroding extends TerraEnchantment {
    public static final String NAME = "eroding";
    private static final ModConfig.ErodingOptions CONFIG = FancyEnchantments.getConfig().erodingOptions;

    public Eroding() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.OFFHAND, EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        ItemStack stack = pAttacker.getMainHandItem();
        int level = EnchantmentHelper.getEnchantmentLevel(this, pAttacker);
        if (Math.random() < CONFIG.probabilityPerLevel * level) {
            stack.hurtAndBreak(level * CONFIG.damageMultiplier, pAttacker, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
        }
    }
}
