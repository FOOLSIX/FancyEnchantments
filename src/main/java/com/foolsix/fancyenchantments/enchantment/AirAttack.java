package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AerEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class AirAttack extends AerEnchantment {
    public static final String NAME = "air_attack";
    private static final ModConfig.AirAttackOptions CONFIG = FancyEnchantments.getConfig().airAttackOptions;

    public AirAttack() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public void attack(LivingHurtEvent e) {
        if (e.getSource().getEntity() instanceof LivingEntity living && living.fallDistance > 0) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, living);
            if (level > 0) {
                e.setAmount(e.getAmount() + living.fallDistance * CONFIG.damageMultiplier * level);
                living.resetFallDistance();
            }
        }
    }
}
