package com.foolsix.fancyenchantments.effect;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AttackSpeedBoost extends MobEffect {
    public static final String NAME = "attack_speed_boost";
    private static final String ID = "87afd20a-6f82-2f39-6eaf-04081b0b2f29";
    private static final ModConfig.StackingWavesOptions CONFIG = FancyEnchantments.getConfig().stackingWavesOptions;

    public AttackSpeedBoost() {
        super(MobEffectCategory.BENEFICIAL, 0x55FFFF);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, ID, CONFIG.attackSpeedMultiplier, AttributeModifier.Operation.MULTIPLY_BASE);
    }
}
