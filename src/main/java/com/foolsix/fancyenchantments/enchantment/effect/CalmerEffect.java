package com.foolsix.fancyenchantments.enchantment.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record CalmerEffect(LevelBasedValue cooldown, LevelBasedValue duration) implements EnchantmentEntityEffect {
    public static final MapCodec<CalmerEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    LevelBasedValue.CODEC.fieldOf("cooldown").forGetter(CalmerEffect::cooldown),
                    LevelBasedValue.CODEC.fieldOf("duration").forGetter(CalmerEffect::duration)
            ).apply(instance, CalmerEffect::new)
    );

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, @NotNull Entity entity, @NotNull Vec3 origin) {
        if (!(item.owner() instanceof Player player)) {
            return;
        }

        var chestItem = item.itemStack().getItem();
        if (player.getCooldowns().isOnCooldown(chestItem)) {
            return;
        }

        int cooldownTicks = Math.max(0, Math.round(this.cooldown.calculate(enchantmentLevel) * 20.0F));
        int durationTicks = Math.max(0, Math.round(this.duration.calculate(enchantmentLevel) * 20.0F));
        player.getCooldowns().addCooldown(chestItem, cooldownTicks);
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, durationTicks, enchantmentLevel - 1));
    }

    @Override
    public @NotNull MapCodec<CalmerEffect> codec() {
        return CODEC;
    }
}
