package com.foolsix.fancyenchantments.enchantment.effect;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record LavaBurstEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<LavaBurstEffect> CODEC = MapCodec.unit(LavaBurstEffect::new);

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if (enchantmentLevel <= 0 || !(item.owner() instanceof LivingEntity attacker)) {
            return;
        }

        if (attacker.getRandom().nextDouble() >= Config.LAVA_BURST_PROBABILITY.get() * enchantmentLevel) {
            return;
        }

        level.explode(attacker, entity.getX(), entity.getY(), entity.getZ(), 0.5F * enchantmentLevel, Level.ExplosionInteraction.NONE);
        EnchUtils.generateSimpleParticleAroundEntity(entity, ParticleTypes.LAVA);
    }

    @Override
    public @NotNull MapCodec<LavaBurstEffect> codec() {
        return CODEC;
    }
}
