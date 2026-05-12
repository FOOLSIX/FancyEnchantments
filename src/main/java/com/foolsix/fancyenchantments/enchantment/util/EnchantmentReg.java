package com.foolsix.fancyenchantments.enchantment.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

public final class EnchantmentReg {
    public static final ResourceKey<Enchantment> ADVANCED_FIRE_ASPECT = key("advanced_fire_aspect");

    public static final List<ResourceKey<Enchantment>> ALL = List.of(
            key("abyssal_maelstrom"),
            ADVANCED_FIRE_ASPECT,
            key("advanced_flame"),
            key("advanced_looting"),
            key("advanced_protection"),
            key("advanced_sharpness"),
            key("afterimage"),
            key("ailment_devourer"),
            key("air_attack"),
            key("armor_forging"),
            key("beyond_the_flash"),
            key("blessed_wind"),
            key("blind_loyalty"),
            key("blood_feed"),
            key("blood_sacrifice"),
            key("bloodthirsty"),
            key("bubble_shield"),
            key("bullying"),
            key("calmer"),
            key("charge"),
            key("condition_overload"),
            key("counterattack"),
            key("cracked_crown"),
            key("cumbersome"),
            key("cursed_gaze"),
            key("dedication"),
            key("delayed_execution"),
            key("dexterity"),
            key("dominion"),
            key("downwind"),
            key("drowning"),
            key("duellists_prerogative"),
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
