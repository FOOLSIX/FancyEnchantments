package com.foolsix.fancyenchantments.damage;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.damagesource.DamageType;

public final class FEDamageTypes {
    private FEDamageTypes() {
    }

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(FEDamageSource.GIVE_UP, FEDamageSource.GIVE_UP_DAMAGE);
        context.register(FEDamageSource.GENERAL_ENCHANTMENT, FEDamageSource.GENERAL_ENCHANTMENT_DAMAGE);
    }
}
