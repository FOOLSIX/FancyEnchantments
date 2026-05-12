package com.foolsix.fancyenchantments.enchantment.EssentiaEnch;

import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

public final class IgnisEnchantment {
    private IgnisEnchantment() {
    }

    public static boolean is(Holder<Enchantment> enchantment) {
        return FEEnchantments.elementOf(enchantment) == ElementalEssentia.IGNIS;
    }
}
