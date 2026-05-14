package com.foolsix.fancyenchantments.effect;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

public class Invincible extends net.minecraft.world.effect.MobEffect {
    public static final String NAME = "invincible";

    public Invincible() {
        super(net.minecraft.world.effect.MobEffectCategory.BENEFICIAL, 0xE69C21);
    }

    public void onAddEffect(MobEffectEvent.Added event) {
        LivingEntity living = event.getEntity();
        if (event.getEffectInstance() != null && event.getEffectInstance().getEffect().is(EffectReg.INVINCIBLE.getKey())) {
            living.setInvulnerable(true);
        }
    }

    public void onRemoveEffect(MobEffectEvent.Remove event) {
        LivingEntity living = event.getEntity();
        if (event.getEffect().is(EffectReg.INVINCIBLE.getKey())) {
            living.setInvulnerable(false);
        }
    }

    public void onExpire(MobEffectEvent.Expired event) {
        LivingEntity living = event.getEntity();
        if (event.getEffectInstance() != null && event.getEffectInstance().getEffect().is(EffectReg.INVINCIBLE.getKey())) {
            living.setInvulnerable(false);
        }
    }
}
