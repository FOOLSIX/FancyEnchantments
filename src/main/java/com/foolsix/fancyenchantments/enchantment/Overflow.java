package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AquaEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.LivingDeathEventHandler;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class Overflow extends AquaEnchantment implements LivingDeathEventHandler {
    private static final ModConfig.OverflowOptions CONFIG = FancyEnchantments.getConfig().overflowOptions;

    public Overflow() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @Override
    public void handleLivingDeathEvent(LivingDeathEvent e) {
        generateWater(e);
    }

    public void generateWater(LivingDeathEvent e) {
        LivingEntity victim = e.getEntity();
        if (e.getSource().getEntity() instanceof LivingEntity attacker) {
            Level world = attacker.level;
            int level = EnchantmentHelper.getEnchantmentLevel(this, attacker);
            if (level > 0) {
                if (Math.random() < CONFIG.probability * level) {
                    BlockPos pos = victim.blockPosition();
                    if (world.isEmptyBlock(pos)) {
                        world.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
                    }
                }
            }
        }
    }
}
