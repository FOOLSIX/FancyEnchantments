package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.CRACKED_CROWN;

@EventBusSubscriber(modid = MODID)
public final class CrackedCrownHandler {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity victim = event.getEntity();
        int level = EnchUtils.getEnchantmentLevel(CRACKED_CROWN, victim);
        if (level > 0) {
            event.setAmount(event.getAmount() * (float) (1.0D + Config.CRACKED_CROWN_TAKEN_DAMAGE_MULTIPLIER.get() * level));
        }

        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            int attackerLevel = EnchUtils.getEnchantmentLevel(CRACKED_CROWN, attacker);
            if (attackerLevel > 0) {
                event.setAmount(event.getAmount() * (float) (1.0D + Config.CRACKED_CROWN_DAMAGE_MULTIPLIER.get() * attackerLevel));
            }
        }
    }
}
