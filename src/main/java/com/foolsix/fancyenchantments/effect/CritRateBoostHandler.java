package com.foolsix.fancyenchantments.effect;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

@EventBusSubscriber(modid = MODID)
public final class CritRateBoostHandler {
    private static final float DAMAGE_MULTIPLIER = 1.5F;
    private static final double CHANCE_PER_AMPLIFIER = 0.2D;
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        Entity attacker = event.getSource().getEntity();
        if (!(attacker instanceof LivingEntity living)) {
            return;
        }

        MobEffectInstance effectInstance = living.getEffect(EffectReg.CRIT_RATE_BOOST);
        if (effectInstance == null || living.getRandom().nextDouble() >= CHANCE_PER_AMPLIFIER * effectInstance.getAmplifier()) {
            return;
        }

        event.setAmount(event.getAmount() * DAMAGE_MULTIPLIER);
        EnchUtils.generateSimpleParticleAroundEntity(event.getEntity(), ParticleTypes.CRIT);
    }
}
