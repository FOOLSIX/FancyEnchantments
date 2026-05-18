package com.foolsix.fancyenchantments.enchantment.effect;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public record PurificationSlashEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<PurificationSlashEffect> CODEC = MapCodec.unit(new PurificationSlashEffect());

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if (!(entity instanceof LivingEntity victim)) {
            return;
        }

        Entity attacker = item.owner();
        if (!(attacker instanceof Player)) {
            return;
        }

        if (victim instanceof Player || (victim instanceof OwnableEntity ownable
                && Objects.equals(ownable.getOwnerUUID(), attacker.getUUID()))) {
            victim.clearFire();
            List<Holder<MobEffect>> harmfulEffects = victim.getActiveEffects().stream()
                    .map(MobEffectInstance::getEffect)
                    .filter(holder -> holder.value().getCategory() == MobEffectCategory.HARMFUL)
                    .toList();
            harmfulEffects.forEach(victim::removeEffect);
            EnchUtils.generateSimpleParticleAroundEntity(victim, ParticleTypes.HAPPY_VILLAGER);
        }
    }

    @Override
    public @NotNull MapCodec<PurificationSlashEffect> codec() {
        return CODEC;
    }
}
