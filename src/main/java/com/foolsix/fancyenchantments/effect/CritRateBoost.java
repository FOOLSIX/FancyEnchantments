package com.foolsix.fancyenchantments.effect;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class CritRateBoost extends MobEffect {
    public static final String CRIT_RATE_BOOST_NAME = "crit_rate_boost";

    CritRateBoost() {
        super(MobEffectCategory.BENEFICIAL, 0xFF0000);
    }

    public void effect(LivingHurtEvent e) {
        Entity attacker = e.getSource().getEntity();
        Entity victim = e.getEntity();
        if (attacker instanceof LivingEntity living) {
            MobEffectInstance effectInstance = living.getEffect(this);
            if (effectInstance != null) {
                int amplifier = effectInstance.getAmplifier();
                if (Math.random() < 0.2 * amplifier) {
                    e.setAmount(e.getAmount() * 1.5f);
                    EnchUtils.generateSimpleParticleAroundEntity(victim, ParticleTypes.CRIT);
                }
            }
        }
    }
}
