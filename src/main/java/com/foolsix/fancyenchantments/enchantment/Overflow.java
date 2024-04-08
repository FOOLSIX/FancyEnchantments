package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AquaEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class Overflow extends AquaEnchantment {
    private static final ModConfig.OverflowOptions CONFIG = FancyEnchantments.getConfig().overflowOptions;
    public Overflow() {
        super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return CONFIG.level;
    }


    @Override
    public boolean isAllowedOnBooks() {
        return CONFIG.level != 0;
    }

    @Override
    public boolean isCurse() {
        return true;
    }


    public void generateWater(LivingDeathEvent e) {
        LivingEntity victim = e.getEntity();
        if (e.getSource().getEntity() instanceof LivingEntity attacker) {
            Level world = attacker.level;
            int level = EnchantmentHelper.getEnchantmentLevel(this, attacker);
            if (level > 0) {
                if (Math.random() < CONFIG.probability) {
                    BlockPos pos = new BlockPos(victim.getBlockX(), victim.getBlockY(), victim.getBlockZ());
                    if (world.isEmptyBlock(pos)) {
                        world.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
                    }
                }
            }
        }
    }
}
