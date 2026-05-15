package com.foolsix.fancyenchantments.enchantment.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record DrowningEffect(int airSupplyValue, float damage) implements EnchantmentEntityEffect {
    public static final MapCodec<DrowningEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    com.mojang.serialization.Codec.INT.fieldOf("air_supply_value").forGetter(DrowningEffect::airSupplyValue),
                    com.mojang.serialization.Codec.FLOAT.fieldOf("damage").forGetter(DrowningEffect::damage)
            ).apply(instance, DrowningEffect::new)
    );

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if (!(entity instanceof LivingEntity living)) {
            return;
        }

        if (living.getAirSupply() <= 0 && !MobEffectUtil.hasWaterBreathing(living)) {
            living.hurt(level.damageSources().drown(), this.damage);
        }
        living.setAirSupply(this.airSupplyValue);
    }

    @Override
    public @NotNull MapCodec<DrowningEffect> codec() {
        return CODEC;
    }
}
