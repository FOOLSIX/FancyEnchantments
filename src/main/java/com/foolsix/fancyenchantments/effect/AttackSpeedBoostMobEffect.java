package com.foolsix.fancyenchantments.effect;

import com.foolsix.fancyenchantments.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

public final class AttackSpeedBoostMobEffect extends MobEffect {
    public static final String NAME = "attack_speed_boost";
    private static final ResourceLocation ATTACK_SPEED_ID = ResourceLocation.fromNamespaceAndPath(MODID, "attack_speed_boost/attack_speed");

    public AttackSpeedBoostMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x55FFFF);
    }

    @Override
    public void addAttributeModifiers(AttributeMap attributeMap, int amplifier) {
        AttributeInstance attribute = attributeMap.getInstance(Attributes.ATTACK_SPEED);
        if (attribute == null) {
            return;
        }

        attribute.removeModifier(ATTACK_SPEED_ID);
        attribute.addPermanentModifier(
                new AttributeModifier(
                        ATTACK_SPEED_ID,
                        Config.STACKING_WAVES_ATTACK_SPEED_MULTIPLIER.get() * (amplifier + 1),
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                )
        );
    }

    @Override
    public void removeAttributeModifiers(AttributeMap attributeMap) {
        AttributeInstance attribute = attributeMap.getInstance(Attributes.ATTACK_SPEED);
        if (attribute != null) {
            attribute.removeModifier(ATTACK_SPEED_ID);
        }
    }
}
