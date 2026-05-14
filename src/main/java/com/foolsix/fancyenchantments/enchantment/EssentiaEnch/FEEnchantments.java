package com.foolsix.fancyenchantments.enchantment.EssentiaEnch;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public final class FEEnchantments {
    private static final int DEFAULT_MAX_LEVEL = 1;
    private static final int[] EMPTY_CONDITION = new int[Element.values().length];

    private static final Map<ResourceKey<Enchantment>, FEBaseEnchantment> DEFINITIONS =
            com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.ALL.stream()
                    .collect(java.util.stream.Collectors.toUnmodifiableMap(key -> key, FEEnchantments::definition));

    private FEEnchantments() {
    }

    public static Collection<FEBaseEnchantment> all() {
        return DEFINITIONS.values();
    }

    public static Optional<FEBaseEnchantment> get(ResourceKey<Enchantment> key) {
        return Optional.ofNullable(DEFINITIONS.get(key));
    }

    public static Optional<FEBaseEnchantment> get(Holder<Enchantment> enchantment) {
        return enchantment.unwrapKey().flatMap(FEEnchantments::get);
    }

    public static boolean isFancyEnchantment(Holder<Enchantment> enchantment) {
        return get(enchantment).isPresent();
    }

    private static FEBaseEnchantment definition(ResourceKey<Enchantment> key) {
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.BLIND_LOYALTY)) {
            return new FEBaseEnchantment(key, null, 1, false, true, true, true, true, 0.0D, EMPTY_CONDITION);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.BLOOD_FEED)) {
            int[] condition = new int[Element.values().length];
            condition[Element.TWISTED.ordinal()] = 5;
            condition[Element.AQUA.ordinal()] = 5;
            return new FEBaseEnchantment(key, null, 3, true, false, true, false, true, 0.1D, condition);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.BLOOD_SACRIFICE)) {
            int[] condition = new int[Element.values().length];
            condition[Element.TWISTED.ordinal()] = 3;
            return new FEBaseEnchantment(key, Element.TWISTED, 3, true, false, true, false, true, 0.1D, condition);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.BLOODTHIRSTY)) {
            return new FEBaseEnchantment(key, Element.TWISTED, 1, true, true, true, true, true, 0.0D, EMPTY_CONDITION);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.BUBBLE_SHIELD)) {
            return new FEBaseEnchantment(key, Element.AQUA, 3, false, true, true, true, true, 0.0D, EMPTY_CONDITION);
        }
        return new FEBaseEnchantment(key, null, DEFAULT_MAX_LEVEL, false, true, true, true, true, 0.0D, EMPTY_CONDITION);
    }
}
