package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.WIND_FIRE_WHEELS;

@EventBusSubscriber(modid = MODID)
public final class WindFireWheelsHandler {
    @SubscribeEvent
    public static void onPlayerTickPost(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!(player.level() instanceof ServerLevel) || !player.isSprinting() || player.onGround()) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(WIND_FIRE_WHEELS, player);
        if (level <= 0) {
            return;
        }

        player.resetFallDistance();
        EnchUtils.generateSimpleParticleAroundEntity(player, ParticleTypes.FLAME, 1, 0.0D, 0.1D, 0.0D, 0.0D);
    }
}
