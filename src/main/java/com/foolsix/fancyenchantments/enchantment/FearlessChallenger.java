package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.HolyEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class FearlessChallenger extends HolyEnchantment {
    private static final ModConfig.FearlessChallengerOptions CONFIG = FancyEnchantments.getConfig().fearlessChallengerOptions;

    public FearlessChallenger() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public void damageBonus(LivingDamageEvent e) {
        if (e.getSource().getEntity() instanceof Player player) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, player);
            LivingEntity victim = e.getEntity();
            if (level > 0 && player.getHealth() > 1 && victim.getHealth() / player.getHealth() > CONFIG.HPCondition ) {
                double damageMultiplier = Math.min(CONFIG.cap, CONFIG.multiplier * victim.getHealth() / player.getHealth() * level);
                e.setAmount((float) (e.getAmount() * damageMultiplier));
            }
        }
    }
}
