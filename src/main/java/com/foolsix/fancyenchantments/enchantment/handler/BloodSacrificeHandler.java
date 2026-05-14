package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.BLOOD_SACRIFICE;

@EventBusSubscriber(modid = MODID)
public final class BloodSacrificeHandler {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(BLOOD_SACRIFICE, player);
        if (level <= 0 || player.getAttackStrengthScale(0.5F) <= 0.95F) {
            return;
        }

        player.hurt(player.damageSources().wither(), (float) (Config.BLOOD_SACRIFICE_SELF_DAMAGE_PER_LEVEL.get() * level));
        float lostHealthRatio = 1.0F - player.getHealth() / player.getMaxHealth();
        event.setAmount(event.getAmount() * (1.0F + level * ((float) Config.BLOOD_SACRIFICE_BASE.get().doubleValue() + lostHealthRatio)));
    }
}
