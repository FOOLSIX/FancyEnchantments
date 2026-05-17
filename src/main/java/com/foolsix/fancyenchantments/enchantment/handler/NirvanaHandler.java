package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.NIRVANA;

@EventBusSubscriber(modid = MODID)
public final class NirvanaHandler {
    private static final String COOLDOWN_TAG = MODID + ":nirvana_cooldown";

    @SubscribeEvent
    public static void onPlayerTickPre(PlayerTickEvent.Pre event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        int intervalTicks = Config.NIRVANA_INTERVAL_SECONDS.get() * 20;

        if (player.getPersistentData().getLong(COOLDOWN_TAG) <= player.level().getGameTime()) {
            player.getPersistentData().remove(COOLDOWN_TAG);
        } else {
            return;
        }


        int level = EnchUtils.getEnchantmentLevel(NIRVANA, player);
        if (level <= 0
                || !player.isOnFire()
                || player.getHealth() >= player.getMaxHealth() * Config.NIRVANA_MINIMUM_HEALTH_RATIO.get()) {
            return;
        }

        player.addEffect(new MobEffectInstance(MobEffects.HEAL, Config.NIRVANA_HEAL_DURATION_TICKS.get(), level - 1, false, false));
        EnchUtils.generateSimpleParticleAroundEntity(player, ParticleTypes.TOTEM_OF_UNDYING);
        EnchUtils.generateSimpleParticleAroundEntity(player, ParticleTypes.LAVA);
        player.clearFire();
        player.getPersistentData().putLong(COOLDOWN_TAG, player.level().getGameTime() + intervalTicks);
    }
}
