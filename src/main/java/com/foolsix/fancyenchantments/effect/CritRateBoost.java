package com.foolsix.fancyenchantments.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class CritRateBoost extends MobEffect {
    public static final String NAME = "crit_rate_boost";
    CritRateBoost() {
        super(MobEffectCategory.BENEFICIAL, 0xFF0000);
    }

}
