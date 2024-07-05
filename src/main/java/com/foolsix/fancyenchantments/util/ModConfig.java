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
    public final boolean enableIncompatibility = true;
    @ConfigEntry.Gui.CollapsibleObject
    public final ElementStatOptions elementStatOptions = new ElementStatOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final ChestLootOptions chestLootOptions = new ChestLootOptions();
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
    public final BlessedWindOptions blessedWindOptions = new BlessedWindOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final HeavyBlowOptions heavyBlowOptions = new HeavyBlowOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final WindBladeOptions windBladeOptions = new WindBladeOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final FloatingOptions floatingOptions = new FloatingOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final CumbersomeOptions cumbersomeOptions = new CumbersomeOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final BullyingOptions bullyingOptions = new BullyingOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final HungryOptions hungryOptions = new HungryOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final DominionOptions dominionOptions = new DominionOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final BloodthirstyOptions bloodthirstyOptions = new BloodthirstyOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final FallingStoneOptions fallingStoneOptions = new FallingStoneOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final FeatherFallOptions featherFallOptions = new FeatherFallOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final UnyieldingSpiritOptions unyieldingSpiritOptions = new UnyieldingSpiritOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final CursedGazeOptions cursedGazeOptions = new CursedGazeOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final CalmerOptions calmerOptions = new CalmerOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final FeintAttackOptions feintAttackOptions = new FeintAttackOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final PurifyingOptions purifyingOptions = new PurifyingOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final DuellistsPrerogativeOptions duellistsPrerogativeOptions = new DuellistsPrerogativeOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final PaladinsShieldOptions paladinsShieldOptions = new PaladinsShieldOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final EucharistOptions eucharistOptions = new EucharistOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final MelterOptions melterOptions = new MelterOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final LavaBurstOptions lavaBurstOptions = new LavaBurstOptions();



    public static class ElementStatOptions {
        public int aerLevelToGetSpeed = 5;
        public int ignisLevelToGetFireResistance = 8;
        @Comment("Can amplify the effect")
        public int aquaLevelToGetRegeneration = 6;
        @Comment("Can amplify the effect")
        public int terraLevelToGetResistance = 9;
        @Comment("actually twisted level - holy level")
        public int twistedLevelToGetDebuff = 10;
        @Comment("The probability of getting a debuff per second")
        public float probabilityToGetDebuff = 0.02f;
        @Comment("second")
        public int debuffDuration = 3;
    }

    public static class ChestLootOptions{
        @Comment("The chance of spawning a mod enchanted book")
        public double chanceOfSpawningBook = 0.5f;
    }

    public static class BaseOptions {
        @Comment("====== Basic options below ======")
        public int level;
        public Rarity rarity;
        public boolean isTreasure = false;
        public boolean isTradeable = true;
        public boolean isAllowedOnBooks = true;
        public boolean isDiscoverable = true;

        BaseOptions(int maxLevel, Rarity rarity) {
            this.level = maxLevel;
            this.rarity = rarity;
        }

    }

    public static class AdvancedLootingOptions extends BaseOptions {
        @Comment("Looting level += level * multiplier (There is a level / (level + 5) probability of adding additional levels) \n" +
                "(vanilla Looting X provides X looting level)")
        public int lootingLevelMultiplier = 2;

        AdvancedLootingOptions() {
            super(3, Rarity.VERY_RARE);
        }
    }

    public static class AdvancedSharpnessOptions extends BaseOptions {
        @Comment("Damage += base + multiplier * level")
        public float baseDamage = 1.5f;
        public float damageMultiplier = 0.8f;

        AdvancedSharpnessOptions() {
            super(5, Rarity.RARE);
        }
    }

    public static class CounterattackOptions extends BaseOptions {
        CounterattackOptions() {
            super(3, Rarity.UNCOMMON);
        }
    }

    public static class EaterOfSoulsOptions extends BaseOptions {
        @Comment("damage += sqrt(kill count) * level * multiplier")
        public float damageMultiplier = 0.3f;

        EaterOfSoulsOptions() {
            super(3, Rarity.VERY_RARE);
            super.isTreasure = true;
        }
    }

    public static class GiftOfFireOptions extends BaseOptions {
        @Comment("damage += level * multiplier")
        public float beneficialMultiplier = 2.0f;
        @Comment("damage -= level * multiplier")
        public float harmfulMultiplier = 0.5f;

        GiftOfFireOptions() {
            super(3, Rarity.UNCOMMON);
        }
    }

    public static class LightnessOptions extends BaseOptions {
        @Comment("speed while blocking *= 1 + level * multiplier")
        public float speedMultiplier = 0.8f;

        LightnessOptions() {
            super(3, Rarity.COMMON);
        }
    }

    public static class OceanCurrentOptions extends BaseOptions {
        @Comment("attack speed += level * multiplier")
        public float speedMultiplier = 0.5f;
        @Comment("attack speed while in water += level * multiplier * extra multiplier")
        public float extraSpeedMultiplier = 1.5f;
        public boolean ineffectiveWhenOnFire = true;

        OceanCurrentOptions() {
            super(3, Rarity.RARE);
        }
    }

    public static class PyromaniacOptions extends BaseOptions {
        @Comment("Heal Amount = explosive damage * multiplayer * level")
        public float healMultiplier = 0.1f;
        @Comment("Durability value of armor -= base + healAmount * multiplier")
        public int armorBaseDamage = 3;
        public float damageMultiplier = 1.0f;

        PyromaniacOptions() {
            super(5, Rarity.VERY_RARE);
        }
    }

    public static class ReflectingOptions extends BaseOptions {
        @Comment("Reflected project velocity = 1.0 + baseVelocity * level")
        public float baseVelocity = 0.5f;
        @Comment("Durability value of shield -= base")
        public int baseDamage = 3;

        ReflectingOptions() {
            super(5, Rarity.UNCOMMON);
        }
    }

    public static class SolidAsARockOptions extends BaseOptions {
        @Comment("armor *= 1 + multiplier * level")
        public float armorMultiplier = 0.15f;
        @Comment("toughness *= 1 + multiplier * level")
        public float toughnessMultiplier = 0.1f;

        SolidAsARockOptions() {
            super(3, Rarity.RARE);
        }
    }

    public static class OverflowOptions extends BaseOptions {
        @Comment("The probability of generating a puddle of water")
        public double probability = 0.05;

        OverflowOptions() {
            super(1, Rarity.RARE);
        }
    }

    public static class FireDisasterOptions extends BaseOptions {
        @Comment("The probability of generating a fire")
        public double probability = 0.05;

        FireDisasterOptions() {
            super(1, Rarity.RARE);
        }
    }

    public static class TheFallenOptions extends BaseOptions {
        @Comment("attack damage *= 1 + multiplier * level * numberOfCurse")
        public float damageMultiplier = 0.4f;

        TheFallenOptions() {
            super(3, Rarity.VERY_RARE);
        }
    }

    public static class EmpathyOptions extends BaseOptions {
        public double shootPowerMultiplier = 0.1;

        EmpathyOptions() {
            super(1, Rarity.RARE);
            super.isTreasure = true;
        }
    }

    public static class RollingStoneOptions extends BaseOptions {
        @Comment("damage += multiplier * level * speed")
        public float damageMultiplier = 25f;
        @Comment("damageTaken *= 1 - reducer * level")
        public float damageReducer = 0.1f;
        @Comment("Lower limit, Minimum percentage of damage taken")
        public float lowerLimit = 0.5f;
        RollingStoneOptions() {
            super(3, Rarity.UNCOMMON);
        }

    }

    public static class BlessedWindOptions extends BaseOptions {
        @Comment("speed while sprinting += level * multiplier (The initial walking speed is 0.1 and the sprinting speed is 0.133)")
        public float sprintSpeedMultiplier = 0.04f;
        @Comment("speed while walking += level * multiplier")
        public float walkSpeedMultiplier = 0.01f;

        BlessedWindOptions() {
            super(3, Rarity.UNCOMMON);
        }
    }

    public static class HeavyBlowOptions extends BaseOptions {
        @Comment("rate = baseRate * level")
        public double baseRate = 0.1;
        @Comment("damage *= 1 + level * multiplier")
        public double damageMultiplier = 1.0;
        @Comment("attackSpeed *= 1 - reducer")
        public double speedReducer = 0.1;

        HeavyBlowOptions() {
            super(3, Rarity.VERY_RARE);
        }
    }

    public static class WindBladeOptions extends BaseOptions {
        @Comment("damage *= 1 + extraSpeedAttributeValue * level * BaseMultiplier")
        public double baseDamageMultiplier = 0.5;
        @Comment("damage *= 1 - reducer")
        public double damageReducer = 0.1;

        WindBladeOptions() {
            super(3, Rarity.VERY_RARE);
        }
    }

    public static class FloatingOptions extends BaseOptions {
        @Comment("damage *= 1 - reducer")
        public double damageReducer = 0.2;

        FloatingOptions() {
            super(1, Rarity.RARE);
        }
    }

    public static class CumbersomeOptions extends BaseOptions {
        @Comment("The probability of get debuff")
        public double probability = 0.05;
        @Comment("attack speed *= 1 - reducer")
        public double atkSpeedReducer = 0.2;
        @Comment("Duration (second)")
        public int duration = 3;

        CumbersomeOptions() {
            super(1, Rarity.RARE);
        }
    }

    public static class BullyingOptions extends BaseOptions {
        BullyingOptions() {
            super(1, Rarity.RARE);
            super.isTreasure = true;
        }
    }

    public static class HungryOptions extends BaseOptions {
        @Comment("The probability of generating a copy = level * probability")
        public double probability = 0.2;

        HungryOptions() {
            super(3, Rarity.RARE);
        }
    }

    public static class DominionOptions extends BaseOptions {
        @Comment("Damage += sum of enchantment level * multiplier")
        public float damageMultiplier = 1.0f;

        DominionOptions() {
            super(1, Rarity.VERY_RARE);
            super.isTreasure = true;
            super.isTradeable = false;
        }
    }

    public static class BloodthirstyOptions extends BaseOptions {
        @Comment("Hunger Value += damageValue * multiplier")
        public float hungerMultiplier = 0.5f;
        public int hungerUpperLimit = 20;
        @Comment("Saturation += damageValue * multiplier")
        public float saturationMultiplier = 0.2f;

        BloodthirstyOptions() {
            super(1, Rarity.VERY_RARE);
            super.isTreasure = true;
        }
    }

    public static class FallingStoneOptions extends BaseOptions {
        @Comment("damage = (1 + level * multiplier) * fallingDistance")
        public float damageMultiplier = 0.5f;

        FallingStoneOptions() {
            super(3, Rarity.UNCOMMON);
        }
    }

    public static class FeatherFallOptions extends BaseOptions {
        FeatherFallOptions() {
            super(1, Rarity.RARE);
        }
    }

    public static class UnyieldingSpiritOptions extends BaseOptions {
        @Comment("Extra Time To Live (second)")
        public int extraTime = 8;
        @Comment("Durability value of shield -= base")
        public int baseDamage = 5;
        @Comment("The health set when enchantment takes effect, percentage")
        public float healthPercentage = 0.5f;
        @Comment("Slowness effect level, set 0 to disable")
        public int slownessLevel = 3;
        @Comment("Damage resistance effect level, set 0 to disable")
        public int damageResistanceLevel = 3;

        public boolean enableBlindness = false;

        UnyieldingSpiritOptions() {
            super(1, Rarity.VERY_RARE);
        }
    }

    public static class CursedGazeOptions extends BaseOptions {
        @Comment("The effective distance = min (level * baseDistance, 128)")
        public double baseDistance = 5.0;

        CursedGazeOptions() {
            super(3, Rarity.VERY_RARE);
        }
    }

    public static class CalmerOptions extends BaseOptions {
        @Comment("Cooldown (second)")
        public int cooldown = 5;
        @Comment("Duration (second)")
        public int duration = 3;

        CalmerOptions() {
            super(5, Rarity.RARE);
        }
    }

    public static class FeintAttackOptions extends BaseOptions {
        @Comment("Damage to the target *= 1 - reducer")
        public float damageReducer = 0.7f;
        @Comment("Damage to others *= 1 + multiplier * level")
        public float damageMultiplier = 0.2f;

        public FeintAttackOptions() {
            super(3, Rarity.RARE);
            super.isTreasure = true;
        }
    }

    public static class PurifyingOptions extends BaseOptions {
        @Comment("Damage to undead += 1 + addon * level")
        public float addon = 3.0f;

        PurifyingOptions() {
            super(5, Rarity.RARE);
        }
    }

    public static class DuellistsPrerogativeOptions extends BaseOptions {
        @Comment("Damage *= 1 + multiplier * level")
        public float damageMultiplier = 0.2f;

        DuellistsPrerogativeOptions() {
            super(3, Rarity.VERY_RARE);
        }
    }

    public static class PaladinsShieldOptions extends BaseOptions {
        public float damageTransferRatio = 0.5f;
        @Comment("Damage reduction while holding a shield -= baseDamageReductionRatio * level")
        public float baseDamageReductionRatio = 0.05f;
        @Comment("Damage the enchantment holder receives = damage * damageTransferRatio * transferredDamageRatio")
        public float transferredDamageRatio = 0.5f;
        @Comment("If the final damage exceeds the upperLimit * receiver's maxHealth, it will not be transferred.")
        public float upperLimit = 0.5f;

        PaladinsShieldOptions() {
            super(3, Rarity.VERY_RARE);
        }
    }

    public static class EucharistOptions extends BaseOptions {
        @Comment("The minimum hunger point to obtain the buff")
        public int minimumHunger = 6;
        @Comment("The effect duration = multiplier * foodSaturation (second)")
        public int durationMultiplier = 3;

        EucharistOptions() {
            super(3, Rarity.VERY_RARE);
        }
    }

    public static class MelterOptions extends BaseOptions {
        @Comment("All level, reduce base damage")
        public double damageReducer = 0.5;
        @Comment("Armor *= 1 - reducer * level")
        public double armorReducer = 0.2;
        @Comment("The effect duration = level * duration (second)")
        public int duration = 3;
        MelterOptions(){
            super(3, Rarity.RARE);
        }
    }

    public static class LavaBurstOptions extends BaseOptions {
        @Comment("The probability of generating a burst = level * probability")
        public double probability = 0.1;

        LavaBurstOptions() {
            super(3, Rarity.VERY_RARE);
            isTreasure = true;
            isTradeable = false;
            isDiscoverable = false;
        }
    }

}
