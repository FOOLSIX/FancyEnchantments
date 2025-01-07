package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AquaEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.LivingHurtEventHandler;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class BubbleShield extends AquaEnchantment implements LivingHurtEventHandler {
    private static final ModConfig.BubbleShieldOptions CONFIG = FancyEnchantments.getConfig().bubbleShieldOptions;

    public BubbleShield() {
        super(CONFIG, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public int getLivingHurtPriority() {
        return ADD;
    }

    @Override
    public void handleLivingHurtEvent(LivingHurtEvent e) {
        reduceDamage(e);
    }

    public void reduceDamage(LivingHurtEvent e) {
        if (e.getEntity() instanceof Player player) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, player);
            if (level > 0 && player.getAirSupply() >= player.getMaxAirSupply() * CONFIG.airSupplyRatio) {
                int reducer = (int) (player.getAirSupply() * CONFIG.costRatio * CONFIG.multiplier * level);
                player.setAirSupply((int) Math.max(0, player.getAirSupply() * (1 - CONFIG.costRatio)));
                e.setAmount(Math.max(0f, e.getAmount() - reducer));
            }
        }
    }
}
