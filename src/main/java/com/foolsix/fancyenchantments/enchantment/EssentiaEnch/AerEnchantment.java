package com.foolsix.fancyenchantments.enchantment.EssentiaEnch;

import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

public final class AerEnchantment {
    private AerEnchantment() {
    }

    public static boolean is(Holder<Enchantment> enchantment) {
        return FEEnchantments.elementOf(enchantment) == ElementalEssentia.AER;
    }
}
