package com.foolsix.fancyenchantments.enchantment.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

public final class EnchantmentReg {
    public static final ResourceKey<Enchantment> ABYSSAL_MAELSTROM = key("abyssal_maelstrom");
    public static final ResourceKey<Enchantment> ADVANCED_FIRE_ASPECT = key("advanced_fire_aspect");
    public static final ResourceKey<Enchantment> ADVANCED_FLAME = key("advanced_flame");
    public static final ResourceKey<Enchantment> ADVANCED_LOOTING = key("advanced_looting");
    public static final ResourceKey<Enchantment> ADVANCED_PROTECTION = key("advanced_protection");
    public static final ResourceKey<Enchantment> ADVANCED_SHARPNESS = key("advanced_sharpness");
    public static final ResourceKey<Enchantment> AFTERIMAGE = key("afterimage");
    public static final ResourceKey<Enchantment> AILMENT_DEVOURER = key("ailment_devourer");
    public static final ResourceKey<Enchantment> AIR_ATTACK = key("air_attack");
    public static final ResourceKey<Enchantment> ARMOR_FORGING = key("armor_forging");
    public static final ResourceKey<Enchantment> BEYOND_THE_FLASH = key("beyond_the_flash");
    public static final ResourceKey<Enchantment> BLESSED_WIND = key("blessed_wind");
    public static final ResourceKey<Enchantment> BLIND_LOYALTY = key("blind_loyalty");
    public static final ResourceKey<Enchantment> BLOOD_FEED = key("blood_feed");
    public static final ResourceKey<Enchantment> BLOOD_SACRIFICE = key("blood_sacrifice");
    public static final ResourceKey<Enchantment> BLOODTHIRSTY = key("bloodthirsty");
    public static final ResourceKey<Enchantment> BUBBLE_SHIELD = key("bubble_shield");
    public static final ResourceKey<Enchantment> BULLYING = key("bullying");
    public static final ResourceKey<Enchantment> CALMER = key("calmer");
    public static final ResourceKey<Enchantment> CHARGE = key("charge");
    public static final ResourceKey<Enchantment> CONDITION_OVERLOAD = key("condition_overload");
    public static final ResourceKey<Enchantment> COUNTERATTACK = key("counterattack");
    public static final ResourceKey<Enchantment> CRACKED_CROWN = key("cracked_crown");
    public static final ResourceKey<Enchantment> CUMBERSOME = key("cumbersome");
    public static final ResourceKey<Enchantment> CURSED_GAZE = key("cursed_gaze");
    public static final ResourceKey<Enchantment> DEDICATION = key("dedication");
    public static final ResourceKey<Enchantment> DELAYED_EXECUTION = key("delayed_execution");
    public static final ResourceKey<Enchantment> DEXTERITY = key("dexterity");
    public static final ResourceKey<Enchantment> DOMINION = key("dominion");
    public static final ResourceKey<Enchantment> DOWNWIND = key("downwind");
    public static final ResourceKey<Enchantment> DROWNING = key("drowning");
    public static final ResourceKey<Enchantment> DUELLISTS_PREROGATIVE = key("duellists_prerogative");
    public static final ResourceKey<Enchantment> EATER_OF_SOULS = key("eater_of_souls");
    public static final ResourceKey<Enchantment> EMPATHY = key("empathy");
    public static final ResourceKey<Enchantment> ERODING = key("eroding");
    public static final ResourceKey<Enchantment> EUCHARIST = key("eucharist");
    public static final ResourceKey<Enchantment> FALLING_STONE = key("falling_stone");
    public static final ResourceKey<Enchantment> FEARLESS_CHALLENGER = key("fearless_challenger");
    public static final ResourceKey<Enchantment> FEATHER_FALL = key("feather_fall");
    public static final ResourceKey<Enchantment> FEINT_ATTACK = key("feint_attack");
    public static final ResourceKey<Enchantment> FIRE_DISASTER = key("fire_disaster");
    public static final ResourceKey<Enchantment> FLOATING = key("floating");
    public static final ResourceKey<Enchantment> FROZEN_HEART = key("frozen_heart");
    public static final ResourceKey<Enchantment> GALE = key("gale");
    public static final ResourceKey<Enchantment> GIFT_OF_FIRE = key("gift_of_fire");
    public static final ResourceKey<Enchantment> GREED_SUPREME_LOOTING = key("greed_supreme_looting");
    public static final ResourceKey<Enchantment> HEAVY_ARROW = key("heavy_arrow");
    public static final ResourceKey<Enchantment> HEAVY_BLOW = key("heavy_blow");
    public static final ResourceKey<Enchantment> HUNGRY = key("hungry");
    public static final ResourceKey<Enchantment> ICY_BURST = key("icy_burst");
    public static final ResourceKey<Enchantment> LAVA_BURST = key("lava_burst");
    public static final ResourceKey<Enchantment> LIGHTNESS = key("lightness");
    public static final ResourceKey<Enchantment> LITHIC_SIPHON = key("lithic_siphon");
    public static final ResourceKey<Enchantment> MELTER = key("melter");
    public static final ResourceKey<Enchantment> MOUNTAIN_SUPREME_PROTECTION = key("mountain_supreme_protection");
    public static final ResourceKey<Enchantment> MULTIPLE_SHOT = key("multiple_shot");
    public static final ResourceKey<Enchantment> NIGHTMARE = key("nightmare");
    public static final ResourceKey<Enchantment> NIRVANA = key("nirvana");
    public static final ResourceKey<Enchantment> OCEAN_CURRENT = key("ocean_current");
    public static final ResourceKey<Enchantment> OVERFLOW = key("overflow");
    public static final ResourceKey<Enchantment> OVER_HEALING = key("over_healing");
    public static final ResourceKey<Enchantment> PALADINS_SHIELD = key("paladins_shield");

