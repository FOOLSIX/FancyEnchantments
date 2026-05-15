package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.DOWNWIND;

@EventBusSubscriber(modid = MODID)
public final class DownwindHandler {
    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(DOWNWIND, attacker);
        if (level <= 0) {
            return;
        }

        LivingEntity victim = event.getEntity();
        if (!victim.onGround()) {
            event.setAmount(event.getAmount() * (1.0F + (float) Config.DOWNWIND_DAMAGE_MULTIPLIER_PER_LEVEL.get().doubleValue()));
        }

        victim.teleportTo(victim.getX(), victim.getY() + 0.5D, victim.getZ());
        double downwardSpeed = victim.getDeltaMovement().y() > 0.0D ? 0.0D : -victim.getDeltaMovement().y();
        EnchUtils.pushLiving(victim, 0.0D, Config.DOWNWIND_PUSH_FORCE_MULTIPLIER.get() + downwardSpeed, 0.0D);
    }
}
