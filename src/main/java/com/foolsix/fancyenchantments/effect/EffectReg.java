package com.foolsix.fancyenchantments.effect;

import com.foolsix.fancyenchantments.FancyEnchantments;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class EffectReg {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, FancyEnchantments.MODID);
    public static final RegistryObject<MobEffect> CRIT_RATE_BOOST =
            EFFECTS.register(CritRateBoost.CRIT_RATE_BOOST_NAME, CritRateBoost::new);
    public static final RegistryObject<MobEffect> CUMBERSOME =
            EFFECTS.register(Cumbersome.CUMBERSOME_NAME, Cumbersome::new);
    public static final RegistryObject<MobEffect> TEMPLAR_SHIELD =
            EFFECTS.register(TemplarShield.NAME, TemplarShield::new);
    public static final RegistryObject<MobEffect> MELTING =
            EFFECTS.register(Melting.NAME, Melting::new);
    public static final RegistryObject<MobEffect> ATTACK_SPEED_BOOST =
            EFFECTS.register(AttackSpeedBoost.NAME, AttackSpeedBoost::new);
    public static final RegistryObject<MobEffect> INVINCIBLE =
            EFFECTS.register(Invincible.NAME, Invincible::new);
    public static final RegistryObject<MobEffect> PRISON_CAGE =
            EFFECTS.register(PrisonCage.NAME, PrisonCage::new);
}
