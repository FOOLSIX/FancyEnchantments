package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.SELF_IMMOLATION;

@EventBusSubscriber(modid = MODID)
public final class SelfImmolationHandler {
    private static final double BASE_PROBABILITY = 0.05;
    private static final double PROBABILITY_WITH_FIRE = 0.5;
    private static final int FIRE_TIME = 3;

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            int level = EnchUtils.getEnchantmentLevel(SELF_IMMOLATION, attacker);
            if (level <= 0) return;

            double rand = attacker.getRandom().nextDouble();
            if (event.getEntity().isOnFire() && rand < PROBABILITY_WITH_FIRE || rand < BASE_PROBABILITY) {
                attacker.setRemainingFireTicks(FIRE_TIME * 20);
            }
        }
    }
}
