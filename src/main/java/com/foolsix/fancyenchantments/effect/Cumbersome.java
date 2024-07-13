package com.foolsix.fancyenchantments.effect;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class Cumbersome extends MobEffect {
    public static final String CUMBERSOME_NAME = "cumbersome";
    public static final UUID CUMBERSOME_UUID = UUID.fromString("ce0565a4-c0e0-4a8b-902a-722516749f11");
    public static final ModConfig.CumbersomeOptions CONFIG = FancyEnchantments.getConfig().cumbersomeOptions;

    public Cumbersome() {
        super(MobEffectCategory.HARMFUL, 0x24B262);
        this.addAttributeModifier(Attributes.ATTACK_SPEED,
                                   CUMBERSOME_UUID.toString(),
                                   -CONFIG.atkSpeedReducer,
                                   AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

}
