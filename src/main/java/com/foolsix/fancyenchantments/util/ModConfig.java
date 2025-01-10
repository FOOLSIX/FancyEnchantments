package com.foolsix.fancyenchantments.util;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils.Element;
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
            The elemental conditions in order are Aer, Aqua, Ignis, Terra, Holy, Twisted
            """)
    @ConfigEntry.Gui.CollapsibleObject
    public boolean enableIncompatibility = true;
    @ConfigEntry.Gui.CollapsibleObject
    public boolean enableModBookTexture = true;
    @ConfigEntry.Gui.CollapsibleObject
    public final ElementStatOptions elementStatOptions = new ElementStatOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final ChestLootOptions chestLootOptions = new ChestLootOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final JEIInfoOptions jeiInfoOptions = new JEIInfoOptions();

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
    public final StackingWavesOptions stackingWavesOptions = new StackingWavesOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final ChargeOptions chargeOptions = new ChargeOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final LavaBurstOptions lavaBurstOptions = new LavaBurstOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final ArmorForgingOptions armorForgingOptions = new ArmorForgingOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final NirvanaOptions nirvanaOptions = new NirvanaOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final SelfImmolationOptions selfImmolationOptions = new SelfImmolationOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final BubbleShieldOptions bubbleShieldOptions = new BubbleShieldOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final DrowningOptions drowningOptions = new DrowningOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final PervertOptions pervertOptions = new PervertOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final PureFateOptions pureFateOptions = new PureFateOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final BloodSacrificeOptions bloodSacrificeOptions = new BloodSacrificeOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final SharpRockOptions sharpRockOptions = new SharpRockOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final DelayedExecutionOptions delayedExecutionOptions = new DelayedExecutionOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final SacredSupremeSharpnessOptions sacredSupremeSharpnessOptions = new SacredSupremeSharpnessOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final GreedySupremeLootingOptions greedySupremeLootingOptions = new GreedySupremeLootingOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final WindFireWheelsOptions windFireWheelsOptions = new WindFireWheelsOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final SpreadSporesOptions spreadSporesOptions = new SpreadSporesOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final ThrillingThunderOptions thrillingThunderOptions = new ThrillingThunderOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final RecoilOptions recoilOptions = new RecoilOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final AirAttackOptions airAttackOptions = new AirAttackOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final ErodingOptions erodingOptions = new ErodingOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final SanderOptions sanderOptions = new SanderOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final AdvancedProtectionOptions advancedProtectionOptions = new AdvancedProtectionOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final MountainSupremeProtectionOptions mountainSupremeProtectionOptions = new MountainSupremeProtectionOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final AfterimageOptions afterimageOptions = new AfterimageOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final StandingWallOptions standingWallOptions = new StandingWallOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final ConditionOverloadOptions conditionOverloadOptions = new ConditionOverloadOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final FearlessChallengerOptions fearlessChallengerOptions = new FearlessChallengerOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final StreamlineOptions windWingsOptions = new StreamlineOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final HeavyArrowOptions heavyArrowOptions = new HeavyArrowOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final DownwindOptions downwindOptions = new DownwindOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final NightmareOptions nightmareOptions = new NightmareOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final DexterityOptions dexterityOptions = new DexterityOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final CrackedCrownOptions crackedCrownOptions = new CrackedCrownOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final PurificationSlashOptions purificationSlashOptions = new PurificationSlashOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final SighsOfAshesOptions sighsOfAshesOptions = new SighsOfAshesOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final AdvancedFireAspectOptions advancedFireAspectOptions = new AdvancedFireAspectOptions();
    @ConfigEntry.Gui.CollapsibleObject
    public final AdvancedFlameOptions advancedFlameOptions = new AdvancedFlameOptions();

    public static class ElementStatOptions {
        public int aerLevelToGetSpeed = 5;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 255)
        public int maxAerEffectLevel = 6;
        public int ignisLevelToGetFireResistance = 10;
        public int ignisLevelToGetStrength = 4;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 255)
        public int maxIgnisEffectLevel = 6;
        public int aquaLevelToGetRegeneration = 6;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 255)
        public int maxAquaEffectLevel = 6;
        public int terraLevelToGetResistance = 9;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 255)
        public int maxTerraEffectLevel = 4;
        @Comment("actually twisted level - holy level")
        public int twistedLevelToGetDebuff = 10;
        @Comment("The probability of getting a debuff per second")
        public double probabilityToGetDebuff = 0.02;
        @Comment("second")
        public int debuffDuration = 3;
    }

    public static class ChestLootOptions {
        @Comment("The chance of spawning a mod enchanted book")
        public double chanceOfSpawningBook = 0.3;
    }

    public static class JEIInfoOptions {
        public boolean enableDescription = true;
        public boolean enableMaxLevel = true;
        public boolean enableRarity = true;
    }

    public static class BaseOptions {
        @Comment("====== Basic options below ======")
        @ConfigEntry.BoundedDiscrete(max = 255)
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

    public static class LootEnchantmentOptions extends BaseOptions {
        @Comment("====== Chest Loot Condition ======")
        public double probabilityOfGeneration;
        public int[] elementalCondition = new int[Element.values().length];

        LootEnchantmentOptions(int maxLevel, Rarity rarity, double probability) {
            super(maxLevel, rarity);
            isTreasure = true;
            isTradeable = false;
            isDiscoverable = false;
            this.probabilityOfGeneration = probability;
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
        public float damageMultiplier = 1f;

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
        @Comment("damage cap")
        public float cap = 1000f;

        EaterOfSoulsOptions() {
            super(3, Rarity.VERY_RARE);
            super.isTreasure = true;
        }
    }

    public static class GiftOfFireOptions extends BaseOptions {
        @Comment("damage += level * multiplier")
        public float beneficialMultiplier = 2.5f;
        @Comment("damage -= level * multiplier")
        public float harmfulMultiplier = 0.5f;

        GiftOfFireOptions() {
            super(5, Rarity.UNCOMMON);
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
        public float speedMultiplier = 0.2f;
        @Comment("attack speed while in water += level * multiplier * extra multiplier")
        public float extraSpeedMultiplier = 1.5f;
        public boolean ineffectiveWhenOnFire = true;

        OceanCurrentOptions() {
            super(3, Rarity.VERY_RARE);
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
        public double armorMultiplier = 0.15;
        @Comment("toughness *= 1 + multiplier * level")
        public double toughnessMultiplier = 0.1;
        public double movementSpeedReducer = 0.1;

        SolidAsARockOptions() {
            super(3, Rarity.RARE);
        }
    }

    public static class OverflowOptions extends BaseOptions {
        @Comment("The probability of generating a puddle of water (per level)")
        public double probability = 0.1;

        OverflowOptions() {
            super(3, Rarity.RARE);
        }
    }

    public static class FireDisasterOptions extends BaseOptions {
        @Comment("The probability of generating a fire (per level)")
        public double probability = 0.05;

        FireDisasterOptions() {
            super(3, Rarity.RARE);
        }
    }

    public static class TheFallenOptions extends BaseOptions {
        @Comment("weapon attack damage *= 1 + multiplier * level * numberOfCurse")
        public float damageMultiplier = 0.3f;

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
        public float saturationCap = 25.0f;

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
        @Comment("taken damage *= multiplier")
        public float damageMultiplier = 0.1f;

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

        MelterOptions() {
            super(3, Rarity.RARE);
        }
    }

    public static class StackingWavesOptions extends BaseOptions {
        public float attackSpeedMultiplier = 0.3f;
        @Comment("Attack speed * = 1 - reducer * level")
        public float attackSpeedReducer = 0.1f;
        @Comment("second")
        public int duration = 2;

        StackingWavesOptions() {
            super(3, Rarity.RARE);
        }
    }

    public static class ChargeOptions extends BaseOptions {
        public float chargeDistanceMultiplier = 2f;
        @Comment("Duration = 5 + durationPerLevel * level (tick)")
        public int invincibleDurationPerLevel = 5;

        ChargeOptions() {
            super(3, Rarity.RARE);
            isTreasure = true;
        }
    }

    public static class LavaBurstOptions extends LootEnchantmentOptions {
        @Comment("The probability of generating a burst = level * probability")
        public double probability = 0.2;

        LavaBurstOptions() {
            super(3, Rarity.VERY_RARE, 0.25);
            elementalCondition[Element.IGNIS.ordinal()] = 5;
            elementalCondition[Element.TERRA.ordinal()] = 3;
        }
    }

    public static class ArmorForgingOptions extends LootEnchantmentOptions {
        @Comment("Upper limit of forging value = value * level")
        public int forgingValue = 1000;
        @Comment("Increased armor = value / base * 100%")
        public int armorBase = 5000;
        public int toughnessBase = 10000;

        ArmorForgingOptions() {
            super(3, Rarity.VERY_RARE, 0.1);
            elementalCondition[Element.IGNIS.ordinal()] = 6;
            elementalCondition[Element.TERRA.ordinal()] = 6;
        }
    }

    public static class NirvanaOptions extends BaseOptions {
        @Comment("Minimum health ratio to trigger the effect")
        public double minimumHealthRatio = 0.5;
        @Comment("Trigger interval (second)")
        public int interval = 10;
        @Comment("instant health duration (tick)")
        public int duration = 3;

        NirvanaOptions() {
            super(3, Rarity.RARE);
        }
    }

    public static class SelfImmolationOptions extends BaseOptions {
        public double baseProbability = 0.05;
        @Comment("Probability of being ignited if target is on fire")
        public double probabilityWithFire = 0.5;
        @Comment("Second")
        public int fireTime = 3;

        SelfImmolationOptions() {
            super(1, Rarity.RARE);
        }
    }

    public static class BubbleShieldOptions extends BaseOptions {
        @Comment("Triggering condition: airSupplyValue >= maxValue * Ratio")
        public double airSupplyRatio = 0.8;
        public double costRatio = 0.7;
        @Comment("damage -= airSupplyValue * costRatio * multiplier * level")
        public double multiplier = 0.1;

        BubbleShieldOptions() {
            super(3, Rarity.RARE);
        }
    }

    public static class DrowningOptions extends BaseOptions {
        public int airSupplyValue = -40;
        public float damage = 1.0f;

        DrowningOptions() {
            super(1, Rarity.RARE);
        }
    }

    public static class PervertOptions extends BaseOptions {
        public double dropProbability = 0.9;

        PervertOptions() {
            super(1, Rarity.VERY_RARE);
        }
    }

    public static class PureFateOptions extends LootEnchantmentOptions {
        @Comment("Probability of removing curse per level")
        public double probability = 0.1;

        PureFateOptions() {
            super(3, Rarity.VERY_RARE, 0.1);
            elementalCondition[Element.HOLY.ordinal()] = 3;
        }
    }

    public static class BloodSacrificeOptions extends LootEnchantmentOptions {
        @Comment("damage to player per level")
        public float damage = 2.0f;
        @Comment("damage *= 1 + (base + LostHealth / MaxHealth) * level")
        public float base = 0.1f;

        BloodSacrificeOptions() {
            super(3, Rarity.VERY_RARE, 0.1);
            elementalCondition[Element.TWISTED.ordinal()] = 3;
        }
    }

    public static class SharpRockOptions extends LootEnchantmentOptions {
        @Comment("damage += armor * damageMultiplier * level")
        public double damageMultiplierForShield = 0.4;
        public double damageMultiplierForOthers = 0.2;

        SharpRockOptions() {
            super(3, Rarity.VERY_RARE, 0.25);
            elementalCondition[Element.TERRA.ordinal()] = 5;
        }
    }

    public static class DelayedExecutionOptions extends BaseOptions {
        @Comment("Proportion of damage to store")
        public float storedDamage = 0.8f;
        @Comment("damage = storedDamage * (1 + multiplier * level)")
        public float damageMultiplier = 0.8f;
        @Comment("second")
        public int duration = 5;

        DelayedExecutionOptions() {
            super(3, Rarity.VERY_RARE);
        }
    }

    public static class SacredSupremeSharpnessOptions extends LootEnchantmentOptions {
        @Comment("damage += base + level * multiplier")
        public float baseDamage = 5;
        public float damageMultiplier = 1.5f;
        public float damageMultiplierToUndead = 3f;

        SacredSupremeSharpnessOptions() {
            super(5, Rarity.VERY_RARE, 0.25);
            elementalCondition[Element.HOLY.ordinal()] = 3;
        }
    }

    public static class GreedySupremeLootingOptions extends LootEnchantmentOptions {
        @Comment("loot level += multiplier * level (probability * level double it)")
        public int lootLevelMultiplier = 3;
        public double probabilityOfDoubling = 0.05;

        GreedySupremeLootingOptions() {
            super(3, Rarity.VERY_RARE, 0.25);
            elementalCondition[Element.TWISTED.ordinal()] = 3;
        }
    }

    public static class WindFireWheelsOptions extends LootEnchantmentOptions {
        public double speedMultiplier = 0.06;

        WindFireWheelsOptions() {
            super(1, Rarity.VERY_RARE, 0.1);
            elementalCondition[Element.AER.ordinal()] = 5;
            elementalCondition[Element.IGNIS.ordinal()] = 5;
        }
    }

    public static class SpreadSporesOptions extends LootEnchantmentOptions {
        @Comment("Reaching the cap triggers the damage")
        public int sporeCap = 10;
        public double damageMultiplier = 3.0;

        SpreadSporesOptions() {
            super(3, Rarity.VERY_RARE, 0.2);
            elementalCondition[Element.AQUA.ordinal()] = 5;
            elementalCondition[Element.TERRA.ordinal()] = 5;
        }
    }

    public static class ThrillingThunderOptions extends LootEnchantmentOptions {
        @Comment("time gap of causing damage")
        public int tickGap = 10;
        @Comment("damage = effect level * multiplier")
        public double damageMultiplier = 1.0;
        public double probabilityOfApplyingEffectPerLevel = 0.1;

        ThrillingThunderOptions() {
            super(3, Rarity.VERY_RARE, 0.2);
            elementalCondition[Element.AER.ordinal()] = 5;
            elementalCondition[Element.AQUA.ordinal()] = 5;
        }
    }

    public static class RecoilOptions extends BaseOptions {
        public double distanceMultiplierPerLevel = 0.5;
        public double probabilityPerLevel = 0.2;

        RecoilOptions() {
            super(3, Rarity.RARE);
        }
    }

    public static class AirAttackOptions extends BaseOptions {
        @Comment("damage = fallDistance * multiplier * level")
        public float damageMultiplier = 0.3f;

        AirAttackOptions() {
            super(3, Rarity.RARE);
        }
    }

    public static class ErodingOptions extends BaseOptions {
        public double probabilityPerLevel = 0.15;
        public int damageMultiplier = 1;

        ErodingOptions() {
            super(3, Rarity.RARE);
        }
    }

    public static class SanderOptions extends BaseOptions {
        public double damageMultiplier = 1.0;

        SanderOptions() {
            super(3, Rarity.RARE);
        }
    }

    public static class AdvancedProtectionOptions extends BaseOptions {
        @Comment("enchantment protection factor per level, can not exceed vanilla limit")
        public double EPFMultiplier = 2.0;

        AdvancedProtectionOptions() {
            super(4, Rarity.RARE);
        }
    }

    public static class MountainSupremeProtectionOptions extends LootEnchantmentOptions {
        public double EPFMultiplier = 3.0;
        @Comment("Fixed damage reduction, per level")
        public double reducer = 0.5;

        MountainSupremeProtectionOptions() {
            super(4, Rarity.VERY_RARE, 0.2);
            elementalCondition[Element.TERRA.ordinal()] = 5;
        }
    }

    public static class AfterimageOptions extends BaseOptions {
        @Comment("probability of dodging = extraSpeed(%) * multiplier")
        public double probabilityMultiplier = 0.5;
        public double probabilityCapPerLevel = 0.1;
        public double probabilityMaxCap = 0.5;

        AfterimageOptions() {
            super(3, Rarity.VERY_RARE);
            isTreasure = true;
        }
    }

    public static class StandingWallOptions extends LootEnchantmentOptions {
        StandingWallOptions() {
            super(1, Rarity.RARE, 0.1);
            elementalCondition[Element.TERRA.ordinal()] = 8;
        }
    }

    public static class ConditionOverloadOptions extends BaseOptions {
        @Comment("damage *= 1 + multiplier * level * debuffCount")
        public double damageMultiplier = 0.05;

        ConditionOverloadOptions() {
            super(3, Rarity.VERY_RARE);
        }
    }

    public static class FearlessChallengerOptions extends LootEnchantmentOptions {
        @Comment("trigger when target's HP / user's HP > condition")
        public double HPCondition = 3;
        @Comment("damage *= 1 + target's HP / user's HP * multiplier * level")
        public double multiplier = 1;
        @Comment("damageBonusCap")
        public double cap = 10;

        FearlessChallengerOptions() {
            super(3, Rarity.VERY_RARE, 0.1);
            elementalCondition[Element.HOLY.ordinal()] = 8;
        }
    }

    public static class StreamlineOptions extends BaseOptions {
        public double speedMultiplierPerLevel = 0.5;

        StreamlineOptions() {
            super(3, Rarity.VERY_RARE);
        }
    }

    public static class HeavyArrowOptions extends BaseOptions {
        public double damageMultiplierPerLevel = 0.15;
        public int knockbackAddonPerLevel = 1;

        HeavyArrowOptions() {
            super(3, Rarity.VERY_RARE);
        }
    }

    public static class DownwindOptions extends BaseOptions {
        public double pushForceMultiplier = 2.0;
        public double damageMultiplierPerLevel = 0.2;

        DownwindOptions() {
            super(3, Rarity.VERY_RARE);
        }
    }

    public static class NightmareOptions extends BaseOptions {
        public int explodeRadiusMultiplier = 2;

        NightmareOptions() {
            super(3, Rarity.RARE);
        }
    }

    public static class DexterityOptions extends BaseOptions {
        public double rangeMultiplierPerLevel = 0.5;

        DexterityOptions() {
            super(3, Rarity.VERY_RARE);
        }
    }

    public static class CrackedCrownOptions extends BaseOptions {
        public double damageMultiplier = 0.3;
        public double takenDamageMultiplier = 0.3;
        CrackedCrownOptions() {
            super(3, Rarity.VERY_RARE);
        }
    }

    public static class PurificationSlashOptions extends BaseOptions {
        public double damageMultiplier = 0.01;

        PurificationSlashOptions() {
            super(1, Rarity.RARE);
        }
    }

    public static class SighsOfAshesOptions extends LootEnchantmentOptions {
        public double damagePerSecond = 0.2;

        SighsOfAshesOptions() {
            super(1, Rarity.VERY_RARE, 0.1);
        }
    }

    public static class AdvancedFireAspectOptions extends BaseOptions {
        public int increasedSecondPerHit = 8;

        AdvancedFireAspectOptions() {
            super(2, Rarity.VERY_RARE);
        }
    }

    public static class AdvancedFlameOptions extends BaseOptions {
        public int increasedSecondPerHit = 8;

        AdvancedFlameOptions() {
            super(1, Rarity.VERY_RARE);
        }
    }
}
