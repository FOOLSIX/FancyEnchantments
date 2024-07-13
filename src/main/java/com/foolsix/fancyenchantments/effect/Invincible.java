package com.foolsix.fancyenchantments.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.MobEffectEvent;

public class Invincible extends MobEffect {
    public static final String NAME = "invincible";

    public Invincible() {
        super(MobEffectCategory.BENEFICIAL, 0xe69c21);
    }

    public void onAddEffect(MobEffectEvent.Added e) {
        LivingEntity living = e.getEntity();
        if (living == null) return;
        if (e.getEffectInstance().getEffect() == EffectReg.INVINCIBLE.get()) {
            living.setInvulnerable(true);
        }
    }

    public void onRemoveEffect(MobEffectEvent.Remove e) {
        LivingEntity living = e.getEntity();
        if (living == null || e.getEffect() == null) return;
        if (e.getEffect() == EffectReg.INVINCIBLE.get()) {
            living.setInvulnerable(false);
        }
    }

    public void onExpire(MobEffectEvent.Expired e) {
        LivingEntity living = e.getEntity();
        if (living == null || e.getEffectInstance() == null) return;
        MobEffect effect = e.getEffectInstance().getEffect();
        if (effect == EffectReg.INVINCIBLE.get()) {
            living.setInvulnerable(false);
        }
    }
}
