package com.foolsix.fancyenchantments.effect;


import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Trembling extends MobEffect {
    public static final String NAME = "trembling";
    private static final String TREMBLING_UUID = "70a33510-2106-b752-bb7a-08f695096b0a";
    private static final ModConfig.ThrillingThunderOptions CONFIG = FancyEnchantments.getConfig().thrillingThunderOptions;

    public Trembling() {
        super(MobEffectCategory.HARMFUL, 0xFAFAD2);
        super.addAttributeModifier(Attributes.MOVEMENT_SPEED, TREMBLING_UUID, -0.3, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % CONFIG.tickGap == 0;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        float health = pLivingEntity.getHealth();
        float damage = (float) ((pAmplifier + 1) * CONFIG.damageMultiplier);
        if (health > damage) {
            EnchUtils.generateSimpleParticleAroundEntity(pLivingEntity, ParticleTypes.WAX_OFF, 5, 0.5, 0.7, 0.5, 1);
            pLivingEntity.setHealth(health - damage);
        }
    }
}
