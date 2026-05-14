package com.foolsix.fancyenchantments.enchantment.effect;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record BullyingEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<BullyingEffect> CODEC = MapCodec.unit(BullyingEffect::new);

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if (!(item.owner() instanceof LivingEntity attacker) || !(entity instanceof LivingEntity living) || !living.isBaby() || !living.isAlive()) {
            return;
        }

        living.setHealth(0.0F);
    }

    @Override
    public @NotNull MapCodec<BullyingEffect> codec() {
        return CODEC;
    }
}
