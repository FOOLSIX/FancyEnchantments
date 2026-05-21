package com.foolsix.fancyenchantments.enchantment.EssentiaEnch;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public final class FEEnchantments {
    private static final int DEFAULT_MAX_LEVEL = 1;

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
            return new FEBaseEnchantment(key, null, 1, false, true, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.BLOOD_FEED)) {
            return new FEBaseEnchantment(key, null, 3, true, false, true, false, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.BLOOD_SACRIFICE)) {
            return new FEBaseEnchantment(key, Element.TWISTED, 3, true, false, true, false, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.BLOODTHIRSTY)) {
            return new FEBaseEnchantment(key, Element.TWISTED, 1, true, true, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.BUBBLE_SHIELD)) {
            return new FEBaseEnchantment(key, Element.AQUA, 3, false, true, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.DEXTERITY)) {
            return new FEBaseEnchantment(key, Element.AER, 3, false, true, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.DOMINION)) {
            return new FEBaseEnchantment(key, null, 1, true, false, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.DOWNWIND)) {
            return new FEBaseEnchantment(key, Element.AER, 3, false, true, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.DROWNING)) {
            return new FEBaseEnchantment(key, Element.AQUA, 1, false, true, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.DUELLISTS_PREROGATIVE)) {
            return new FEBaseEnchantment(key, null, 3, false, true, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.HEAVY_BLOW)) {
            return new FEBaseEnchantment(key, Element.TERRA, 3, false, true, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.HUNGRY)) {
            return new FEBaseEnchantment(key, Element.TWISTED, 3, false, true, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.ICY_BURST)) {
            return new FEBaseEnchantment(key, Element.AQUA, 3, false, true, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.LAVA_BURST)) {
            return new FEBaseEnchantment(key, null, 3, true, false, true, false, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.LIGHTNESS)) {
            return new FEBaseEnchantment(key, Element.AER, 3, false, true, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.LITHIC_SIPHON)) {
            return new FEBaseEnchantment(key, Element.TERRA, 3, true, true, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.MELTER)) {
            return new FEBaseEnchantment(key, Element.IGNIS, 3, false, true, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.MOUNTAIN_SUPREME_PROTECTION)) {
            return new FEBaseEnchantment(key, Element.TERRA, 4, true, false, true, false, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.MULTIPLE_SHOT)) {
            return new FEBaseEnchantment(key, null, 3, true, true, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.NIGHTMARE)) {
            return new FEBaseEnchantment(key, Element.TWISTED, 3, false, true, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.NIRVANA)) {
            return new FEBaseEnchantment(key, Element.IGNIS, 3, false, true, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.OCEAN_CURRENT)) {
            return new FEBaseEnchantment(key, Element.AQUA, 3, false, true, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.OVERFLOW)) {
            return new FEBaseEnchantment(key, Element.AQUA, 3, false, true, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.OVER_HEALING)) {
            return new FEBaseEnchantment(key, Element.HOLY, 3, false, true, true, true, true);
        }
        if (key.equals(com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.PALADINS_SHIELD)) {
            return new FEBaseEnchantment(key, Element.HOLY, 3, false, true, true, true, true);
        }
        return new FEBaseEnchantment(key, null, DEFAULT_MAX_LEVEL, false, true, true, true, true);
    }
}
