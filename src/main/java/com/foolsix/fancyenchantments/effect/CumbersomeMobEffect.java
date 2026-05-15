package com.foolsix.fancyenchantments.effect;

import com.foolsix.fancyenchantments.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

public final class CumbersomeMobEffect extends MobEffect {
    public static final String NAME = "cumbersome";
    private static final ResourceLocation ATTACK_SPEED_ID = ResourceLocation.fromNamespaceAndPath(MODID, "cumbersome/attack_speed");

    public CumbersomeMobEffect() {
        super(MobEffectCategory.HARMFUL, 0x24B262);
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
                        -Config.CUMBERSOME_ATTACK_SPEED_REDUCER.get(),
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
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
