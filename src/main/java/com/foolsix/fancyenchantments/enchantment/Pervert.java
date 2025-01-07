package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;

public class Pervert extends FEBaseEnchantment {
    private static final ModConfig.PervertOptions CONFIG = FancyEnchantments.getConfig().pervertOptions;

    public Pervert() {
        super(CONFIG, EnchantmentCategory.ARMOR_LEGS, new EquipmentSlot[]{EquipmentSlot.LEGS});
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    public void dropOnWear(LivingEquipmentChangeEvent e) {
        if (e.getEntity() instanceof Player player
                && e.getSlot() == EquipmentSlot.LEGS
                && e.getTo().getEnchantmentLevel(this) > 0
                && Math.random() < CONFIG.dropProbability) {
            player.drop(e.getTo(), false);
            player.getInventory().removeItem(e.getTo());
        }
    }
}
