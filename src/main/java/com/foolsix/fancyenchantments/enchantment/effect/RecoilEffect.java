package com.foolsix.fancyenchantments.enchantment.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record RecoilEffect(float probabilityPerLevel, LevelBasedValue distance) implements EnchantmentEntityEffect {
    public static final MapCodec<RecoilEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.FLOAT.fieldOf("probability_per_level").forGetter(RecoilEffect::probabilityPerLevel),
                    LevelBasedValue.CODEC.fieldOf("distance").forGetter(RecoilEffect::distance)
            ).apply(instance, RecoilEffect::new)
    );

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if (entity.getRandom().nextFloat() < probabilityPerLevel * enchantmentLevel) {
            entity.push(0, distance.calculate(enchantmentLevel), 0);
        }
    }

    @Override
    public @NotNull MapCodec<RecoilEffect> codec() {
        return CODEC;
    }
}
