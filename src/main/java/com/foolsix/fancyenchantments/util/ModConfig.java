package com.foolsix.fancyenchantments.util;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

@Config(name = MODID)
public class ModConfig implements ConfigData {
    @Comment("""
            Set level = 0 to disable the enchantment.
            Enchantments with a default maximum level of 1 should not be set to numbers other than 0 and 1
            The valid value of rarity is: "COMMON" "UNCOMMON" "RARE" "VERY_RARE"
            """)
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
    @ConfigEntry.Gui.CollapsibleObject
    public final HeavyBlowOptions heavyBlowOptions = new HeavyBlowOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final WindBladeOptions windBladeOptions = new WindBladeOptions();

    public static class BaseOptions {
        BaseOptions(int maxLevel, Rarity rarity) {
            this.level = maxLevel;
            this.rarity = rarity;
        }
        @Comment("====== Basic options below ======")
        public int level;
        public Rarity rarity;
        public boolean isTreasure = false;
        public boolean isTradeable = true;
        public boolean isAllowedOnBooks = true;
        public boolean isDiscoverable = true;

    }

    public static class AdvancedLootingOptions extends BaseOptions {
        AdvancedLootingOptions() {
            super(3, Rarity.VERY_RARE);
        }
        @Comment("Looting level += level * multiplier (vanilla Looting X provides X looting level)")
        public int lootingLevelMultiplier = 2;
    }

    public static class AdvancedSharpnessOptions extends BaseOptions{
        AdvancedSharpnessOptions() {
            super(5, Rarity.RARE);
        }
    }

    public static class CounterattackOptions extends BaseOptions{
        CounterattackOptions() {
            super(3, Rarity.UNCOMMON);
        }
    }

    public static class EaterOfSoulsOptions extends BaseOptions{
        EaterOfSoulsOptions() {
            super(3, Rarity.VERY_RARE);
            super.isTreasure = true;
        }
        @Comment("damage += sqrt(kill count) * level * multiplier")
        public float damageMultiplier = 0.1f;
    }

    public static class GiftOfFireOptions extends BaseOptions{
        GiftOfFireOptions() {
            super(3, Rarity.UNCOMMON);
        }
        @Comment("damage += level * multiplier")
        public float beneficialMultiplier = 2.0f;
        @Comment("damage -= level * multiplier")
        public float harmfulMultiplier = 0.5f;
    }

    public static class LightnessOptions extends BaseOptions{
        LightnessOptions() {
            super(3, Rarity.COMMON);
        }
        @Comment("speed while blocking *= 1 + level * multiplier")
        public float speedMultiplier = 0.8f;
    }

    public static class OceanCurrentOptions extends BaseOptions{
        OceanCurrentOptions() {
            super(3, Rarity.RARE);
        }
        @Comment("attack speed += level * multiplier")
        public float speedMultiplier = 0.5f;
        @Comment("attack speed while in water += level * multiplier * extra multiplier")
        public float extraSpeedMultiplier = 1.5f;
    }

    public static class PyromaniacOptions extends BaseOptions{
        PyromaniacOptions() {
            super(5, Rarity.VERY_RARE);
        }
        @Comment("Heal Amount = explosive damage * multiplayer * level")
        public float healMultiplier = 0.1f;
        @Comment("Durability value of armor -= base + healAmount * multiplier")
        public int armorBaseDamage = 3;
        public float damageMultiplier = 1.0f;
    }

    public static class ReflectingOptions extends BaseOptions{
        ReflectingOptions() {
            super(5, Rarity.UNCOMMON);
        }
        @Comment("Reflected project velocity = 1.0 + baseVelocity * level")
        public float baseVelocity = 0.5f;
        @Comment("Durability value of shield -= base")
        public int baseDamage = 3;
    }

    public static class SolidAsARockOptions extends BaseOptions{
        SolidAsARockOptions() {
            super(3, Rarity.RARE);
        }
        @Comment("armor *= 1 + multiplier * level")
        public float armorMultiplier = 0.15f;
        @Comment("toughness *= 1 + multiplier * level")
        public float toughnessMultiplier = 0.1f;
    }

    public static class OverflowOptions extends BaseOptions{
        OverflowOptions() {
            super(1, Rarity.RARE);
        }
        @Comment("The probability of generating a puddle of water")
        public double probability = 0.05;
    }

    public static class FireDisasterOptions extends BaseOptions{
        FireDisasterOptions() {
            super(1, Rarity.RARE);
        }
        @Comment("The probability of generating a fire")
        public double probability = 0.05;
    }

    public static class TheFallenOptions extends BaseOptions {
        TheFallenOptions() {
            super(3, Rarity.VERY_RARE);
        }
        @Comment("attack damage *= 1 + multiplier * level * numberOfCurse")
        public float damageMultiplier = 0.4f;
    }

    public static class EmpathyOptions extends BaseOptions{
        EmpathyOptions() {
            super(1, Rarity.RARE);
            super.isTreasure = true;
        }
        public double shootPowerMultiplier = 0.1;
    }

    public static class RollingStoneOptions extends BaseOptions{
        RollingStoneOptions() {
            super(3, Rarity.UNCOMMON);
        }
        @Comment("damage += multiplier * level + speed * damageBonusMultiplier")
        public double damageMultiplier = 3.0;
        public double damageBonusMultiplier = 0.5;
        @Comment("damageTaken *= 1 - reducer * level")
        public double damageReducer = 0.1;

    }

    public static class BlessedWindOptions extends BaseOptions{
        BlessedWindOptions() {
            super(3, Rarity.UNCOMMON);
        }
        @Comment("speed while sprinting += level * multiplier (The initial walking speed is 0.1 and the sprinting speed is 0.133)")
        public float sprintSpeedMultiplier = 0.04f;
        @Comment("speed while walking += level * multiplier")
        public float walkSpeedMultiplier = 0.01f;
    }

    public static class HeavyBlowOptions extends BaseOptions{
        HeavyBlowOptions() {
            super(3, Rarity.VERY_RARE);
        }
        @Comment("rate = baseRate * level")
        public double baseRate = 0.1;
        @Comment("damage *= 1 + level * multiplier")
        public double damageMultiplier = 1.0;
    }

    public static class WindBladeOptions extends BaseOptions{
        WindBladeOptions() {
            super(3, Rarity.VERY_RARE);
        }
        @Comment("damage *= 1 + extraSpeedAttributeValue * level * BaseMultiplier")
        public double baseDamageMultiplier = 0.5;
    }
}
