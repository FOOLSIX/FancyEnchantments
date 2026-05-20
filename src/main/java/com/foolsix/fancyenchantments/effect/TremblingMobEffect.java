package com.foolsix.fancyenchantments.effect;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

public final class TremblingMobEffect extends MobEffect {
    public static final String NAME = "trembling";
    private static final ResourceLocation MOVEMENT_SPEED_ID = ResourceLocation.fromNamespaceAndPath(MODID, "trembling/movement_speed");

    public TremblingMobEffect() {
        super(MobEffectCategory.HARMFUL, 0xFAFAD2);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED_ID, -0.3D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % Config.THRILLING_THUNDER_TICK_GAP.get() == 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity living, int amplifier) {
        float damage = (float) ((amplifier + 1) * Config.THRILLING_THUNDER_DAMAGE_MULTIPLIER.get());
        if (living.getHealth() > damage) {
            EnchUtils.generateSimpleParticleAroundEntity(living, ParticleTypes.WAX_OFF, 5, 0.5D, 0.7D, 0.5D, 1.0D);
            living.setHealth(living.getHealth() - damage);
        }
        return true;
    }
}
