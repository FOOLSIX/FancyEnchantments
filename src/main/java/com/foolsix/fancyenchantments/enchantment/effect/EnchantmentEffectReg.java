package com.foolsix.fancyenchantments.enchantment.effect;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

public final class EnchantmentEffectReg {
    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_EFFECT_TYPES =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, MODID);

    public static final DeferredHolder<MapCodec<? extends EnchantmentEntityEffect>, MapCodec<AddFireTimeEffect>> ADD_FIRE_TIME =
            ENTITY_EFFECT_TYPES.register("add_fire_time", () -> AddFireTimeEffect.CODEC);
    public static final DeferredHolder<MapCodec<? extends EnchantmentEntityEffect>, MapCodec<BullyingEffect>> BULLYING =
            ENTITY_EFFECT_TYPES.register("bullying", () -> BullyingEffect.CODEC);
    public static final DeferredHolder<MapCodec<? extends EnchantmentEntityEffect>, MapCodec<CalmerEffect>> CALMER =
            ENTITY_EFFECT_TYPES.register("calmer", () -> CalmerEffect.CODEC);

    private EnchantmentEffectReg() {
    }

    public static void register(IEventBus eventBus) {
        ENTITY_EFFECT_TYPES.register(eventBus);
    }
}
