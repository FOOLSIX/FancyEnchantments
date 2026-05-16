package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.FEARLESS_CHALLENGER;

@EventBusSubscriber(modid = MODID)
public final class FearlessChallengerHandler {
    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player) || player.getHealth() <= 1.0F) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(FEARLESS_CHALLENGER, player);
        if (level <= 0) {
            return;
        }

        LivingEntity victim = event.getEntity();
        double ratio = victim.getHealth() / player.getHealth();
        if (ratio <= Config.FEARLESS_CHALLENGER_HP_CONDITION.get()) {
            return;
        }

        double damageMultiplier = Math.min(Config.FEARLESS_CHALLENGER_CAP.get(), Config.FEARLESS_CHALLENGER_MULTIPLIER.get() * ratio * level);
        event.setAmount((float) (event.getAmount() * damageMultiplier));
    }
}
