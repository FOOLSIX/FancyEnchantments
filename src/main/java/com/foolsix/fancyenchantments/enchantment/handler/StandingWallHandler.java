package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.STANDING_WALL;

@EventBusSubscriber(modid = MODID)
public final class StandingWallHandler {
    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player && player.isBlocking()) {
            int level = EnchUtils.getEnchantmentLevel(STANDING_WALL, player);
            if (level <= 0) return;

            Entity attacker = event.getSource().getEntity();
            if (attacker != null && EnchUtils.canBlock(player, attacker.position())) {
                player.getUseItem().hurtAndBreak(2, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
                player.level().playSound(null, player.blockPosition(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0F, 1.5F + player.level().random.nextFloat() * 0.4F);
                event.setCanceled(true);
            }
        }
    }
}
