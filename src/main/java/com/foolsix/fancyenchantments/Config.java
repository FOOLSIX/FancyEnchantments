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
            BUILDER.comment("Afterimage: probability of dodging = extra speed(%) * multiplier.")
                    .defineInRange("Probability Multiplier", 0.5D, 0.0D, Double.MAX_VALUE);
    public static final ModConfigSpec.DoubleValue AFTERIMAGE_PROBABILITY_CAP_PER_LEVEL =
            BUILDER.comment("Afterimage: probability cap per enchantment level.")
                    .defineInRange("Probability Cap Per Level", 0.3D, 0.0D, Double.MAX_VALUE);
    public static final ModConfigSpec.DoubleValue AFTERIMAGE_PROBABILITY_MAX_CAP =
            BUILDER.comment("Afterimage: maximum dodge probability cap.")
                    .defineInRange("Probability Max Cap", 0.8D, 0.0D, 1.0D);

    public static final ModConfigSpec.DoubleValue AIR_ATTACK_DAMAGE_MULTIPLIER =
            BUILDER.comment("Air Attack: damage = fall distance * multiplier * level.")
                    .defineInRange("Damage Multiplier", 0.3D, 0.0D, Double.MAX_VALUE);

    public static final ModConfigSpec.IntValue ARMOR_FORGING_VALUE_CAP_PER_LEVEL =
            BUILDER.comment("Armor Forging: upper limit of forging value = value cap per level * level.")
                    .defineInRange("Value Cap Per Level", 1000, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue ARMOR_FORGING_ARMOR_BASE =
            BUILDER.comment("Armor Forging: increased armor = forging value / armor base * 100%.")
                    .defineInRange("Armor Base", 5000, 1, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue ARMOR_FORGING_TOUGHNESS_BASE =
            BUILDER.comment("Armor Forging: increased toughness = forging value / toughness base * 100%.")
                    .defineInRange("Toughness Base", 10000, 1, Integer.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue BEYOND_THE_FLASH_DURABILITY_CONSUMPTION_MULTIPLIER =
            BUILDER.comment("Beyond the Flash: durability consumption multiplier.")
                    .defineInRange("Durability Consumption Multiplier", 1.0D, 0.0D, Double.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue BLOOD_FEED_PROBABILITY_PER_LEVEL =
            BUILDER.comment("Blood Feed: trigger probability = probability per level * level.")
                    .defineInRange("Probability Per Level", 0.03D, 0.0D, 1.0D);
    public static final ModConfigSpec.IntValue BLOOD_FEED_CAP_PER_LEVEL =
            BUILDER.comment("Blood Feed: stored stack cap = cap per level * level.")
                    .defineInRange("Cap Per Level", 10, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue BLOOD_SACRIFICE_SELF_DAMAGE_PER_LEVEL =
            BUILDER.comment("Blood Sacrifice: damage to player per level.")
                    .defineInRange("Self Damage Per Level", 2.0D, 0.0D, Double.MAX_VALUE);
    public static final ModConfigSpec.DoubleValue BLOOD_SACRIFICE_BASE =
            BUILDER.comment("Blood Sacrifice: damage *= 1 + (base + lost health / max health) * level.")
                    .defineInRange("Multiplier", 0.1D, 0.0D, Double.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue BLOODTHIRSTY_HUNGER_MULTIPLIER =
            BUILDER.comment("Bloodthirsty: hunger value += damage value * multiplier.")
                    .defineInRange("Hunger Multiplier", 0.5D, 0.0D, Double.MAX_VALUE);
    public static final ModConfigSpec.IntValue BLOODTHIRSTY_HUNGER_UPPER_LIMIT =
            BUILDER.comment("Bloodthirsty: maximum hunger value restored by its damage effect.")
                    .defineInRange("Hunger Upper Limit", 20, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.DoubleValue BLOODTHIRSTY_SATURATION_MULTIPLIER =
            BUILDER.comment("Bloodthirsty: saturation += damage value * multiplier.")
                    .defineInRange("Saturation Multiplier", 0.2D, 0.0D, Double.MAX_VALUE);
    public static final ModConfigSpec.DoubleValue BLOODTHIRSTY_SATURATION_CAP =
            BUILDER.comment("Bloodthirsty: maximum saturation restored by its damage effect.")
                    .defineInRange("Saturation Cap", 25.0D, 0.0D, Double.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue BUBBLE_SHIELD_AIR_SUPPLY_RATIO =
            BUILDER.comment("Bubble Shield: triggering condition: air supply value >= max value * ratio.")
                    .defineInRange("Air Supply Ratio", 0.8D, 0.0D, Double.MAX_VALUE);
    public static final ModConfigSpec.DoubleValue BUBBLE_SHIELD_COST_RATIO =
            BUILDER.comment("Bubble Shield: air supply cost ratio.")
                    .defineInRange("Cost Ratio", 0.7D, 0.0D, 1.0D);
    public static final ModConfigSpec.DoubleValue BUBBLE_SHIELD_DAMAGE_MULTIPLIER =
            BUILDER.comment("Bubble Shield: damage -= air supply value * cost ratio * multiplier * level.")
                    .defineInRange("Damage Multiplier", 0.1D, 0.0D, Double.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue CHARGE_DISTANCE_MULTIPLIER =
            BUILDER.comment("Charge: push distance multiplier.")
                    .defineInRange("Distance Multiplier", 2.0D, 0.0D, Double.MAX_VALUE);
    public static final ModConfigSpec.IntValue CHARGE_INVINCIBLE_DURATION_PER_LEVEL =
            BUILDER.comment("Charge: duration = 5 + invincible duration per level * level (ticks).")
                    .defineInRange("Invincible Duration Per Level", 5, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue CONDITION_OVERLOAD_DAMAGE_MULTIPLIER =
            BUILDER.comment("Condition Overload: damage *= 1 + multiplier * level * debuff count.")
                    .defineInRange("Damage Multiplier", 0.05D, 0.0D, Double.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue CUMBERSOME_ATTACK_SPEED_REDUCER =
            BUILDER.comment("Cumbersome: attack speed *= 1 - reducer.")
                    .defineInRange("Attack Speed Reducer", 0.2D, 0.0D, 1.0D);


    public static final ModConfigSpec.DoubleValue CURSED_GAZE_BASE_DISTANCE =
            BUILDER.comment("Cursed Gaze: effective distance = min(level * base distance, 128).")
                    .defineInRange("Base Distance", 5.0D, 0.0D, Double.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue CRACKED_CROWN_DAMAGE_MULTIPLIER =
            BUILDER.comment("Cracked Crown: attacker dealt damage *= 1 + damage multiplier * level.")
                    .defineInRange("Damage Multiplier", 0.3D, 0.0D, Double.MAX_VALUE);
    public static final ModConfigSpec.DoubleValue CRACKED_CROWN_TAKEN_DAMAGE_MULTIPLIER =
            BUILDER.comment("Cracked Crown: wearer taken damage *= 1 + taken damage multiplier * level.")
                    .defineInRange("Taken Damage Multiplier", 0.3D, 0.0D, Double.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue DEDICATION_ARMOR_BONUS_MULTIPLIER =
            BUILDER.comment("Dedication: armor += stored level * armor bonus multiplier.")
                    .defineInRange("Armor Bonus Multiplier", 1.0D, 0.0D, Double.MAX_VALUE);
    public static final ModConfigSpec.DoubleValue DEDICATION_TOUGHNESS_BONUS_MULTIPLIER =
            BUILDER.comment("Dedication: toughness += stored level * toughness bonus multiplier.")
                    .defineInRange("Toughness Bonus Multiplier", 0.5D, 0.0D, Double.MAX_VALUE);
    public static final ModConfigSpec.DoubleValue DEDICATION_PROBABILITY =
            BUILDER.comment("Dedication: probability to consume the enchantment on hurt.")
                    .defineInRange("Probability", 0.001D, 0.0D, 1.0D);

    public static final ModConfigSpec.DoubleValue DELAYED_EXECUTION_STORED_DAMAGE =
            BUILDER.comment("Delayed Execution: proportion of damage to store.")
                    .defineInRange("Stored Damage", 0.8D, 0.0D, 1.0D);
    public static final ModConfigSpec.DoubleValue DELAYED_EXECUTION_DAMAGE_MULTIPLIER =
            BUILDER.comment("Delayed Execution: damage = stored damage * (1 + damage multiplier * level).")
                    .defineInRange("Damage Multiplier", 0.8D, 0.0D, Double.MAX_VALUE);


    static final ModConfigSpec SPEC = BUILDER.build();

}
