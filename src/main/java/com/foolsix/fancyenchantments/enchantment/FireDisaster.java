package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.IgnisEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import static com.foolsix.fancyenchantments.enchantment.util.EnchUtils.getRandomValidPos;

public class FireDisaster extends IgnisEnchantment {
    public static final String NAME = "fire_disaster";
    private static final ModConfig.FireDisasterOptions CONFIG = FancyEnchantments.getConfig().fireDisasterOptions;

    public FireDisaster() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    public void generateFire(LivingDeathEvent e) {
        LivingEntity victim = e.getEntity();
        Level world = victim.level();
        if (e.getSource().getEntity() instanceof LivingEntity attacker) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, attacker);
            if (level > 0) {
                if (Math.random() < CONFIG.probability) {
                    for (BlockPos pos : getRandomValidPos(victim, world, 4)) {
                        world.setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
                    }
                }
            }
        }
    }
}
