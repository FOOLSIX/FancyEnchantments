package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AquaEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class Calmer extends AquaEnchantment {
    public static final String NAME = "calmer";
    private static final ModConfig.CalmerOptions CONFIG = FancyEnchantments.getConfig().calmerOptions;

    public Calmer() {
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

    public void gainBuff(LivingHurtEvent e) {
        int level = EnchantmentHelper.getEnchantmentLevel(this, e.getEntity());
        if (level > 0 && e.getEntity() instanceof Player player) {
            Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
            if (!player.getCooldowns().isOnCooldown(chest)) {
                player.getCooldowns().addCooldown(chest, CONFIG.cooldown * 20);
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, CONFIG.duration * 20, level - 1));
            }
        }
    }
}
