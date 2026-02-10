package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.HolyEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.MobEffectEvent;

public class AilmentDevourer extends HolyEnchantment {
    private static final ModConfig.AilmentDevourerOptions CONFIG = FancyEnchantments.getConfig().ailmentDevourerOptions;
    public AilmentDevourer() {
        super(CONFIG, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    public void decreaseDebuffDuration(MobEffectEvent.Added e) {
        LivingEntity living = e.getEntity();
        MobEffectInstance instance = e.getEffectInstance();
        if (instance.getEffect().getCategory() != MobEffectCategory.HARMFUL) return;
        int level = EnchantmentHelper.getEnchantmentLevel(this, living);
        if (level > 0) {
            instance.duration = instance.getDuration() / (level + 1);
        }
    }
}
