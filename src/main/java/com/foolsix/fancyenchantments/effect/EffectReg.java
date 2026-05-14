package com.foolsix.fancyenchantments.effect;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

public final class EffectReg {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, MODID);

    public static final DeferredHolder<MobEffect, CritRateBoost> CRIT_RATE_BOOST =
            EFFECTS.register(CritRateBoost.NAME, CritRateBoost::new);
    public static final DeferredHolder<MobEffect, SimpleMobEffect> CUMBERSOME =
            registerHarmful("cumbersome", 0x808080);
    public static final DeferredHolder<MobEffect, SimpleMobEffect> TEMPLAR_SHIELD =
            registerBeneficial("templar_shield", 0xFFF2AA);
    public static final DeferredHolder<MobEffect, SimpleMobEffect> MELTING =
            registerHarmful("melting", 0xFF7A00);
    public static final DeferredHolder<MobEffect, SimpleMobEffect> ATTACK_SPEED_BOOST =
            registerBeneficial("attack_speed_boost", 0x55FFFF);
    public static final DeferredHolder<MobEffect, Invincible> INVINCIBLE =
            EFFECTS.register(Invincible.NAME, Invincible::new);
    public static final DeferredHolder<MobEffect, SimpleMobEffect> PRISON_CAGE =
            registerHarmful("prison_cage", 0x342A55);
    public static final DeferredHolder<MobEffect, SimpleMobEffect> TREMBLING =
            registerHarmful("trembling", 0xB7B7B7);
    public static final DeferredHolder<MobEffect, Maelstrom> MAELSTROM =
            EFFECTS.register("maelstrom", Maelstrom::new);

    private EffectReg() {
    }

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }

    private static DeferredHolder<MobEffect, SimpleMobEffect> registerBeneficial(String name, int color) {
        return EFFECTS.register(name, () -> new SimpleMobEffect(MobEffectCategory.BENEFICIAL, color));
    }

    private static DeferredHolder<MobEffect, SimpleMobEffect> registerHarmful(String name, int color) {
        return EFFECTS.register(name, () -> new SimpleMobEffect(MobEffectCategory.HARMFUL, color));
    }
}
