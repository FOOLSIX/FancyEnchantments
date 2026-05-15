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
            key("eater_of_souls"),
            key("empathy"),
            key("eroding"),
            key("eucharist"),
            key("falling_stone"),
            key("fearless_challenger"),
            key("feather_fall"),
            key("feint_attack"),
            key("fire_disaster"),
            key("floating"),
            key("frozen_heart"),
            key("gale"),
            key("gift_of_fire"),
            key("greed_supreme_looting"),
            key("heavy_arrow"),
            key("heavy_blow"),
            key("hungry"),
            key("icy_burst"),
            key("lava_burst"),
            key("lightness"),
            key("lithic_siphon"),
            key("melter"),
            key("mountain_supreme_protection"),
            key("multiple_shot"),
            key("nightmare"),
            key("nirvana"),
            key("ocean_current"),
            key("overflow"),
            key("over_healing"),
            key("paladins_shield"),
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
