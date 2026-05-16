package com.foolsix.fancyenchantments;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_INCOMPATIBILITY;
    public static final ModConfigSpec.BooleanValue ENABLE_MOD_BOOK_TEXTURE;

    public static final ModConfigSpec.IntValue ADVANCED_FLAME_PROJECTILE_FIRE_SECONDS;
    public static final ModConfigSpec.IntValue ADVANCED_FLAME_HIT_FIRE_SECONDS;

    public static final ModConfigSpec.DoubleValue AFTERIMAGE_PROBABILITY_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue AFTERIMAGE_PROBABILITY_CAP_PER_LEVEL;
    public static final ModConfigSpec.DoubleValue AFTERIMAGE_PROBABILITY_MAX_CAP;

    public static final ModConfigSpec.DoubleValue AIR_ATTACK_DAMAGE_MULTIPLIER;

    public static final ModConfigSpec.IntValue ARMOR_FORGING_VALUE_CAP_PER_LEVEL;
    public static final ModConfigSpec.IntValue ARMOR_FORGING_ARMOR_BASE;
    public static final ModConfigSpec.IntValue ARMOR_FORGING_TOUGHNESS_BASE;

    public static final ModConfigSpec.DoubleValue BEYOND_THE_FLASH_DURABILITY_CONSUMPTION_MULTIPLIER;

    public static final ModConfigSpec.DoubleValue BLOOD_FEED_PROBABILITY_PER_LEVEL;
    public static final ModConfigSpec.IntValue BLOOD_FEED_CAP_PER_LEVEL;

    public static final ModConfigSpec.DoubleValue BLOOD_SACRIFICE_SELF_DAMAGE_PER_LEVEL;
    public static final ModConfigSpec.DoubleValue BLOOD_SACRIFICE_BASE;

    public static final ModConfigSpec.DoubleValue BLOODTHIRSTY_HUNGER_MULTIPLIER;
    public static final ModConfigSpec.IntValue BLOODTHIRSTY_HUNGER_UPPER_LIMIT;
    public static final ModConfigSpec.DoubleValue BLOODTHIRSTY_SATURATION_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue BLOODTHIRSTY_SATURATION_CAP;

    public static final ModConfigSpec.DoubleValue BUBBLE_SHIELD_AIR_SUPPLY_RATIO;
    public static final ModConfigSpec.DoubleValue BUBBLE_SHIELD_COST_RATIO;
    public static final ModConfigSpec.DoubleValue BUBBLE_SHIELD_DAMAGE_MULTIPLIER;

    public static final ModConfigSpec.DoubleValue CHARGE_DISTANCE_MULTIPLIER;
    public static final ModConfigSpec.IntValue CHARGE_INVINCIBLE_DURATION_PER_LEVEL;

    public static final ModConfigSpec.DoubleValue CONDITION_OVERLOAD_DAMAGE_MULTIPLIER;

    public static final ModConfigSpec.DoubleValue CUMBERSOME_ATTACK_SPEED_REDUCER;

    public static final ModConfigSpec.DoubleValue CURSED_GAZE_BASE_DISTANCE;

    public static final ModConfigSpec.DoubleValue CRACKED_CROWN_DAMAGE_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue CRACKED_CROWN_TAKEN_DAMAGE_MULTIPLIER;

    public static final ModConfigSpec.DoubleValue DEDICATION_ARMOR_BONUS_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue DEDICATION_TOUGHNESS_BONUS_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue DEDICATION_PROBABILITY;

    public static final ModConfigSpec.DoubleValue DELAYED_EXECUTION_STORED_DAMAGE;
    public static final ModConfigSpec.DoubleValue DELAYED_EXECUTION_DAMAGE_MULTIPLIER;

    public static final ModConfigSpec.DoubleValue DOMINION_DAMAGE_MULTIPLIER;

    public static final ModConfigSpec.DoubleValue DOWNWIND_PUSH_FORCE_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue DOWNWIND_DAMAGE_MULTIPLIER_PER_LEVEL;

    public static final ModConfigSpec.IntValue DROWNING_AIR_SUPPLY_VALUE;
    public static final ModConfigSpec.DoubleValue DROWNING_DAMAGE;

    public static final ModConfigSpec.DoubleValue DUELLISTS_PREROGATIVE_DAMAGE_MULTIPLIER;

    public static final ModConfigSpec.DoubleValue EATER_OF_SOULS_DAMAGE_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue EATER_OF_SOULS_CAP;

    public static final ModConfigSpec.DoubleValue EMPATHY_SHOOT_POWER_MULTIPLIER;

    public static final ModConfigSpec.IntValue EUCHARIST_MINIMUM_HUNGER;
    public static final ModConfigSpec.IntValue EUCHARIST_DURATION_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue EUCHARIST_DAMAGE_MULTIPLIER;

    public static final ModConfigSpec.DoubleValue FALLING_STONE_DAMAGE_MULTIPLIER;

    static final ModConfigSpec SPEC;

    static {
        BUILDER.push("General");
        ENABLE_INCOMPATIBILITY =
                BUILDER.comment("Whether Fancy Enchantments elemental opposites are incompatible.")
                        .define("Enable Incompatibility", true);
        ENABLE_MOD_BOOK_TEXTURE =
                BUILDER.comment("Whether enchanted books use Fancy Enchantments custom element textures on the client.")
                        .define("Enable Mod Book Texture", true);
        BUILDER.pop();

        BUILDER.push("Advanced Flame");
        ADVANCED_FLAME_PROJECTILE_FIRE_SECONDS =
                BUILDER.comment("Projectile fire duration in seconds.")
                        .defineInRange("Projectile Fire Seconds", 30, 0, Integer.MAX_VALUE);
        ADVANCED_FLAME_HIT_FIRE_SECONDS =
                BUILDER.comment("Extra fire duration applied on hit in seconds.")
                        .defineInRange("Hit Fire Seconds", 8, 0, Integer.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Afterimage");
        AFTERIMAGE_PROBABILITY_MULTIPLIER =
                BUILDER.comment("Probability of dodging = extra speed(%) * multiplier.")
                        .defineInRange("Probability Multiplier", 0.5D, 0.0D, Double.MAX_VALUE);
        AFTERIMAGE_PROBABILITY_CAP_PER_LEVEL =
                BUILDER.comment("Probability cap per enchantment level.")
                        .defineInRange("Probability Cap Per Level", 0.3D, 0.0D, Double.MAX_VALUE);
        AFTERIMAGE_PROBABILITY_MAX_CAP =
                BUILDER.comment("Maximum dodge probability cap.")
                        .defineInRange("Probability Max Cap", 0.8D, 0.0D, 1.0D);
        BUILDER.pop();

        BUILDER.push("Air Attack");
        AIR_ATTACK_DAMAGE_MULTIPLIER =
                BUILDER.comment("Damage = fall distance * multiplier * level.")
                        .defineInRange("Damage Multiplier", 0.3D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Armor Forging");
        ARMOR_FORGING_VALUE_CAP_PER_LEVEL =
                BUILDER.comment("Upper limit of forging value = value cap per level * level.")
                        .defineInRange("Value Cap Per Level", 1000, 0, Integer.MAX_VALUE);
        ARMOR_FORGING_ARMOR_BASE =
                BUILDER.comment("Increased armor = forging value / armor base * 100%.")
                        .defineInRange("Armor Base", 5000, 1, Integer.MAX_VALUE);
        ARMOR_FORGING_TOUGHNESS_BASE =
                BUILDER.comment("Increased toughness = forging value / toughness base * 100%.")
                        .defineInRange("Toughness Base", 10000, 1, Integer.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Beyond the Flash");
        BEYOND_THE_FLASH_DURABILITY_CONSUMPTION_MULTIPLIER =
                BUILDER.comment("Durability consumption multiplier.")
                        .defineInRange("Durability Consumption Multiplier", 1.0D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Blood Feed");
        BLOOD_FEED_PROBABILITY_PER_LEVEL =
                BUILDER.comment("Trigger probability = probability per level * level.")
                        .defineInRange("Probability Per Level", 0.03D, 0.0D, 1.0D);
        BLOOD_FEED_CAP_PER_LEVEL =
                BUILDER.comment("Stored stack cap = cap per level * level.")
                        .defineInRange("Cap Per Level", 10, 0, Integer.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Blood Sacrifice");
        BLOOD_SACRIFICE_SELF_DAMAGE_PER_LEVEL =
                BUILDER.comment("Damage to player per level.")
                        .defineInRange("Self Damage Per Level", 2.0D, 0.0D, Double.MAX_VALUE);
        BLOOD_SACRIFICE_BASE =
                BUILDER.comment("Damage *= 1 + (base + lost health / max health) * level.")
                        .defineInRange("Multiplier", 0.1D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Bloodthirsty");
        BLOODTHIRSTY_HUNGER_MULTIPLIER =
                BUILDER.comment("Hunger value += damage value * multiplier.")
                        .defineInRange("Hunger Multiplier", 0.5D, 0.0D, Double.MAX_VALUE);
        BLOODTHIRSTY_HUNGER_UPPER_LIMIT =
                BUILDER.comment("Maximum hunger value restored by its damage effect.")
                        .defineInRange("Hunger Upper Limit", 20, 0, Integer.MAX_VALUE);
        BLOODTHIRSTY_SATURATION_MULTIPLIER =
                BUILDER.comment("Saturation += damage value * multiplier.")
                        .defineInRange("Saturation Multiplier", 0.2D, 0.0D, Double.MAX_VALUE);
        BLOODTHIRSTY_SATURATION_CAP =
                BUILDER.comment("Maximum saturation restored by its damage effect.")
                        .defineInRange("Saturation Cap", 25.0D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Bubble Shield");
        BUBBLE_SHIELD_AIR_SUPPLY_RATIO =
                BUILDER.comment("Triggering condition: air supply value >= max value * ratio.")
                        .defineInRange("Air Supply Ratio", 0.8D, 0.0D, Double.MAX_VALUE);
        BUBBLE_SHIELD_COST_RATIO =
                BUILDER.comment("Air supply cost ratio.")
                        .defineInRange("Cost Ratio", 0.7D, 0.0D, 1.0D);
        BUBBLE_SHIELD_DAMAGE_MULTIPLIER =
                BUILDER.comment("Damage -= air supply value * cost ratio * multiplier * level.")
                        .defineInRange("Damage Multiplier", 0.1D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Charge");
        CHARGE_DISTANCE_MULTIPLIER =
                BUILDER.comment("Push distance multiplier.")
                        .defineInRange("Distance Multiplier", 2.0D, 0.0D, Double.MAX_VALUE);
        CHARGE_INVINCIBLE_DURATION_PER_LEVEL =
                BUILDER.comment("Duration = 5 + invincible duration per level * level (ticks).")
                        .defineInRange("Invincible Duration Per Level", 5, 0, Integer.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Condition Overload");
        CONDITION_OVERLOAD_DAMAGE_MULTIPLIER =
                BUILDER.comment("Damage *= 1 + multiplier * level * debuff count.")
                        .defineInRange("Damage Multiplier", 0.05D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Cumbersome");
        CUMBERSOME_ATTACK_SPEED_REDUCER =
                BUILDER.comment("Attack speed *= 1 - reducer.")
                        .defineInRange("Attack Speed Reducer", 0.2D, 0.0D, 1.0D);
        BUILDER.pop();

        BUILDER.push("Cursed Gaze");
        CURSED_GAZE_BASE_DISTANCE =
                BUILDER.comment("Effective distance = min(level * base distance, 128).")
                        .defineInRange("Base Distance", 5.0D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Cracked Crown");
        CRACKED_CROWN_DAMAGE_MULTIPLIER =
                BUILDER.comment("Attacker dealt damage *= 1 + damage multiplier * level.")
                        .defineInRange("Damage Multiplier", 0.3D, 0.0D, Double.MAX_VALUE);
        CRACKED_CROWN_TAKEN_DAMAGE_MULTIPLIER =
                BUILDER.comment("Wearer taken damage *= 1 + taken damage multiplier * level.")
                        .defineInRange("Taken Damage Multiplier", 0.3D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Dedication");
        DEDICATION_ARMOR_BONUS_MULTIPLIER =
                BUILDER.comment("Armor += stored level * armor bonus multiplier.")
                        .defineInRange("Armor Bonus Multiplier", 1.0D, 0.0D, Double.MAX_VALUE);
        DEDICATION_TOUGHNESS_BONUS_MULTIPLIER =
                BUILDER.comment("Toughness += stored level * toughness bonus multiplier.")
                        .defineInRange("Toughness Bonus Multiplier", 0.5D, 0.0D, Double.MAX_VALUE);
        DEDICATION_PROBABILITY =
                BUILDER.comment("Probability to consume the enchantment on hurt.")
                        .defineInRange("Probability", 0.001D, 0.0D, 1.0D);
        BUILDER.pop();

        BUILDER.push("Delayed Execution");
        DELAYED_EXECUTION_STORED_DAMAGE =
                BUILDER.comment("Proportion of damage to store.")
                        .defineInRange("Stored Damage", 0.8D, 0.0D, 1.0D);
        DELAYED_EXECUTION_DAMAGE_MULTIPLIER =
                BUILDER.comment("Damage = stored damage * (1 + damage multiplier * level).")
                        .defineInRange("Damage Multiplier", 0.8D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Dominion");
        DOMINION_DAMAGE_MULTIPLIER =
                BUILDER.comment("Damage += sum of enchantment level * multiplier.")
                        .defineInRange("Damage Multiplier", 0.5D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Downwind");
        DOWNWIND_PUSH_FORCE_MULTIPLIER =
                BUILDER.comment("Upward push base added after hit.")
                        .defineInRange("Push Force Multiplier", 2.0D, 0.0D, Double.MAX_VALUE);
        DOWNWIND_DAMAGE_MULTIPLIER_PER_LEVEL =
                BUILDER.comment("Airborne target damage *= 1 + damage multiplier per level. Legacy behavior applies this once and does not scale with enchantment level.")
                        .defineInRange("Damage Multiplier Per Level", 0.2D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Drowning");
        DROWNING_AIR_SUPPLY_VALUE =
                BUILDER.comment("Air supply is set to this value after each hit.")
                        .defineInRange("Air Supply Value", -40, Integer.MIN_VALUE, Integer.MAX_VALUE);
        DROWNING_DAMAGE =
                BUILDER.comment("Self damage dealt when attacking while already out of air.")
                        .defineInRange("Damage", 1.0D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Duellists Prerogative");
        DUELLISTS_PREROGATIVE_DAMAGE_MULTIPLIER =
                BUILDER.comment("Damage *= 1 + multiplier * level.")
                        .defineInRange("Damage Multiplier", 0.2D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Eater of Souls");
        EATER_OF_SOULS_DAMAGE_MULTIPLIER =
                BUILDER.comment("Damage += sqrt(kill count) * level * multiplier.")
                        .defineInRange("Damage Multiplier", 0.3D, 0.0D, Double.MAX_VALUE);
        EATER_OF_SOULS_CAP =
                BUILDER.comment("Damage cap.")
                        .defineInRange("Cap", 1000.0D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Empathy");
        EMPATHY_SHOOT_POWER_MULTIPLIER =
                BUILDER.comment("Push distance multiplier = charge * multiplier.")
                        .defineInRange("Shoot Power Multiplier", 0.1D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Eucharist");
        EUCHARIST_MINIMUM_HUNGER =
                BUILDER.comment("The minimum hunger point to obtain the buff.")
                        .defineInRange("Minimum Hunger", 6, 0, Integer.MAX_VALUE);
        EUCHARIST_DURATION_MULTIPLIER =
                BUILDER.comment("The effect duration = multiplier * foodSaturation (second).")
                        .defineInRange("Duration Multiplier", 3, 0, Integer.MAX_VALUE);
        EUCHARIST_DAMAGE_MULTIPLIER =
                BUILDER.comment("Taken damage *= multiplier.")
                        .defineInRange("Damage Multiplier", 0.1D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Falling Stone");
        FALLING_STONE_DAMAGE_MULTIPLIER =
                BUILDER.comment("Damage = (1 + level * multiplier) * fallingDistance.")
                        .defineInRange("Damage Multiplier", 0.5D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
