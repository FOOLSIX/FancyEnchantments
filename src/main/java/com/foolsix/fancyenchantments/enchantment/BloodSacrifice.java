package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TwistedEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class BloodSacrifice extends TwistedEnchantment {
    public static final String NAME = "blood_sacrifice";
    private static final ModConfig.BloodSacrificeOptions CONFIG = FancyEnchantments.getConfig().bloodSacrificeOptions;

    public BloodSacrifice() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public void attack(LivingHurtEvent e) {
        if (e.getSource().getEntity() instanceof ServerPlayer player) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, player);
            if (level > 0 && player.getAttackStrengthScale(0.5F) > 0.95) {
                player.hurt(player.damageSources().wither(), CONFIG.damage * level);
                e.setAmount(e.getAmount() * (1 + level * (CONFIG.base + 1 - player.getHealth() / player.getMaxHealth())));
            }
        }
    }
}
