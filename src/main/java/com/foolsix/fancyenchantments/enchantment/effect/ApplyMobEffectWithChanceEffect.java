package com.foolsix.fancyenchantments.enchantment.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record ApplyMobEffectWithChanceEffect(Holder<MobEffect> mobEffect, float probability, LevelBasedValue duration) implements EnchantmentEntityEffect {
    public static final MapCodec<ApplyMobEffectWithChanceEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    MobEffect.CODEC.fieldOf("mob_effect").forGetter(ApplyMobEffectWithChanceEffect::mobEffect),
                    ExtraCodecs.POSITIVE_FLOAT.fieldOf("probability").forGetter(ApplyMobEffectWithChanceEffect::probability),
                    LevelBasedValue.CODEC.fieldOf("duration").forGetter(ApplyMobEffectWithChanceEffect::duration)
            ).apply(instance, ApplyMobEffectWithChanceEffect::new)
    );

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if (!(entity instanceof LivingEntity living) || enchantmentLevel <= 0 || living.getRandom().nextDouble() >= this.probability) {
            return;
        }

        int durationTicks = Math.max(0, Math.round(this.duration.calculate(enchantmentLevel) * 20.0F));
        living.addEffect(new MobEffectInstance(this.mobEffect, durationTicks, enchantmentLevel - 1));
    }

    @Override
    public @NotNull MapCodec<ApplyMobEffectWithChanceEffect> codec() {
        return CODEC;
    }
}
