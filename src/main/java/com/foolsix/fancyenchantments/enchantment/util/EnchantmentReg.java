package com.foolsix.fancyenchantments.enchantment.util;

import com.foolsix.fancyenchantments.enchantment.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public final class EnchantmentReg {
  public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "fancyenchantments");

  public static final RegistryObject<Enchantment> ADVANCED_FIRE_ASPECT = reg("advanced_fire_aspect", AdvancedFireAspect::new);

  public static final RegistryObject<Enchantment> ADVANCED_FLAME = reg("advanced_flame", AdvancedFlame::new);

  public static final RegistryObject<Enchantment> ADVANCED_LOOTING = reg("advanced_looting", AdvancedLooting::new);

  public static final RegistryObject<Enchantment> ADVANCED_PROTECTION = reg("advanced_protection", AdvancedProtection::new);

  public static final RegistryObject<Enchantment> ADVANCED_SHARPNESS = reg("advanced_sharpness", AdvancedSharpness::new);

  public static final RegistryObject<Enchantment> AFTERIMAGE = reg("afterimage", Afterimage::new);

  public static final RegistryObject<Enchantment> AIR_ATTACK = reg("air_attack", AirAttack::new);

  public static final RegistryObject<Enchantment> ARMOR_FORGING = reg("armor_forging", ArmorForging::new);

  public static final RegistryObject<Enchantment> BLESSED_WIND = reg("blessed_wind", BlessedWind::new);

  public static final RegistryObject<Enchantment> BLOOD_FEED = reg("blood_feed", BloodFeed::new);

  public static final RegistryObject<Enchantment> BLOOD_SACRIFICE = reg("blood_sacrifice", BloodSacrifice::new);

  public static final RegistryObject<Enchantment> BLOODTHIRSTY = reg("bloodthirsty", Bloodthirsty::new);

  public static final RegistryObject<Enchantment> BUBBLE_SHIELD = reg("bubble_shield", BubbleShield::new);

  public static final RegistryObject<Enchantment> BULLYING = reg("bullying", Bullying::new);

  public static final RegistryObject<Enchantment> CALMER = reg("calmer", Calmer::new);

  public static final RegistryObject<Enchantment> CHARGE = reg("charge", Charge::new);

  public static final RegistryObject<Enchantment> CONDITION_OVERLOAD = reg("condition_overload", ConditionOverload::new);

  public static final RegistryObject<Enchantment> COUNTERATTACK = reg("counterattack", Counterattack::new);

  public static final RegistryObject<Enchantment> CRACKED_CROWN = reg("cracked_crown", CrackedCrown::new);

  public static final RegistryObject<Enchantment> CUMBERSOME = reg("cumbersome", Cumbersome::new);

  public static final RegistryObject<Enchantment> CURSED_GAZE = reg("cursed_gaze", CursedGaze::new);

  public static final RegistryObject<Enchantment> DELAYED_EXECUTION = reg("delayed_execution", DelayedExecution::new);

  public static final RegistryObject<Enchantment> DEXTERITY = reg("dexterity", Dexterity::new);

  public static final RegistryObject<Enchantment> DOMINION = reg("dominion", Dominion::new);

  public static final RegistryObject<Enchantment> DOWNWIND = reg("downwind", Downwind::new);

  public static final RegistryObject<Enchantment> DROWNING = reg("drowning", Drowning::new);

  public static final RegistryObject<Enchantment> DUELLISTS_PREROGATIVE = reg("duellists_prerogative", DuellistsPrerogative::new);

  public static final RegistryObject<Enchantment> EATER_OF_SOULS = reg("eater_of_souls", EaterOfSouls::new);

  public static final RegistryObject<Enchantment> EMPATHY = reg("empathy", Empathy::new);

  public static final RegistryObject<Enchantment> ERODING = reg("eroding", Eroding::new);

  public static final RegistryObject<Enchantment> EUCHARIST = reg("eucharist", Eucharist::new);

  public static final RegistryObject<Enchantment> FALLING_STONE = reg("falling_stone", FallingStone::new);

  public static final RegistryObject<Enchantment> FEARLESS_CHALLENGER = reg("fearless_challenger", FearlessChallenger::new);

  public static final RegistryObject<Enchantment> FEATHER_FALL = reg("feather_fall", FeatherFall::new);

  public static final RegistryObject<Enchantment> FEINT_ATTACK = reg("feint_attack", FeintAttack::new);

  public static final RegistryObject<Enchantment> FIRE_DISASTER = reg("fire_disaster", FireDisaster::new);

  public static final RegistryObject<Enchantment> FLOATING = reg("floating", Floating::new);

  public static final RegistryObject<Enchantment> FROZEN_HEART = reg("frozen_heart", FrozenHeart::new);

  public static final RegistryObject<Enchantment> GIFT_OF_FIRE = reg("gift_of_fire", GiftOfFire::new);

  public static final RegistryObject<Enchantment> GREED_SUPREME_LOOTING = reg("greed_supreme_looting", GreedSupremeLooting::new);

  public static final RegistryObject<Enchantment> HEAVY_ARROW = reg("heavy_arrow", HeavyArrow::new);

  public static final RegistryObject<Enchantment> HEAVY_BLOW = reg("heavy_blow", HeavyBlow::new);

  public static final RegistryObject<Enchantment> HUNGRY = reg("hungry", Hungry::new);

  public static final RegistryObject<Enchantment> ICY_BURST = reg("icy_burst", IcyBurst::new);

  public static final RegistryObject<Enchantment> LAVA_BURST = reg("lava_burst", LavaBurst::new);

  public static final RegistryObject<Enchantment> LIGHTNESS = reg("lightness", Lightness::new);

  public static final RegistryObject<Enchantment> MELTER = reg("melter", Melter::new);

  public static final RegistryObject<Enchantment> MOUNTAIN_SUPREME_PROTECTION = reg("mountain_supreme_protection", MountainSupremeProtection::new);

  public static final RegistryObject<Enchantment> MULTIPLE_SHOT = reg("multiple_shot", MultipleShot::new);

  public static final RegistryObject<Enchantment> NIGHTMARE = reg("nightmare", Nightmare::new);

  public static final RegistryObject<Enchantment> NIRVANA = reg("nirvana", Nirvana::new);

  public static final RegistryObject<Enchantment> OCEAN_CURRENT = reg("ocean_current", OceanCurrent::new);

  public static final RegistryObject<Enchantment> OVERFLOW = reg("overflow", Overflow::new);

  public static final RegistryObject<Enchantment> PALADINS_SHIELD = reg("paladins_shield", PaladinsShield::new);

  public static final RegistryObject<Enchantment> PERVERT = reg("pervert", Pervert::new);

  public static final RegistryObject<Enchantment> PURE_FATE = reg("pure_fate", PureFate::new);

  public static final RegistryObject<Enchantment> PURIFICATION_SLASH = reg("purification_slash", PurificationSlash::new);

  public static final RegistryObject<Enchantment> PURIFYING = reg("purifying", Purifying::new);

  public static final RegistryObject<Enchantment> PYROMANIAC = reg("pyromaniac", Pyromaniac::new);

  public static final RegistryObject<Enchantment> RECOIL = reg("recoil", Recoil::new);

  public static final RegistryObject<Enchantment> REFLECTING = reg("reflecting", Reflecting::new);

  public static final RegistryObject<Enchantment> ROLLING_STONE = reg("rolling_stone", RollingStone::new);

  public static final RegistryObject<Enchantment> SACRED_SUPREME_SHARPNESS = reg("sacred_supreme_sharpness", SacredSupremeSharpness::new);

  public static final RegistryObject<Enchantment> SANDER = reg("sander", Sander::new);

  public static final RegistryObject<Enchantment> SELF_IMMOLATION = reg("self_immolation", SelfImmolation::new);

  public static final RegistryObject<Enchantment> SHARP_ROCK = reg("sharp_rock", SharpRock::new);

  public static final RegistryObject<Enchantment> SIGHS_OF_ASHES = reg("sighs_of_ashes", SighsOfAshes::new);

  public static final RegistryObject<Enchantment> SOLID_AS_A_ROCK = reg("solid_as_a_rock", SolidAsARock::new);

  public static final RegistryObject<Enchantment> SPREADING_SPORES = reg("spreading_spores", SpreadingSpores::new);

  public static final RegistryObject<Enchantment> STACKING_WAVES = reg("stacking_waves", StackingWaves::new);

  public static final RegistryObject<Enchantment> STANDING_WALL = reg("standing_wall", StandingWall::new);

  public static final RegistryObject<Enchantment> STREAMLINE = reg("streamline", Streamline::new);

  public static final RegistryObject<Enchantment> THE_FALLEN = reg("the_fallen", TheFallen::new);

  public static final RegistryObject<Enchantment> THRILLING_THUNDER = reg("thrilling_thunder", ThrillingThunder::new);

  public static final RegistryObject<Enchantment> UNYIELDING_SPIRIT = reg("unyielding_spirit", UnyieldingSpirit::new);

  public static final RegistryObject<Enchantment> WIND_BLADE = reg("wind_blade", WindBlade::new);

  public static final RegistryObject<Enchantment> WIND_FIRE_WHEELS = reg("wind_fire_wheels", WindFireWheels::new);

  public static RegistryObject<Enchantment> reg(String name, Supplier<? extends Enchantment> sup) {
    return ENCHANTMENTS.register(name, sup);
  }

  public static void register(IEventBus eventBus) {
    ENCHANTMENTS.register(eventBus);
  }
}
