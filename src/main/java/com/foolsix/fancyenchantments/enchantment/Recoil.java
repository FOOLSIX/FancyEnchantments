package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AerEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class Recoil extends AerEnchantment {
    private static final ModConfig.RecoilOptions CONFIG = FancyEnchantments.getConfig().recoilOptions;

    public Recoil() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    public void push(LivingAttackEvent e) {
        if (e.getSource() != null && e.getSource().getEntity() instanceof LivingEntity living) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, living);
            if (level > 0 && Math.random() < CONFIG.probabilityPerLevel * level) {
                living.push(0, level * CONFIG.distanceMultiplierPerLevel, 0);
            }
        }
    }
}
