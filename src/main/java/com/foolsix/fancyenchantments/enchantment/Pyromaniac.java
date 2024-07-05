package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.IgnisEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class Pyromaniac extends IgnisEnchantment {
    public static final String NAME = "pyromaniac";
    private static final ModConfig.PyromaniacOptions CONFIG = FancyEnchantments.getConfig().pyromaniacOptions;

    public Pyromaniac() {
        super(CONFIG, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public int getMinCost(int pLevel) {
        return 5 + pLevel * 5;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 50;
    }

    @Override
    protected boolean checkCompatibility(Enchantment pOther) {
        return super.checkCompatibility(pOther) && pOther != Enchantments.BLAST_PROTECTION;
    }

    public void receiveExplosive(LivingHurtEvent e) {
        if (e.getEntity() instanceof Player player && e.getSource().is(DamageTypes.EXPLOSION)) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, player);
            if (level > 0) {
                float healValue = CONFIG.healMultiplier * e.getAmount() * level;
                float damageBonus = Math.min(player.getMaxHealth() - player.getHealth(), healValue);
                ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
                stack.hurtAndBreak(CONFIG.armorBaseDamage + (int) (CONFIG.damageMultiplier * damageBonus), player, (p) -> p.broadcastBreakEvent(EquipmentSlot.CHEST));
                player.heal(healValue);
                e.setCanceled(true);
            }
        }
    }
}
