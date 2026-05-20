package com.foolsix.fancyenchantments;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_INCOMPATIBILITY;
    public static final ModConfigSpec.BooleanValue ENABLE_MOD_BOOK_TEXTURE;

    public static final ModConfigSpec.ConfigValue<String> ELEMENT_STAT_AER_BUFF;
    public static final ModConfigSpec.ConfigValue<String> ELEMENT_STAT_AQUA_BUFF;
    public static final ModConfigSpec.ConfigValue<String> ELEMENT_STAT_IGNIS_BUFF;
    public static final ModConfigSpec.ConfigValue<String> ELEMENT_STAT_TERRA_BUFF;
    public static final ModConfigSpec.IntValue ELEMENT_STAT_AER_CONDITION;
    public static final ModConfigSpec.IntValue ELEMENT_STAT_AQUA_CONDITION;
    public static final ModConfigSpec.IntValue ELEMENT_STAT_IGNIS_CONDITION;
    public static final ModConfigSpec.IntValue ELEMENT_STAT_TERRA_CONDITION;
    public static final ModConfigSpec.IntValue ELEMENT_STAT_AER_MAX_EFFECT_LEVEL;
    public static final ModConfigSpec.IntValue ELEMENT_STAT_AQUA_MAX_EFFECT_LEVEL;
    public static final ModConfigSpec.IntValue ELEMENT_STAT_IGNIS_MAX_EFFECT_LEVEL;
    public static final ModConfigSpec.IntValue ELEMENT_STAT_TERRA_MAX_EFFECT_LEVEL;
    public static final ModConfigSpec.IntValue ELEMENT_STAT_IGNIS_FIRE_RESISTANCE_CONDITION;
    public static final ModConfigSpec.IntValue ELEMENT_STAT_TWISTED_DEBUFF_CONDITION;
    public static final ModConfigSpec.DoubleValue ELEMENT_STAT_TWISTED_DEBUFF_PROBABILITY_PER_SECOND;
    public static final ModConfigSpec.IntValue ELEMENT_STAT_TWISTED_DEBUFF_DURATION_SECONDS;

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

    public static final ModConfigSpec.DoubleValue FEARLESS_CHALLENGER_HP_CONDITION;
    public static final ModConfigSpec.DoubleValue FEARLESS_CHALLENGER_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue FEARLESS_CHALLENGER_CAP;

    public static final ModConfigSpec.DoubleValue FEINT_ATTACK_DAMAGE_REDUCER;
    public static final ModConfigSpec.DoubleValue FEINT_ATTACK_DAMAGE_MULTIPLIER;

    public static final ModConfigSpec.DoubleValue FIRE_DISASTER_PROBABILITY;

    public static final ModConfigSpec.IntValue FROZEN_HEART_DURATION;

    public static final ModConfigSpec.IntValue GALE_DURATION_SECONDS;

    public static final ModConfigSpec.DoubleValue GIFT_OF_FIRE_BENEFICIAL_MULTIPLIER;

    public static final ModConfigSpec.IntValue GREED_SUPREME_LOOTING_LEVEL_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue GREED_SUPREME_PROBABILITY_OF_DOUBLING;

    public static final ModConfigSpec.DoubleValue HEAVY_ARROW_DAMAGE_MULTIPLIER;
    public static final ModConfigSpec.IntValue HEAVY_ARROW_KNOCKBACK_ADDON;

    public static final ModConfigSpec.DoubleValue HEAVY_BLOW_BASE_RATE;
    public static final ModConfigSpec.DoubleValue HEAVY_BLOW_DAMAGE_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue HEAVY_BLOW_SPEED_REDUCER;

    public static final ModConfigSpec.DoubleValue HUNGRY_PROBABILITY;

    public static final ModConfigSpec.DoubleValue ICY_BURST_DAMAGE_MULTIPLIER;
    public static final ModConfigSpec.IntValue ICY_BURST_DURATION_PER_LEVEL;

    public static final ModConfigSpec.DoubleValue LAVA_BURST_PROBABILITY;

    public static final ModConfigSpec.DoubleValue LIGHTNESS_SPEED_MULTIPLIER;

    public static final ModConfigSpec.DoubleValue LITHIC_SIPHON_PROBABILITY_PER_LEVEL;

    public static final ModConfigSpec.DoubleValue MELTER_DAMAGE_REDUCER;
    public static final ModConfigSpec.DoubleValue MELTER_ARMOR_REDUCER;
    public static final ModConfigSpec.IntValue MELTER_DURATION;

    public static final ModConfigSpec.DoubleValue MOUNTAIN_SUPREME_PROTECTION_REDUCER;

    public static final ModConfigSpec.DoubleValue NIGHTMARE_EXPLODE_RADIUS_BASE;
    public static final ModConfigSpec.DoubleValue NIGHTMARE_EXPLODE_RADIUS_PER_LEVEL;
    public static final ModConfigSpec.DoubleValue NIGHTMARE_EXPLODE_DAMAGE_BASE;
    public static final ModConfigSpec.DoubleValue NIGHTMARE_EXPLODE_DAMAGE_PER_LEVEL;
    public static final ModConfigSpec.DoubleValue NIRVANA_MINIMUM_HEALTH_RATIO;
    public static final ModConfigSpec.IntValue NIRVANA_INTERVAL_SECONDS;
    public static final ModConfigSpec.IntValue NIRVANA_HEAL_DURATION_TICKS;
    public static final ModConfigSpec.DoubleValue OCEAN_CURRENT_SPEED_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue OCEAN_CURRENT_EXTRA_SPEED_MULTIPLIER;
    public static final ModConfigSpec.BooleanValue OCEAN_CURRENT_INEFFECTIVE_WHEN_ON_FIRE;
    public static final ModConfigSpec.DoubleValue OVERFLOW_PROBABILITY;
    public static final ModConfigSpec.IntValue OVER_HEALING_CAP;
    public static final ModConfigSpec.DoubleValue PALADINS_SHIELD_DAMAGE_TRANSFER_RATIO;
    public static final ModConfigSpec.DoubleValue PALADINS_SHIELD_BASE_DAMAGE_REDUCTION_RATIO;
    public static final ModConfigSpec.DoubleValue PALADINS_SHIELD_TRANSFERRED_DAMAGE_RATIO;
    public static final ModConfigSpec.DoubleValue PALADINS_SHIELD_UPPER_LIMIT;
    public static final ModConfigSpec.DoubleValue CHEST_LOOT_MOD_BOOK_CHANCE;
    public static final ModConfigSpec.DoubleValue CHEST_LOOT_COMMON_CHANCE;
    public static final ModConfigSpec.DoubleValue CHEST_LOOT_UNCOMMON_CHANCE;
    public static final ModConfigSpec.DoubleValue CHEST_LOOT_RARE_CHANCE;
    public static final ModConfigSpec.DoubleValue CHEST_LOOT_VERY_RARE_CHANCE;

    public static final ModConfigSpec.DoubleValue DROP_PROBABILITY;
    public static final ModConfigSpec.DoubleValue CURSE_REMOVAL_PROBABILITY;
    public static final ModConfigSpec.DoubleValue UNDEAD_DAMAGE_ADDON;
    public static final ModConfigSpec.DoubleValue EXPLOSION_HEAL_MULTIPLIER;
    public static final ModConfigSpec.IntValue EXPLOSION_ARMOR_BASE_DAMAGE;
    public static final ModConfigSpec.DoubleValue EXPLOSION_DAMAGE_MULTIPLIER;

    public static final ModConfigSpec.DoubleValue REFLECTING_BASE_VELOCITY;
    public static final ModConfigSpec.IntValue REFLECTING_BASE_DAMAGE;

    public static final ModConfigSpec.DoubleValue ROLLING_STONE_DAMAGE_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue ROLLING_STONE_DAMAGE_REDUCER;
    public static final ModConfigSpec.DoubleValue ROLLING_STONE_LOWER_LIMIT;

    public static final ModConfigSpec.DoubleValue SANDER_DAMAGE_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue SHARP_ROCK_SHIELD_DAMAGE_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue SHARP_ROCK_WEAPON_DAMAGE_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue SIGHS_OF_ASHES_DAMAGE_PER_SECOND;
    public static final ModConfigSpec.IntValue SPREADING_SPORES_SPORE_CAP;
    public static final ModConfigSpec.DoubleValue SPREADING_SPORES_DAMAGE_MULTIPLIER;
    public static final ModConfigSpec.IntValue STACKING_WAVES_DURATION;
    public static final ModConfigSpec.DoubleValue STACKING_WAVES_ATTACK_SPEED_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue STREAMLINE_SPEED_MULTIPLIER_PER_LEVEL;
    public static final ModConfigSpec.DoubleValue THE_FALLEN_DAMAGE_MULTIPLIER;
    public static final ModConfigSpec.IntValue THRILLING_THUNDER_TICK_GAP;
    public static final ModConfigSpec.DoubleValue THRILLING_THUNDER_DAMAGE_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue THRILLING_THUNDER_PROBABILITY_PER_LEVEL;
    public static final ModConfigSpec.IntValue UNYIELDING_SPIRIT_EXTRA_TIME_SECONDS;
    public static final ModConfigSpec.IntValue UNYIELDING_SPIRIT_BASE_DAMAGE;
    public static final ModConfigSpec.DoubleValue UNYIELDING_SPIRIT_HEALTH_PERCENTAGE;
    public static final ModConfigSpec.IntValue UNYIELDING_SPIRIT_SLOWNESS_LEVEL;
    public static final ModConfigSpec.IntValue UNYIELDING_SPIRIT_DAMAGE_RESISTANCE_LEVEL;
    public static final ModConfigSpec.BooleanValue UNYIELDING_SPIRIT_ENABLE_BLINDNESS;
    public static final ModConfigSpec.DoubleValue WIND_BLADE_BASE_DAMAGE_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue WIND_FIRE_WHEELS_SPEED_MULTIPLIER;

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

        BUILDER.push("Element Stat");
        ELEMENT_STAT_AER_BUFF =
                BUILDER.comment("Buff effect id for Aer.")
                        .define("Aer Buff", "minecraft:speed");
        ELEMENT_STAT_AQUA_BUFF =
                BUILDER.comment("Buff effect id for Aqua.")
                        .define("Aqua Buff", "minecraft:regeneration");
        ELEMENT_STAT_IGNIS_BUFF =
                BUILDER.comment("Buff effect id for Ignis.")
                        .define("Ignis Buff", "minecraft:strength");
        ELEMENT_STAT_TERRA_BUFF =
                BUILDER.comment("Buff effect id for Terra.")
                        .define("Terra Buff", "minecraft:resistance");
        ELEMENT_STAT_AER_CONDITION =
                BUILDER.comment("Aer buff condition.")
                        .defineInRange("Aer Condition", 5, 1, Integer.MAX_VALUE);
        ELEMENT_STAT_AQUA_CONDITION =
                BUILDER.comment("Aqua buff condition.")
                        .defineInRange("Aqua Condition", 6, 1, Integer.MAX_VALUE);
        ELEMENT_STAT_IGNIS_CONDITION =
                BUILDER.comment("Ignis buff condition.")
                        .defineInRange("Ignis Condition", 4, 1, Integer.MAX_VALUE);
        ELEMENT_STAT_TERRA_CONDITION =
                BUILDER.comment("Terra buff condition.")
                        .defineInRange("Terra Condition", 9, 1, Integer.MAX_VALUE);
        ELEMENT_STAT_AER_MAX_EFFECT_LEVEL =
                BUILDER.comment("Aer buff maximum amplifier + 1.")
                        .defineInRange("Aer Max Effect Level", 6, 1, Integer.MAX_VALUE);
        ELEMENT_STAT_AQUA_MAX_EFFECT_LEVEL =
                BUILDER.comment("Aqua buff maximum amplifier + 1.")
                        .defineInRange("Aqua Max Effect Level", 6, 1, Integer.MAX_VALUE);
        ELEMENT_STAT_IGNIS_MAX_EFFECT_LEVEL =
                BUILDER.comment("Ignis buff maximum amplifier + 1.")
                        .defineInRange("Ignis Max Effect Level", 6, 1, Integer.MAX_VALUE);
        ELEMENT_STAT_TERRA_MAX_EFFECT_LEVEL =
                BUILDER.comment("Terra buff maximum amplifier + 1.")
                        .defineInRange("Terra Max Effect Level", 4, 1, Integer.MAX_VALUE);
        ELEMENT_STAT_IGNIS_FIRE_RESISTANCE_CONDITION =
                BUILDER.comment("Ignis point condition for fire resistance.")
                        .defineInRange("Ignis Fire Resistance Condition", 10, 0, Integer.MAX_VALUE);
        ELEMENT_STAT_TWISTED_DEBUFF_CONDITION =
                BUILDER.comment("Debuff condition = twisted points - holy points.")
                        .defineInRange("Twisted Debuff Condition", 10, 0, Integer.MAX_VALUE);
        ELEMENT_STAT_TWISTED_DEBUFF_PROBABILITY_PER_SECOND =
                BUILDER.comment("Probability per second to receive a random debuff when twisted points exceed the condition.")
                        .defineInRange("Twisted Debuff Probability", 0.02D, 0.0D, 1.0D);
        ELEMENT_STAT_TWISTED_DEBUFF_DURATION_SECONDS =
                BUILDER.comment("Random debuff duration in seconds.")
                        .defineInRange("Twisted Debuff Duration", 3, 0, Integer.MAX_VALUE);
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
                        .defineInRange("Probability Cap", 0.3D, 0.0D, Double.MAX_VALUE);
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
                        .defineInRange("Value Cap", 1000, 0, Integer.MAX_VALUE);
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
                        .defineInRange("Probability", 0.03D, 0.0D, 1.0D);
        BLOOD_FEED_CAP_PER_LEVEL =
                BUILDER.comment("Stored stack cap = cap per level * level.")
                        .defineInRange("Cap", 10, 0, Integer.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Blood Sacrifice");
        BLOOD_SACRIFICE_SELF_DAMAGE_PER_LEVEL =
                BUILDER.comment("Damage to player per level.")
                        .defineInRange("Self Damage", 2.0D, 0.0D, Double.MAX_VALUE);
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
                        .defineInRange("Invincible Duration", 5, 0, Integer.MAX_VALUE);
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
                        .defineInRange("Damage Multiplier", 0.2D, 0.0D, Double.MAX_VALUE);
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

        BUILDER.push("Fearless Challenger");
        FEARLESS_CHALLENGER_HP_CONDITION =
                BUILDER.comment("Trigger when target's HP / user's HP > condition.")
                        .defineInRange("HP Condition", 3.0D, 0.0D, Double.MAX_VALUE);
        FEARLESS_CHALLENGER_MULTIPLIER =
                BUILDER.comment("Damage *= min(cap, target's HP / user's HP * multiplier * level).")
                        .defineInRange("Multiplier", 1.0D, 0.0D, Double.MAX_VALUE);
        FEARLESS_CHALLENGER_CAP =
                BUILDER.comment("Damage bonus cap.")
                        .defineInRange("Cap", 10.0D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Feint Attack");
        FEINT_ATTACK_DAMAGE_REDUCER =
                BUILDER.comment("Damage to the target *= 1 - reducer.")
                        .defineInRange("Damage Reducer", 0.7D, 0.0D, 1.0D);
        FEINT_ATTACK_DAMAGE_MULTIPLIER =
                BUILDER.comment("Damage to others *= 1 + multiplier * level.")
                        .defineInRange("Damage Multiplier", 0.2D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Fire Disaster");
        FIRE_DISASTER_PROBABILITY =
                BUILDER.comment("The probability of generating a fire (per level).")
                        .defineInRange("Probability", 0.05D, 0.0D, 1.0D);
        BUILDER.pop();

        BUILDER.push("Frozen Heart");
        FROZEN_HEART_DURATION =
                BUILDER.comment("Slowness duration in seconds.")
                        .defineInRange("Duration", 5, 0, Integer.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Gale");
        GALE_DURATION_SECONDS =
                BUILDER.comment("Haste duration in seconds after breaking a block.")
                        .defineInRange("Duration Seconds", 2, 0, Integer.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Gift of Fire");
        GIFT_OF_FIRE_BENEFICIAL_MULTIPLIER =
                BUILDER.comment("Damage += level * multiplier when the target is on fire.")
                        .defineInRange("Beneficial Multiplier", 2.5D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Greed Supreme Looting");
        GREED_SUPREME_LOOTING_LEVEL_MULTIPLIER =
                BUILDER.comment("Looting level += level * multiplier. On trigger, the same amount is added again.")
                        .defineInRange("Loot Level Multiplier", 3, 0, Integer.MAX_VALUE);
        GREED_SUPREME_PROBABILITY_OF_DOUBLING =
                BUILDER.comment("Probability of applying the additional looting bonus again.")
                        .defineInRange("Probability Of Doubling", 0.05D, 0.0D, 1.0D);
        BUILDER.pop();

        BUILDER.push("Heavy Arrow");
        HEAVY_ARROW_DAMAGE_MULTIPLIER =
                BUILDER.comment("Arrow base damage *= 1 + damage multiplier * level.")
                        .defineInRange("Damage Multiplier", 0.15D, 0.0D, Double.MAX_VALUE);
        HEAVY_ARROW_KNOCKBACK_ADDON =
                BUILDER.comment("Arrow knockback += knockback addon * level.")
                        .defineInRange("Knockback Addon", 1, 0, Integer.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Heavy Blow");
        HEAVY_BLOW_BASE_RATE =
                BUILDER.comment("rate = baseRate * level")
                        .defineInRange("Base Rate", 0.1D, 0.0D, 1.0D);
        HEAVY_BLOW_DAMAGE_MULTIPLIER =
                BUILDER.comment("damage *= 1 + level * multiplier")
                        .defineInRange("Damage Multiplier", 1.0D, 0.0D, Double.MAX_VALUE);
        HEAVY_BLOW_SPEED_REDUCER =
                BUILDER.comment("attackSpeed *= 1 - reducer")
                        .defineInRange("Speed Reducer", 0.1D, 0.0D, 1.0D);
        BUILDER.pop();

        BUILDER.push("Hungry");
        HUNGRY_PROBABILITY =
                BUILDER.comment("The probability of generating a copy = level * probability")
                        .defineInRange("Probability", 0.2D, 0.0D, 1.0D);
        BUILDER.pop();

        BUILDER.push("Icy Burst");
        ICY_BURST_DAMAGE_MULTIPLIER =
                BUILDER.comment("damage = target'sMaxHealth * multiplier * level")
                        .defineInRange("Damage Multiplier", 0.15D, 0.0D, Double.MAX_VALUE);
        ICY_BURST_DURATION_PER_LEVEL =
                BUILDER.comment("Duration per level in seconds.")
                        .defineInRange("Duration", 3, 0, Integer.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Lava Burst");
        LAVA_BURST_PROBABILITY =
                BUILDER.comment("The probability of generating a burst = level * probability")
                        .defineInRange("Probability", 0.2D, 0.0D, 1.0D);
        BUILDER.pop();

        BUILDER.push("Lightness");
        LIGHTNESS_SPEED_MULTIPLIER =
                BUILDER.comment("speed while blocking *= 1 + level * multiplier")
                        .defineInRange("Speed Multiplier", 0.8D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Lithic Siphon");
        LITHIC_SIPHON_PROBABILITY_PER_LEVEL =
                BUILDER.comment("Trigger probability = probability per level * level.")
                        .defineInRange("Probability", 0.03D, 0.0D, 1.0D);
        BUILDER.pop();

        BUILDER.push("Melter");
        MELTER_DAMAGE_REDUCER =
                BUILDER.comment("All level, reduce base damage")
                        .defineInRange("Damage Reducer", 0.5D, 0.0D, Double.MAX_VALUE);
        MELTER_ARMOR_REDUCER =
                BUILDER.comment("Armor *= 1 - reducer * level")
                        .defineInRange("Armor Reducer", 0.2D, 0.0D, Double.MAX_VALUE);
        MELTER_DURATION =
                BUILDER.comment("The effect duration = level * duration (second)")
                        .defineInRange("Duration", 3, 0, Integer.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Mountain Supreme Protection");
        MOUNTAIN_SUPREME_PROTECTION_REDUCER =
                BUILDER.comment("Fixed damage reduction, per level")
                        .defineInRange("Reducer", 0.5D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Nightmare");
        NIGHTMARE_EXPLODE_RADIUS_BASE =
                BUILDER.comment("Explosion radius = base + (level - 1) * per level.")
                        .defineInRange("Explode Radius Base", 3.0D, 0.0D, Double.MAX_VALUE);
        NIGHTMARE_EXPLODE_RADIUS_PER_LEVEL =
                BUILDER.comment("Explosion radius = base + (level - 1) * per level.")
                        .defineInRange("Explode Radius Step", 1.0D, 0.0D, Double.MAX_VALUE);
        NIGHTMARE_EXPLODE_DAMAGE_BASE =
                BUILDER.comment("Explosion entity damage = base + (level - 1) * per level.")
                        .defineInRange("Explode Damage Base", 8.0D, 0.0D, Double.MAX_VALUE);
        NIGHTMARE_EXPLODE_DAMAGE_PER_LEVEL =
                BUILDER.comment("Explosion entity damage = base + (level - 1) * per level.")
                        .defineInRange("Explode Damage Step", 4.0D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Nirvana");
        NIRVANA_MINIMUM_HEALTH_RATIO =
                BUILDER.comment("Minimum health ratio to trigger the effect.")
                        .defineInRange("Minimum Health Ratio", 0.5D, 0.0D, 1.0D);
        NIRVANA_INTERVAL_SECONDS =
                BUILDER.comment("Trigger interval in seconds.")
                        .defineInRange("Interval Seconds", 10, 1, Integer.MAX_VALUE);
        NIRVANA_HEAL_DURATION_TICKS =
                BUILDER.comment("Instant health effect duration in ticks.")
                        .defineInRange("Heal Duration Ticks", 3, 0, Integer.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Ocean Current");
        OCEAN_CURRENT_SPEED_MULTIPLIER =
                BUILDER.comment("Attack speed += level * multiplier.")
                        .defineInRange("Speed Multiplier", 0.2D, 0.0D, Double.MAX_VALUE);
        OCEAN_CURRENT_EXTRA_SPEED_MULTIPLIER =
                BUILDER.comment("Attack speed while in water += level * multiplier * extra multiplier.")
                        .defineInRange("Extra Speed Multiplier", 1.5D, 0.0D, Double.MAX_VALUE);
        OCEAN_CURRENT_INEFFECTIVE_WHEN_ON_FIRE =
                BUILDER.comment("Whether the effect is disabled while on fire.")
                        .define("Ineffective When On Fire", true);
        BUILDER.pop();

        BUILDER.push("Overflow");
        OVERFLOW_PROBABILITY =
                BUILDER.comment("The probability of generating a puddle of water (per level).")
                        .defineInRange("Probability", 0.1D, 0.0D, 1.0D);
        BUILDER.pop();

        BUILDER.push("Over Healing");
        OVER_HEALING_CAP =
                BUILDER.comment("Maximum absorption added per level.")
                        .defineInRange("Cap", 10, 0, Integer.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Chest Loot");
        CHEST_LOOT_MOD_BOOK_CHANCE =
                BUILDER.comment("Chance of spawning a Fancy Enchantments enchanted book.")
                        .defineInRange("Mod Book Chance", 0.3D, 0.0D, 1.0D);
        CHEST_LOOT_COMMON_CHANCE =
                BUILDER.comment("Final spawn probability = base chance * common rarity chance.")
                        .defineInRange("Common Chance", 0.9D, 0.0D, 1.0D);
        CHEST_LOOT_UNCOMMON_CHANCE =
                BUILDER.comment("Final spawn probability = base chance * uncommon rarity chance.")
                        .defineInRange("Uncommon Chance", 0.75D, 0.0D, 1.0D);
        CHEST_LOOT_RARE_CHANCE =
                BUILDER.comment("Final spawn probability = base chance * rare rarity chance.")
                        .defineInRange("Rare Chance", 0.6D, 0.0D, 1.0D);
        CHEST_LOOT_VERY_RARE_CHANCE =
                BUILDER.comment("Final spawn probability = base chance * very rare rarity chance.")
                        .defineInRange("Very Rare Chance", 0.5D, 0.0D, 1.0D);
        BUILDER.pop();

        BUILDER.push("Paladins Shield");
        PALADINS_SHIELD_DAMAGE_TRANSFER_RATIO =
                BUILDER.comment("Transferred damage amount = damage * ratio.")
                        .defineInRange("Damage Transfer Ratio", 0.5D, 0.0D, 1.0D);
        PALADINS_SHIELD_BASE_DAMAGE_REDUCTION_RATIO =
                BUILDER.comment("Damage reduction while holding a shield -= base damage reduction ratio * level.")
                        .defineInRange("Base Damage Reduction Ratio", 0.05D, 0.0D, 1.0D);
        PALADINS_SHIELD_TRANSFERRED_DAMAGE_RATIO =
                BUILDER.comment("Damage the enchantment holder receives = damage * damage transfer ratio * transferred damage ratio.")
                        .defineInRange("Transferred Damage Ratio", 0.5D, 0.0D, Double.MAX_VALUE);
        PALADINS_SHIELD_UPPER_LIMIT =
                BUILDER.comment("If the final damage exceeds the upper limit * receiver's max health, it will not be transferred.")
                        .defineInRange("Upper Limit", 0.5D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Pervert");
        DROP_PROBABILITY =
                BUILDER.comment("Probability to drop the cursed item upon equipping.")
                        .defineInRange("Drop Probability", 0.9D, 0.0D, 1.0D);
        BUILDER.pop();

        BUILDER.push("Pure Fate");
        CURSE_REMOVAL_PROBABILITY =
                BUILDER.comment("Probability of removing a curse = probability * level.")
                        .defineInRange("Curse Removal Probability", 0.1D, 0.0D, 1.0D);
        BUILDER.pop();

        BUILDER.push("Purifying");
        UNDEAD_DAMAGE_ADDON =
                BUILDER.comment("Damage to undead += addon * level.")
                        .defineInRange("Undead Damage Addon", 3.0D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Pyromaniac");
        EXPLOSION_HEAL_MULTIPLIER =
                BUILDER.comment("Heal amount = explosive damage * multiplier * level.")
                        .defineInRange("Explosion Heal Multiplier", 0.1D, 0.0D, Double.MAX_VALUE);
        EXPLOSION_ARMOR_BASE_DAMAGE =
                BUILDER.comment("Durability value of armor -= base + heal amount * multiplier.")
                        .defineInRange("Armor Base Damage", 3, 0, Integer.MAX_VALUE);
        EXPLOSION_DAMAGE_MULTIPLIER =
                BUILDER.comment("Durability value of armor -= base + heal amount * multiplier.")
                        .defineInRange("Explosion Damage Multiplier", 1.0D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Reflecting");
        REFLECTING_BASE_VELOCITY =
                BUILDER.comment("Reflected projectile velocity = 1.0 + baseVelocity * level.")
                        .defineInRange("Base Velocity", 0.5D, 0.0D, Double.MAX_VALUE);
        REFLECTING_BASE_DAMAGE =
                BUILDER.comment("Durability value of shield -= base.")
                        .defineInRange("Base Damage", 3, 0, Integer.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Rolling Stone");
        ROLLING_STONE_DAMAGE_MULTIPLIER =
                BUILDER.comment("Damage += multiplier * level * speed.")
                        .defineInRange("Damage Multiplier", 25.0D, 0.0D, Double.MAX_VALUE);
        ROLLING_STONE_DAMAGE_REDUCER =
                BUILDER.comment("Damage taken *= 1 - reducer * level.")
                        .defineInRange("Damage Reducer", 0.1D, 0.0D, 1.0D);
        ROLLING_STONE_LOWER_LIMIT =
                BUILDER.comment("Lower limit, minimum percentage of damage taken.")
                        .defineInRange("Lower Limit", 0.5D, 0.0D, 1.0D);
        BUILDER.pop();

        BUILDER.push("Sander");
        SANDER_DAMAGE_MULTIPLIER =
                BUILDER.comment("Damage *= 1 + (damageValue / maxDamage) * level * multiplier.")
                        .defineInRange("Damage Multiplier", 1.0D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Sharp Rock");
        SHARP_ROCK_SHIELD_DAMAGE_MULTIPLIER =
                BUILDER.comment("Shield damage = armor * multiplier * level.")
                        .defineInRange("Shield Damage Multiplier", 0.4D, 0.0D, Double.MAX_VALUE);
        SHARP_ROCK_WEAPON_DAMAGE_MULTIPLIER =
                BUILDER.comment("Damage += armor * multiplier * level.")
                        .defineInRange("Weapon Damage Multiplier", 0.2D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Sighs of Ashes");
        SIGHS_OF_ASHES_DAMAGE_PER_SECOND =
                BUILDER.comment("Damage += remaining fire seconds * multiplier.")
                        .defineInRange("Damage Per Second", 0.2D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Spreading Spores");
        SPREADING_SPORES_SPORE_CAP =
                BUILDER.comment("Reaching the cap triggers the damage.")
                        .defineInRange("Spore Cap", 10, 0, Integer.MAX_VALUE);
        SPREADING_SPORES_DAMAGE_MULTIPLIER =
                BUILDER.comment("Damage *= multiplier on spore cap trigger.")
                        .defineInRange("Damage Multiplier", 3.0D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Stacking Waves");
        STACKING_WAVES_DURATION =
                BUILDER.comment("Effect duration in seconds.")
                        .defineInRange("Duration", 2, 0, Integer.MAX_VALUE);
        STACKING_WAVES_ATTACK_SPEED_MULTIPLIER =
                BUILDER.comment("Attack speed += attack speed multiplier * (effect amplifier + 1).")
                        .defineInRange("Attack Speed Multiplier", 0.3D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Streamline");
        STREAMLINE_SPEED_MULTIPLIER_PER_LEVEL =
                BUILDER.comment("Arrow speed boost per enchantment level.")
                        .defineInRange("Speed Multiplier", 0.5D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("The Fallen");
        THE_FALLEN_DAMAGE_MULTIPLIER =
                BUILDER.comment("Damage *= 1 + multiplier * level * curse count.")
                        .defineInRange("Damage Multiplier", 0.3D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Thrilling Thunder");
        THRILLING_THUNDER_TICK_GAP =
                BUILDER.comment("Time gap of causing damage.")
                        .defineInRange("Tick Gap", 10, 1, Integer.MAX_VALUE);
        THRILLING_THUNDER_DAMAGE_MULTIPLIER =
                BUILDER.comment("Damage = effect level * multiplier.")
                        .defineInRange("Damage Multiplier", 1.0D, 0.0D, Double.MAX_VALUE);
        THRILLING_THUNDER_PROBABILITY_PER_LEVEL =
                BUILDER.comment("Trigger probability = probability per level * level.")
                        .defineInRange("Probability", 0.1D, 0.0D, 1.0D);
        BUILDER.pop();

        BUILDER.push("Unyielding Spirit");
        UNYIELDING_SPIRIT_EXTRA_TIME_SECONDS =
                BUILDER.comment("Extra Time To Live (second).")
                        .defineInRange("Extra Time", 8, 0, Integer.MAX_VALUE);
        UNYIELDING_SPIRIT_BASE_DAMAGE =
                BUILDER.comment("Durability value of helmet -= base.")
                        .defineInRange("Base Damage", 5, 0, Integer.MAX_VALUE);
        UNYIELDING_SPIRIT_HEALTH_PERCENTAGE =
                BUILDER.comment("The health set when enchantment takes effect, percentage.")
                        .defineInRange("Health Percentage", 0.5D, 0.0D, 1.0D);
        UNYIELDING_SPIRIT_SLOWNESS_LEVEL =
                BUILDER.comment("Slowness effect level, set 0 to disable.")
                        .defineInRange("Slowness Level", 3, 0, Integer.MAX_VALUE);
        UNYIELDING_SPIRIT_DAMAGE_RESISTANCE_LEVEL =
                BUILDER.comment("Damage resistance effect level, set 0 to disable.")
                        .defineInRange("Damage Resistance Level", 3, 0, Integer.MAX_VALUE);
        UNYIELDING_SPIRIT_ENABLE_BLINDNESS =
                BUILDER.comment("Whether blindness is applied during the extra time.")
                        .define("Enable Blindness", false);
        BUILDER.pop();

        BUILDER.push("Wind Blade");
        WIND_BLADE_BASE_DAMAGE_MULTIPLIER =
                BUILDER.comment("Damage *= 1 + extraSpeedAttributeValue * level * base multiplier.")
                        .defineInRange("Base Damage Multiplier", 0.5D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        BUILDER.push("Wind Fire Wheels");
        WIND_FIRE_WHEELS_SPEED_MULTIPLIER =
                BUILDER.comment("Horizontal push along look direction while sprinting midair.")
                        .defineInRange("Speed Multiplier", 0.06D, 0.0D, Double.MAX_VALUE);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
