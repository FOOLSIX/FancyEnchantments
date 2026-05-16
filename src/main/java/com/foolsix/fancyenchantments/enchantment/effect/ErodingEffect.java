package com.foolsix.fancyenchantments.enchantment.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record ErodingEffect(float probabilityPerLevel, int damageMultiplier) implements net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect {
    public static final MapCodec<ErodingEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    com.mojang.serialization.Codec.FLOAT.fieldOf("probability_per_level").forGetter(ErodingEffect::probabilityPerLevel),
                    com.mojang.serialization.Codec.INT.fieldOf("damage_multiplier").forGetter(ErodingEffect::damageMultiplier)
            ).apply(instance, ErodingEffect::new)
    );

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if (enchantmentLevel <= 0 || item.owner() == null) {
            return;
        }
        if (item.owner().getRandom().nextFloat() >= this.probabilityPerLevel * enchantmentLevel) {
            return;
        }

        ServerPlayer serverPlayer = item.owner() instanceof ServerPlayer player ? player : null;
        item.itemStack().hurtAndBreak(this.damageMultiplier * enchantmentLevel, level, serverPlayer, item.onBreak());
    }

    @Override
    public @NotNull MapCodec<ErodingEffect> codec() {
        return CODEC;
    }
}
