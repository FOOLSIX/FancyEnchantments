package com.foolsix.fancyenchantments.effect;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;


public class Melting extends MobEffect {
    public static final String NAME = "melting";
    public static final String MELTING_UUID = "b27ea205-5488-e5cb-c6a9-b586d85a51d2";
    private static final ModConfig.MelterOptions CONFIG = FancyEnchantments.getConfig().melterOptions;
    public Melting() {
        super(MobEffectCategory.HARMFUL, 0xcb8e00);
        this.addAttributeModifier(Attributes.ARMOR, MELTING_UUID, -CONFIG.armorReducer, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
