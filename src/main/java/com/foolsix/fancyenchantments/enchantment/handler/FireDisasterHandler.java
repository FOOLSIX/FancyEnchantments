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
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.FIRE_DISASTER;

@EventBusSubscriber(modid = MODID)
public final class FireDisasterHandler {
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity victim = event.getEntity();
        Level level = victim.level();
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) {
            return;
        }

        int enchantmentLevel = EnchUtils.getEnchantmentLevel(FIRE_DISASTER, attacker);
        if (enchantmentLevel <= 0 || attacker.getRandom().nextDouble() >= Config.FIRE_DISASTER_PROBABILITY.get() * enchantmentLevel) {
            return;
        }

        for (BlockPos pos : EnchUtils.getRandomValidPos(victim, level, 4)) {
            level.setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
        }
    }
}
