package com.foolsix.fancyenchantments;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_INCOMPATIBILITY =
            BUILDER.comment("Whether Fancy Enchantments elemental opposites are incompatible.")
                    .define("Enable Incompatibility", true);
    public static final ModConfigSpec.BooleanValue ENABLE_MOD_BOOK_TEXTURE =
            BUILDER.comment("Whether enchanted books use Fancy Enchantments custom element textures on the client.")
                    .define("Enable Mod Book Texture", true);

    public static final ModConfigSpec.IntValue ADVANCED_FLAME_PROJECTILE_FIRE_SECONDS =
            BUILDER.comment("Advanced Flame: projectile fire duration in seconds.")
                    .defineInRange("Projectile Fire Seconds", 30, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue ADVANCED_FLAME_HIT_FIRE_SECONDS =
            BUILDER.comment("Advanced Flame: extra fire duration applied on hit in seconds.")
                    .defineInRange("Hit Fire Seconds", 8, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue AFTERIMAGE_PROBABILITY_MULTIPLIER =
            BUILDER.comment("Afterimage: dodge probability multiplier based on extra move speed.")
                    .defineInRange("Probability Multiplier", 0.5D, 0.0D, Double.MAX_VALUE);
    public static final ModConfigSpec.DoubleValue AFTERIMAGE_PROBABILITY_CAP_PER_LEVEL =
            BUILDER.comment("Afterimage: dodge probability cap gained per enchantment level.")
                    .defineInRange("Probability Cap Per Level", 0.3D, 0.0D, Double.MAX_VALUE);
    public static final ModConfigSpec.DoubleValue AFTERIMAGE_PROBABILITY_MAX_CAP =
            BUILDER.comment("Afterimage: maximum dodge probability cap.")
                    .defineInRange("Probability Max Cap", 0.8D, 0.0D, 1.0D);

    public static final ModConfigSpec.DoubleValue AIR_ATTACK_DAMAGE_MULTIPLIER =
            BUILDER.comment("Air Attack: bonus damage multiplier per level based on fall distance.")
                    .defineInRange("Damage Multiplier", 0.3D, 0.0D, Double.MAX_VALUE);

    public static final ModConfigSpec.IntValue ARMOR_FORGING_VALUE_CAP_PER_LEVEL =
            BUILDER.comment("Armor Forging: forging value cap per enchantment level.")
                    .defineInRange("Value Cap Per Level", 1000, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue ARMOR_FORGING_ARMOR_BASE =
            BUILDER.comment("Armor Forging: armor bonus divisor base.")
                    .defineInRange("Armor Base", 5000, 1, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue ARMOR_FORGING_TOUGHNESS_BASE =
            BUILDER.comment("Armor Forging: toughness bonus divisor base.")
                    .defineInRange("Toughness Base", 10000, 1, Integer.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue BEYOND_THE_FLASH_DURABILITY_CONSUMPTION_MULTIPLIER =
            BUILDER.comment("Beyond the Flash: durability consumption multiplier.")
                    .defineInRange("Durability Consumption Multiplier", 1.0D, 0.0D, Double.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue BLOOD_FEED_PROBABILITY_PER_LEVEL =
            BUILDER.comment("Blood Feed: trigger probability per enchantment level.")
                    .defineInRange("Probability Per Level", 0.03D, 0.0D, 1.0D);
    public static final ModConfigSpec.IntValue BLOOD_FEED_CAP_PER_LEVEL =
            BUILDER.comment("Blood Feed: stored stack cap per enchantment level.")
                    .defineInRange("Cap Per Level", 10, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue BLOOD_SACRIFICE_SELF_DAMAGE_PER_LEVEL =
            BUILDER.comment("Blood Sacrifice: self-damage per enchantment level.")
                    .defineInRange("Self Damage Per Level", 2.0D, 0.0D, Double.MAX_VALUE);
    public static final ModConfigSpec.DoubleValue BLOOD_SACRIFICE_BASE =
            BUILDER.comment("Blood Sacrifice: base damage multiplier contribution.")
                    .defineInRange("Base", 0.1D, 0.0D, Double.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue BLOODTHIRSTY_HUNGER_MULTIPLIER =
            BUILDER.comment("Bloodthirsty: hunger restored per point of dealt damage.")
                    .defineInRange("Hunger Multiplier", 0.5D, 0.0D, Double.MAX_VALUE);
    public static final ModConfigSpec.IntValue BLOODTHIRSTY_HUNGER_UPPER_LIMIT =
            BUILDER.comment("Bloodthirsty: maximum hunger level restored by its damage effect.")
                    .defineInRange("Hunger Upper Limit", 20, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.DoubleValue BLOODTHIRSTY_SATURATION_MULTIPLIER =
            BUILDER.comment("Bloodthirsty: saturation restored per point of dealt damage.")
                    .defineInRange("Saturation Multiplier", 0.2D, 0.0D, Double.MAX_VALUE);
    public static final ModConfigSpec.DoubleValue BLOODTHIRSTY_SATURATION_CAP =
            BUILDER.comment("Bloodthirsty: maximum saturation restored by its damage effect.")
                    .defineInRange("Saturation Cap", 25.0D, 0.0D, Double.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue BUBBLE_SHIELD_AIR_SUPPLY_RATIO =
            BUILDER.comment("Bubble Shield: trigger threshold ratio based on current air supply.")
                    .defineInRange("Air Supply Ratio", 0.8D, 0.0D, Double.MAX_VALUE);
    public static final ModConfigSpec.DoubleValue BUBBLE_SHIELD_COST_RATIO =
            BUILDER.comment("Bubble Shield: air supply cost ratio.")
                    .defineInRange("Cost Ratio", 0.7D, 0.0D, 1.0D);
    public static final ModConfigSpec.DoubleValue BUBBLE_SHIELD_DAMAGE_MULTIPLIER =
            BUILDER.comment("Bubble Shield: damage reduction multiplier.")
                    .defineInRange("Damage Multiplier", 0.1D, 0.0D, Double.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue CHARGE_DISTANCE_MULTIPLIER =
            BUILDER.comment("Charge: push distance multiplier.")
                    .defineInRange("Distance Multiplier", 2.0D, 0.0D, Double.MAX_VALUE);
    public static final ModConfigSpec.IntValue CHARGE_INVINCIBLE_DURATION_PER_LEVEL =
            BUILDER.comment("Charge: additional invincible duration per enchantment level in ticks.")
                    .defineInRange("Invincible Duration Per Level", 5, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue CONDITION_OVERLOAD_DAMAGE_MULTIPLIER =
            BUILDER.comment("Condition Overload: damage multiplier per debuff and per level.")
                    .defineInRange("Damage Multiplier", 0.05D, 0.0D, Double.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();

}
