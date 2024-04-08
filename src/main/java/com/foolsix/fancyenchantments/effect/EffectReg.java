package com.foolsix.fancyenchantments.effect;

import com.foolsix.fancyenchantments.FancyEnchantments;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.foolsix.fancyenchantments.effect.EffectIDs.CRIT_RATE_BOOST_NAME;

public class EffectReg {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, FancyEnchantments.MODID);
    public static final RegistryObject<MobEffect> CRIT_RATE_BOOST =
            EFFECTS.register(CRIT_RATE_BOOST_NAME, CritRateBoost::new);

}
