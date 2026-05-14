package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.effect.EffectReg;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.COUNTERATTACK;

@EventBusSubscriber(modid = MODID)
public final class CounterattackHandler {
    private static final int DURATION = 30;

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            tryApplyBuff(player);
        }
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (event.getRayTraceResult().getType() == HitResult.Type.ENTITY
                && ((EntityHitResult) event.getRayTraceResult()).getEntity() instanceof Player player) {
            tryApplyBuff(player);
        }
    }

    private static void tryApplyBuff(Player player) {
        if (!player.isBlocking()) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(COUNTERATTACK, player.getUseItem(), player.registryAccess());
        if (level <= 0) {
            return;
        }

        player.addEffect(new MobEffectInstance(EffectReg.CRIT_RATE_BOOST, DURATION, level));
    }
}
