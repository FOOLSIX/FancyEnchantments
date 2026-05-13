package com.foolsix.fancyenchantments.enchantment.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record AddFireTimeEffect(LevelBasedValue duration) implements EnchantmentEntityEffect {
    public static final MapCodec<AddFireTimeEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    LevelBasedValue.CODEC.fieldOf("duration").forGetter(AddFireTimeEffect::duration)
            ).apply(instance, AddFireTimeEffect::new)
    );

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        int addedTicks = Math.max(0, Math.round(this.duration.calculate(enchantmentLevel) * 20.0F));
        if (addedTicks == 0) {
            return;
        }

        entity.setRemainingFireTicks(Math.max(0, entity.getRemainingFireTicks()) + addedTicks);
    }

    @Override
    public @NotNull MapCodec<AddFireTimeEffect> codec() {
        return CODEC;
    }
}
