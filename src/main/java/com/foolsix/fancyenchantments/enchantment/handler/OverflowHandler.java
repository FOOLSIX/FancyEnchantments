package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.OVERFLOW;

@EventBusSubscriber(modid = MODID)
public final class OverflowHandler {
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(OVERFLOW, attacker);
        if (level <= 0 || attacker.getRandom().nextDouble() >= Config.OVERFLOW_PROBABILITY.get() * level) {
            return;
        }

        Level levelAccess = attacker.level();
        BlockPos pos = event.getEntity().blockPosition();
        if (levelAccess.isEmptyBlock(pos)) {
            levelAccess.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
        }
    }
}
