package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AerEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class Afterimage extends AerEnchantment {
    protected static final ModConfig.AfterimageOptions CONFIG = FancyEnchantments.getConfig().afterimageOptions;

    public Afterimage() {
        super(CONFIG, EnchantmentCategory.ARMOR_LEGS, new EquipmentSlot[]{EquipmentSlot.LEGS});
    }

    public void avoidDamage(LivingDamageEvent e) {
        LivingEntity living = e.getEntity();
        int level = EnchantmentHelper.getEnchantmentLevel(this, living);
        if (level > 0) {
            AttributeInstance moveSpeedAttr = living.getAttribute(Attributes.MOVEMENT_SPEED);
            double extraSpeed = 0.2;
            if (moveSpeedAttr != null) {
                extraSpeed = Math.max(0, moveSpeedAttr.getValue() - moveSpeedAttr.getBaseValue()) / moveSpeedAttr.getBaseValue();
            }
            if (Math.random() < Math.min(extraSpeed * CONFIG.probabilityMultiplier, Math.min(level * CONFIG.probabilityCapPerLevel, CONFIG.probabilityMaxCap))) {
                living.invulnerableTime += 60;
                e.setCanceled(true);
            }
        }
    }
}
