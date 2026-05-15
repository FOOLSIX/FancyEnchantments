package com.foolsix.fancyenchantments.enchantment.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record ApplyMobEffectIfAbsentEffect(Holder<MobEffect> mobEffect, LevelBasedValue duration) implements EnchantmentEntityEffect {
    public static final MapCodec<ApplyMobEffectIfAbsentEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    MobEffect.CODEC.fieldOf("mob_effect").forGetter(ApplyMobEffectIfAbsentEffect::mobEffect),
                    LevelBasedValue.CODEC.fieldOf("duration").forGetter(ApplyMobEffectIfAbsentEffect::duration)
            ).apply(instance, ApplyMobEffectIfAbsentEffect::new)
    );

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if (!(item.owner() instanceof LivingEntity attacker) || !(entity instanceof LivingEntity living)) {
            return;
        }
        if (!(attacker instanceof net.minecraft.world.entity.player.Player player) || player.getAttackStrengthScale(0.5F) <= 0.95F) {
            return;
        }
        if (living.hasEffect(this.mobEffect)) {
            return;
        }

        int durationTicks = Math.max(0, Math.round(this.duration.calculate(enchantmentLevel) * 20.0F));
        living.addEffect(new MobEffectInstance(this.mobEffect, durationTicks, enchantmentLevel - 1));
    }

    @Override
    public @NotNull MapCodec<ApplyMobEffectIfAbsentEffect> codec() {
        return CODEC;
    }
}
