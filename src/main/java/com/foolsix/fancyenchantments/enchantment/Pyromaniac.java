package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.IgnisEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class Pyromaniac extends IgnisEnchantment {
    private static final ModConfig.PyromaniacOptions CONFIG = FancyEnchantments.getConfig().pyromaniacOptions;
    public Pyromaniac() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public int getMaxLevel() {
        return CONFIG.level;
    }

    @Override
    public int getMinCost(int pLevel) {
        return 5 + pLevel * 5;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 10;
    }

    @Override
    public boolean isTreasureOnly() {
        return CONFIG.isTreasure;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return CONFIG.level != 0;
    }

    public void recieveExplosive(LivingHurtEvent e) {
        if (e.getEntity() instanceof Player player && e.getSource().isExplosion()) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, player);
            if (level > 0) {
                player.heal(CONFIG.healMultiplier * e.getAmount() * level);
                e.setCanceled(true);
            }
        }
    }
}