    public static final List<ResourceKey<Enchantment>> ALL = List.of(
            ABYSSAL_MAELSTROM,
            ADVANCED_FIRE_ASPECT,
            ADVANCED_FLAME,
            ADVANCED_LOOTING,
            ADVANCED_PROTECTION,
            ADVANCED_SHARPNESS,
            AFTERIMAGE,
            AILMENT_DEVOURER,
            AIR_ATTACK,
            ARMOR_FORGING,
            BEYOND_THE_FLASH,
            BLESSED_WIND,
            BLIND_LOYALTY,
            BLOOD_FEED,
            BLOOD_SACRIFICE,
            BLOODTHIRSTY,
            BUBBLE_SHIELD,
            BULLYING,
            CALMER,
            CHARGE,
            CONDITION_OVERLOAD,
            COUNTERATTACK,
            CRACKED_CROWN,
            CUMBERSOME,
            CURSED_GAZE,
            DEDICATION,
            DELAYED_EXECUTION,
            DEXTERITY,
            DOMINION,
            DOWNWIND,
            DROWNING,
            DUELLISTS_PREROGATIVE,
            EATER_OF_SOULS,
            EMPATHY,
            ERODING,
            EUCHARIST,
            FALLING_STONE,
            FEARLESS_CHALLENGER,
            FEATHER_FALL,
            FEINT_ATTACK,
            FIRE_DISASTER,
            FLOATING,
            FROZEN_HEART,
            GALE,
            GIFT_OF_FIRE,
            GREED_SUPREME_LOOTING,
            HEAVY_ARROW,
            HEAVY_BLOW,
            HUNGRY,
            ICY_BURST,
            LAVA_BURST,
            LIGHTNESS,
            LITHIC_SIPHON,
            MELTER,
            MOUNTAIN_SUPREME_PROTECTION,
            MULTIPLE_SHOT,
            NIGHTMARE,
            NIRVANA,
            OCEAN_CURRENT,
            OVERFLOW,
            OVER_HEALING,
            PALADINS_SHIELD,
            key("pervert"),
            key("pure_fate"),
            key("purification_slash"),
            key("purifying"),
            key("pyromaniac"),
            key("recoil"),
            key("reflecting"),
            key("rocket_jump"),
            key("rolling_stone"),
            key("sacred_supreme_sharpness"),
            key("sander"),
            key("self_immolation"),
            key("sharp_rock"),
            key("sighs_of_ashes"),
            key("solid_as_a_rock"),
            key("spreading_spores"),
            key("stacking_waves"),
            key("standing_wall"),
            key("streamline"),
            key("the_fallen"),
            key("thrilling_thunder"),
            key("unyielding_spirit"),
            key("wind_blade"),
            key("wind_fire_wheels")
    );

    private EnchantmentReg() {
    }

    public static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(MODID, name));
    }
}
