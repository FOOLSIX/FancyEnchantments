package com.foolsix.fancyenchantments.effect;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class TemplarShield extends MobEffect {
    public static final String NAME = "templar_shield";
    private static final ModConfig.EucharistOptions CONFIG = FancyEnchantments.getConfig().eucharistOptions;

    public TemplarShield() {
        super(MobEffectCategory.BENEFICIAL, 0x0);
    }

    public void effect(LivingDamageEvent e) {
        Entity victim = e.getEntity();
        if (victim instanceof LivingEntity living) {
            MobEffectInstance effectInstance = living.getEffect(this);
            if (effectInstance != null) {
                int amplifier = effectInstance.getAmplifier();
                int duration = effectInstance.getDuration();
                e.setAmount(e.getAmount() * CONFIG.damageMultiplier);
                if (amplifier > 0) {
                    living.forceAddEffect(new MobEffectInstance(this, duration, amplifier - 1), null);
                } else {
                    living.removeEffect(this);
                }
            }
        }
    }
}
