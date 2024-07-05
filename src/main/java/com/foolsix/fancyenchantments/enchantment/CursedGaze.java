package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TwistedEnchantment;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.TickEvent;


public class CursedGaze extends TwistedEnchantment {
    public static final String NAME = "cursed_gaze";
    private static final ModConfig.CursedGazeOptions CONFIG = FancyEnchantments.getConfig().cursedGazeOptions;

    public CursedGaze() {
        super(CONFIG, EnchantmentCategory.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD});
    }

    @Override
    public int getMinCost(int pLevel) {
        return 15 + pLevel * 5;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 50;
    }

    public void cursedGaze(TickEvent.PlayerTickEvent e) {
        int level = EnchantmentHelper.getEnchantmentLevel(this, e.player);
        if (level > 0) {
            LivingEntity lookAt = EnchUtils.getLookAtLivingEntity(e.player, 1.0F, Math.min(CONFIG.baseDistance * level, 128));
            if (lookAt != null) {
                lookAt.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, level - 1));
                lookAt.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, level - 1));
            }
        }
    }
}
