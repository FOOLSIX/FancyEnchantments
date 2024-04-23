package com.foolsix.fancyenchantments.util;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import org.checkerframework.checker.units.qual.C;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

@Config(name = MODID)
public class ModConfig implements ConfigData {
    @Comment("Set level = 0 to disable the enchantment.\n")
    @ConfigEntry.Gui.CollapsibleObject
    public final AdvancedLootingOptions advancedLootingOptions = new AdvancedLootingOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final AdvancedSharpnessOptions advancedSharpnessOptions = new AdvancedSharpnessOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final CounterattackOptions counterattackOptions = new CounterattackOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final EaterOfSoulsOptions eaterOfSoulsOptions = new EaterOfSoulsOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final GiftOfFireOptions giftOfFireOptions = new GiftOfFireOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final LightnessOptions lightnessOptions = new LightnessOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final OceanCurrentOptions oceanCurrentOptions = new OceanCurrentOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final PyromaniacOptions pyromaniacOptions = new PyromaniacOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final ReflectingOptions reflectingOptions = new ReflectingOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final SolidAsARockOptions solidAsARockOptions = new SolidAsARockOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final OverflowOptions overflowOptions = new OverflowOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final FireDisasterOptions fireDisasterOptions = new FireDisasterOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final TheFallenOptions theFallenOptions = new TheFallenOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final EmpathyOptions empathyOptions = new EmpathyOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final RollingStoneOptions rollingStoneOptions = new RollingStoneOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final BlessedWindOptions blessedWind = new BlessedWindOptions();

    public static class AdvancedLootingOptions {
        public int level = 3;
        @Comment("Looting level += level * multiplier (vanilla Looting X provides X looting level)")
        public int lootingLevelMultiplier = 2;
    }

    public static class AdvancedSharpnessOptions {
        public int level = 5;
    }

    public static class CounterattackOptions {
        public int level = 3;
    }

    public static class EaterOfSoulsOptions {
        public boolean isTreasure = true;
        public int level = 3;
        @Comment("damage += sqrt(kill count) * level * multiplier")
        public float damageMultiplier = 0.1f;
    }

    public static class GiftOfFireOptions {
        public int level = 3;
        @Comment("damage += level * multiplier")
        public float beneficialMultiplier = 2.0f;
        @Comment("damage -= level * multiplier")
        public float harmfulMultiplier = 0.5f;
    }

    public static class LightnessOptions {
        public int level = 3;
        @Comment("speed while blocking *= 1 + level * multiplier")
        public float speedMultiplier = 0.8f;
    }

    public static class OceanCurrentOptions {
        public int level = 3;
        @Comment("attack speed += level * multiplier")
        public float speedMultiplier = 0.5f;
        @Comment("attack speed while in water += level * multiplier * extra multiplier")
        public float extraSpeedMultiplier = 1.5f;
    }

    public static class PyromaniacOptions {
        public boolean isTreasure = false;
        public int level = 5;
        @Comment("Heal Amount = explosive damage * multiplayer * level")
        public float healMultiplier = 0.1f;
        @Comment("Durability value of armor -= base + healAmount * multiplier")
        public int armorBaseDamage = 3;
        public float damageMultiplier = 1.0f;
    }

    public static class ReflectingOptions {
        public int level = 5;
        @Comment("Reflected project velocity = 1.0 + baseVelocity * level")
        public float baseVelocity = 0.5f;
        @Comment("Durability value of shield -= base")
        public int baseDamage = 3;
    }

    public static class SolidAsARockOptions {
        public int level = 3;
        @Comment("armor *= 1 + multiplier * level")
        public float armorMultiplier = 0.15f;
        @Comment("toughness *= 1 + multiplier * level")
        public float toughnessMultiplier = 0.1f;
    }

    public static class OverflowOptions {
        @Comment("level should be 0 or 1")
        @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
        public int level = 1;
        @Comment("The probability of generating a puddle of water")
        public double probability = 0.05;
    }

    public static class FireDisasterOptions {
        @Comment("level should be 0 or 1")
        @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
        public int level = 1;
        @Comment("The probability of generating a fire")
        public double probability = 0.05;
    }

    public static class TheFallenOptions {
        public int level = 3;
        public boolean isTreasure = false;
        @Comment("attack damage *= 1 + multiplier * level * numberOfCurse")
        public float damageMultiplier = 0.4f;
    }

    public static class EmpathyOptions {
        @Comment("level should be 0 or 1")
        @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
        public int level = 1;
        public boolean isTreasure = true;
        public boolean isCurse = true;
        public double shootSpeedMultiplier = 0.1;
    }

    public static class RollingStoneOptions {
        public int level = 3;
        @Comment("damage += multiplier * level + speed * damageBonusMultiplier")
        public double damageMultiplier = 3.0;
        public double damageBonusMultiplier = 0.5;
        @Comment("damageTaken *= 1 - reducer * level")
        public double damageReducer = 0.1;

    }

    public static class BlessedWindOptions {
        public int level = 3;
        @Comment("speed while sprinting += level * multiplier (The initial walking speed is 0.1 and the sprinting speed is 0.133)")
        public float sprintSpeedMultiplier = 0.04f;
        @Comment("speed while walking += level * multiplier")
        public float walkSpeedMultiplier = 0.01f;
    }
}
