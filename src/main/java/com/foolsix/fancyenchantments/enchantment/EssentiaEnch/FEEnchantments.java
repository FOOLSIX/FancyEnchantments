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
        return new FEBaseEnchantment(key, null, DEFAULT_MAX_LEVEL, false, true, true, true, true, 0.0D, EMPTY_CONDITION);
    }
}
