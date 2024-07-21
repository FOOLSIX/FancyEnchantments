package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.IgnisEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.TickEvent;

public class Nirvana extends IgnisEnchantment {
    public static final String NAME = "nirvana";
    private static final ModConfig.NirvanaOptions CONFIG = FancyEnchantments.getConfig().nirvanaOptions;

    public Nirvana() {
        super(CONFIG, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    public void getRegeneration(TickEvent.PlayerTickEvent e) {
        if (e.player instanceof ServerPlayer player && player.tickCount % 20 == 0) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, player);
            if (level > 0 && player.isOnFire() && player.getHealth() < player.getMaxHealth() * CONFIG.minimumHealthRatio) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, level - 1, false, false));
            }
        }
    }
}
