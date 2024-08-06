package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AerEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.TickEvent;

public class FeatherFall extends AerEnchantment {
    public static final String NAME = "feather_fall";
    private static final ModConfig.FeatherFallOptions CONFIG = FancyEnchantments.getConfig().featherFallOptions;

    public FeatherFall() {
        super(CONFIG, EnchantmentCategory.ARMOR_LEGS, new EquipmentSlot[]{EquipmentSlot.LEGS});
    }

    public void gainEffect(TickEvent.PlayerTickEvent e) {
        if (EnchantmentHelper.getEnchantmentLevel(this, e.player) > 0) {
            if (!e.player.onGround() && e.player.isCrouching()) {
                e.player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 20));
            }
        }
    }

}
