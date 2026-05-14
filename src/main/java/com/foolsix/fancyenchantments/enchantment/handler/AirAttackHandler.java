package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.AIR_ATTACK;

@EventBusSubscriber(modid = MODID)
public final class AirAttackHandler {
    private static final float DAMAGE_MULTIPLIER = 0.3F;

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker) || attacker.fallDistance <= 0.0F) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(AIR_ATTACK, attacker);
        if (level <= 0) {
            return;
        }

        event.setAmount(event.getAmount() + attacker.fallDistance * DAMAGE_MULTIPLIER * level);
        attacker.resetFallDistance();
    }
}
