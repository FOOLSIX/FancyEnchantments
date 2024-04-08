package com.foolsix.fancyenchantments.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class CritRateBoost extends MobEffect {

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
                if (Math.random() < 0.2f * amplifier) {
                    e.setAmount(e.getAmount() * 1.5f);
                    ((ServerLevel) attacker.level).sendParticles(ParticleTypes.CRIT, victim.getX(), victim.getY(), victim.getZ(), 10, 0.2D, 0.7D, 0.1D, 0);
                }
            }
        }
    }
}
