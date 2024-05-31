package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TerraEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import static com.foolsix.fancyenchantments.effect.EffectReg.CUMBERSOME;

public class Cumbersome extends TerraEnchantment {
    public static final String NAME = "cumbersome";
    private static final ModConfig.CumbersomeOptions CONFIG = FancyEnchantments.getConfig().cumbersomeOptions;

    public Cumbersome() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    public void getCooldown(LivingAttackEvent e) {
        if (e.getSource() == null || e.getSource().getEntity() == null) return;
        if (e.getSource().getEntity() instanceof LivingEntity living) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, living);
            if (level > 0 && Math.random() < CONFIG.probability) {
                living.addEffect(new MobEffectInstance(CUMBERSOME.get(), CONFIG.duration * 20));
            }
        }
    }
}
