package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AerEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.level.BlockEvent;

public class Gale extends AerEnchantment {
    private static final ModConfig.GaleOptions CONFIG = FancyEnchantments.getConfig().galeOptions;

    public Gale() {
        super(CONFIG, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public void blockBreak(BlockEvent.BreakEvent e) {
        if (e.isCanceled()) return;
        Player player = e.getPlayer();
        if (player == null) return;

        ItemStack stack = player.getMainHandItem();
        int level = stack.getEnchantmentLevel(this);
        if (level <= 0) return;

        int durationSeconds = CONFIG.durationSeconds;
        int durationTicks = durationSeconds * 20;
        MobEffectInstance origin = player.getEffect(MobEffects.DIG_SPEED);
        int amplifier = origin == null ? 0 : Math.min(origin.getAmplifier() + 1, level - 1);
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, durationTicks, amplifier));
    }
}

