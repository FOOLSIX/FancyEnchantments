package com.foolsix.fancyenchantments.damage;

import com.foolsix.fancyenchantments.FancyEnchantments;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

public class FEDamageSource {
    public static final ResourceKey<DamageType> GIVE_UP = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(FancyEnchantments.MODID, "give_up"));
    public static final ResourceKey<DamageType> GENERAL_ENCHANTMENT = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(FancyEnchantments.MODID, "general_enchantment_damage"));

    public static final DamageType GIVE_UP_DAMAGE = new DamageType("give_up", DamageScaling.NEVER, 0.1f);
    public static final DamageType GENERAL_ENCHANTMENT_DAMAGE = new DamageType("general_enchantment_damage", DamageScaling.NEVER, 0.1f, DamageEffects.HURT);

    private static DamageSource source(@Nullable LivingEntity living, ResourceKey<DamageType> resourceKey) {
        RegistryAccess registry = living.level().registryAccess();
        Registry<DamageType> types = registry.registryOrThrow(Registries.DAMAGE_TYPE);
        return new DamageSource(types.getHolderOrThrow(resourceKey), living);
    }

    public static DamageSource giveUpDamage(LivingEntity living) {
        return source(living, GIVE_UP);
    }

    public static DamageSource enchantmentDamage(LivingEntity living) {
        return source(living, GENERAL_ENCHANTMENT);
    }
}
