package com.foolsix.fancyenchantments.enchantment.EssentiaEnch;

import com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public final class FEEnchantments {
    private static final int DEFAULT_MAX_LEVEL = 1;
    private static final int[] EMPTY_CONDITION = new int[ElementalEssentia.values().length];

    private static final Map<ResourceKey<Enchantment>, ElementalEssentia> ELEMENTS = Map.ofEntries(
            entry("abyssal_maelstrom", ElementalEssentia.AQUA),
            entry("advanced_fire_aspect", ElementalEssentia.IGNIS),
            entry("advanced_flame", ElementalEssentia.IGNIS),
            entry("afterimage", ElementalEssentia.AER),
            entry("ailment_devourer", ElementalEssentia.HOLY),
            entry("air_attack", ElementalEssentia.AER),
            entry("blessed_wind", ElementalEssentia.AER),
            entry("blood_sacrifice", ElementalEssentia.TWISTED),
            entry("bloodthirsty", ElementalEssentia.TWISTED),
            entry("bubble_shield", ElementalEssentia.AQUA),
            entry("bullying", ElementalEssentia.TWISTED),
            entry("calmer", ElementalEssentia.AQUA),
            entry("cracked_crown", ElementalEssentia.HOLY),
            entry("cumbersome", ElementalEssentia.TERRA),
            entry("cursed_gaze", ElementalEssentia.TWISTED),
            entry("dedication", ElementalEssentia.HOLY),
            entry("dexterity", ElementalEssentia.AER),
            entry("downwind", ElementalEssentia.AER),
            entry("drowning", ElementalEssentia.AQUA),
            entry("eater_of_souls", ElementalEssentia.TWISTED),
            entry("eroding", ElementalEssentia.TERRA),
            entry("eucharist", ElementalEssentia.HOLY),
            entry("falling_stone", ElementalEssentia.TERRA),
            entry("fearless_challenger", ElementalEssentia.HOLY),
            entry("feather_fall", ElementalEssentia.AER),
            entry("fire_disaster", ElementalEssentia.IGNIS),
            entry("floating", ElementalEssentia.AER),
            entry("frozen_heart", ElementalEssentia.AQUA),
            entry("gale", ElementalEssentia.AER),
            entry("gift_of_fire", ElementalEssentia.IGNIS),
            entry("greed_supreme_looting", ElementalEssentia.TWISTED),
            entry("heavy_arrow", ElementalEssentia.TERRA),
            entry("heavy_blow", ElementalEssentia.TERRA),
            entry("hungry", ElementalEssentia.TWISTED),
            entry("icy_burst", ElementalEssentia.AQUA),
            entry("lightness", ElementalEssentia.AER),
            entry("lithic_siphon", ElementalEssentia.TERRA),
            entry("melter", ElementalEssentia.IGNIS),
            entry("mountain_supreme_protection", ElementalEssentia.TERRA),
            entry("nightmare", ElementalEssentia.TWISTED),
            entry("nirvana", ElementalEssentia.IGNIS),
            entry("ocean_current", ElementalEssentia.AQUA),
            entry("overflow", ElementalEssentia.AQUA),
            entry("over_healing", ElementalEssentia.HOLY),
            entry("paladins_shield", ElementalEssentia.HOLY),
            entry("pure_fate", ElementalEssentia.HOLY),
            entry("purification_slash", ElementalEssentia.HOLY),
            entry("purifying", ElementalEssentia.HOLY),
            entry("pyromaniac", ElementalEssentia.IGNIS),
            entry("recoil", ElementalEssentia.AER),
            entry("reflecting", ElementalEssentia.AER),
            entry("rocket_jump", ElementalEssentia.IGNIS),
            entry("rolling_stone", ElementalEssentia.TERRA),
            entry("sacred_supreme_sharpness", ElementalEssentia.HOLY),
            entry("sander", ElementalEssentia.TERRA),
            entry("self_immolation", ElementalEssentia.IGNIS),
            entry("sharp_rock", ElementalEssentia.TERRA),
            entry("sighs_of_ashes", ElementalEssentia.IGNIS),
            entry("solid_as_a_rock", ElementalEssentia.TERRA),
            entry("stacking_waves", ElementalEssentia.AQUA),
            entry("standing_wall", ElementalEssentia.TERRA),
            entry("streamline", ElementalEssentia.AER),
            entry("the_fallen", ElementalEssentia.TWISTED),
            entry("unyielding_spirit", ElementalEssentia.HOLY),
            entry("wind_blade", ElementalEssentia.AER)
    );

    private static final Map<ResourceKey<Enchantment>, FEBaseEnchantment> DEFINITIONS =
            EnchantmentReg.ALL.stream().collect(java.util.stream.Collectors.toUnmodifiableMap(key -> key, FEEnchantments::definition));

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

    public static @Nullable ElementalEssentia elementOf(ResourceKey<Enchantment> key) {
        return ELEMENTS.get(key);
    }

    public static @Nullable ElementalEssentia elementOf(Holder<Enchantment> enchantment) {
        return enchantment.unwrapKey().map(FEEnchantments::elementOf).orElse(null);
    }

    public static boolean areElementsCompatible(Holder<Enchantment> first, Holder<Enchantment> second) {
        ElementalEssentia firstElement = elementOf(first);
        ElementalEssentia secondElement = elementOf(second);
        return firstElement == null || secondElement == null || !firstElement.conflictsWith(secondElement);
    }

    private static FEBaseEnchantment definition(ResourceKey<Enchantment> key) {
        return new FEBaseEnchantment(key, elementOf(key), DEFAULT_MAX_LEVEL, false, true, true, true, true, 0.0D, EMPTY_CONDITION);
    }

    private static Map.Entry<ResourceKey<Enchantment>, ElementalEssentia> entry(String name, ElementalEssentia element) {
        return Map.entry(EnchantmentReg.key(name), element);
    }
}
