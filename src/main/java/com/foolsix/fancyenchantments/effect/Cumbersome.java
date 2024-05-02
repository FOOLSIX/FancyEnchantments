package com.foolsix.fancyenchantments.effect;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import static com.foolsix.fancyenchantments.effect.EffectIDs.CUMBERSOME_UUID;

public class Cumbersome extends MobEffect {
    public static final ModConfig.CumbersomeOptions CONFIG = FancyEnchantments.getConfig().cumbersomeOptions;

    public Cumbersome() {
        super(MobEffectCategory.HARMFUL, 0x24B262);
        super.addAttributeModifier(Attributes.ATTACK_SPEED,
                                   CUMBERSOME_UUID.toString(),
                                   -CONFIG.atkSpeedReducer / 2,
                                   AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
