package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.HolyEnchantment;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;


public class PureFate extends HolyEnchantment {
    private static final ModConfig.PureFateOptions CONFIG = FancyEnchantments.getConfig().pureFateOptions;

    public PureFate() {
        super(CONFIG, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    public void removeCurse(BabyEntitySpawnEvent e) {
        if (e.getCausedByPlayer() instanceof ServerPlayer player) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, player);
            if (level > 0 && Math.random() < CONFIG.probability * level) {
                for (ItemStack stack : player.getAllSlots()) {
                    for (Enchantment enchantment : stack.getAllEnchantments().keySet()) {
                        if (enchantment.isCurse()) {
                            if (EnchUtils.removeEnchantment(stack, enchantment)) {
                                EnchUtils.generateSimpleParticleAroundEntity(player, ParticleTypes.HAPPY_VILLAGER);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
