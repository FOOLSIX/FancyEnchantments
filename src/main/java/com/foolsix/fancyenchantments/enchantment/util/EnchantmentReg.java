package com.foolsix.fancyenchantments.enchantment.util;

import com.foolsix.fancyenchantments.enchantment.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

public class EnchantmentReg {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MODID);

    public static final RegistryObject<Enchantment> LIGHTNESS = reg(Lightness.NAME, Lightness::new);
    public static final RegistryObject<Enchantment> COUNTERATTACK = reg(Counterattack.NAME, Counterattack::new);
    public static final RegistryObject<Enchantment> ADVANCED_SHARPNESS = reg(AdvancedSharpness.NAME, AdvancedSharpness::new);
    public static final RegistryObject<Enchantment> ADVANCED_LOOTING = reg(AdvancedLooting.NAME, AdvancedLooting::new);
    public static final RegistryObject<Enchantment> HUNGRY = reg(Hungry.NAME, Hungry::new);
    public static final RegistryObject<Enchantment> REFLECTING = reg(Reflecting.NAME, Reflecting::new);
    public static final RegistryObject<Enchantment> GIFT_OF_FIRE = reg(GiftOfFire.NAME, GiftOfFire::new);
    public static final RegistryObject<Enchantment> PYROMANIAC = reg(Pyromaniac.NAME, Pyromaniac::new);
    public static final RegistryObject<Enchantment> EATER_OF_SOULS = reg(EaterOfSouls.NAME, EaterOfSouls::new);
    public static final RegistryObject<Enchantment> SOLID_AS_A_ROCK = reg(SolidAsARock.NAME, SolidAsARock::new);
    public static final RegistryObject<Enchantment> OCEAN_CURRENT = reg(OceanCurrent.NAME, OceanCurrent::new);
    public static final RegistryObject<Enchantment> OVERFLOW = reg(Overflow.NAME, Overflow::new);
    public static final RegistryObject<Enchantment> FIRE_DISASTER = reg(FireDisaster.NAME, FireDisaster::new);
    public static final RegistryObject<Enchantment> THE_FALLEN = reg(TheFallen.NAME, TheFallen::new);
    public static final RegistryObject<Enchantment> EMPATHY = reg(Empathy.NAME, Empathy::new);
    public static final RegistryObject<Enchantment> ROLLING_STONE = reg(RollingStone.NAME, RollingStone::new);
    public static final RegistryObject<Enchantment> BLESSED_WIND = reg(BlessedWind.NAME, BlessedWind::new);
    public static final RegistryObject<Enchantment> HEAVY_BLOW = reg(HeavyBlow.NAME, HeavyBlow::new);
    public static final RegistryObject<Enchantment> WIND_BLADE = reg(WindBlade.NAME, WindBlade::new);
    public static final RegistryObject<Enchantment> FLOATING = reg(Floating.NAME, Floating::new);
    public static final RegistryObject<Enchantment> CUMBERSOME = reg(Cumbersome.NAME, Cumbersome::new);
    public static final RegistryObject<Enchantment> BULLYING = reg(Bullying.NAME, Bullying::new);
    public static final RegistryObject<Enchantment> DOMINION = reg(Dominion.NAME, Dominion::new);
    public static final RegistryObject<Enchantment> BLOODTHIRSTY = reg(Bloodthirsty.NAME, Bloodthirsty::new);
    public static final RegistryObject<Enchantment> FALLING_STONE = reg(FallingStone.NAME, FallingStone::new);
    public static final RegistryObject<Enchantment> FEATHER_FALL = reg(FeatherFall.NAME, FeatherFall::new);
    public static final RegistryObject<Enchantment> UNYIELDING_SPIRIT = reg(UnyieldingSpirit.NAME, UnyieldingSpirit::new);
    public static final RegistryObject<Enchantment> CURSED_GAZE = reg(CursedGaze.NAME, CursedGaze::new);
    public static final RegistryObject<Enchantment> CALMER = reg(Calmer.NAME, Calmer::new);
    public static final RegistryObject<Enchantment> PURIFYING = reg(Purifying.NAME, Purifying::new);
    public static final RegistryObject<Enchantment> FEINT_ATTACK = reg(FeintAttack.NAME, FeintAttack::new);
    public static final RegistryObject<Enchantment> DUELLIST = reg(DuellistsPrerogative.NAME, DuellistsPrerogative::new);
    public static final RegistryObject<Enchantment> PALADINS_SHIELD = reg(PaladinsShield.NAME, PaladinsShield::new);
    public static final RegistryObject<Enchantment> EUCHARIST = reg(Eucharist.NAME, Eucharist::new);
    public static final RegistryObject<Enchantment> MELTER = reg(Melter.NAME, Melter::new);
    public static final RegistryObject<Enchantment> STACKING_WAVES = reg(StackingWaves.NAME, StackingWaves::new);
    public static final RegistryObject<Enchantment> CHARGE = reg(Charge.NAME, Charge::new);
    public static final RegistryObject<Enchantment> LAVA_BURST = reg(LavaBurst.NAME, LavaBurst::new);
    public static final RegistryObject<Enchantment> ARMOR_FORGING = reg(ArmorForging.NAME, ArmorForging::new);
    public static final RegistryObject<Enchantment> NIRVANA = reg(Nirvana.NAME, Nirvana::new);
    public static final RegistryObject<Enchantment> SELF_IMMOLATION = reg(SelfImmolation.NAME, SelfImmolation::new);
    public static final RegistryObject<Enchantment> BUBBLE_SHIELD = reg(BubbleShield.NAME, BubbleShield::new);
    public static final RegistryObject<Enchantment> DROWNING = reg(Drowning.NAME, Drowning::new);
    public static final RegistryObject<Enchantment> PERVERT = reg(Pervert.NAME, Pervert::new);
    public static final RegistryObject<Enchantment> PURE_FATE = reg(PureFate.NAME, PureFate::new);
    public static final RegistryObject<Enchantment> BLOOD_SACRIFICE = reg(BloodSacrifice.NAME, BloodSacrifice::new);
    public static final RegistryObject<Enchantment> SHARP_ROCK = reg(SharpRock.NAME, SharpRock::new);
    public static final RegistryObject<Enchantment> DELAYED_EXECUTION = reg(DelayedExecution.NAME, DelayedExecution::new);
    public static final RegistryObject<Enchantment> SACRED_SUPREME_SHARPNESS = reg(SacredSupremeSharpness.NAME, SacredSupremeSharpness::new);
    public static final RegistryObject<Enchantment> GREEDY_SUPREME_LOOTING = reg(GreedySupremeLooting.NAME, GreedySupremeLooting::new);
    public static final RegistryObject<Enchantment> WIND_FIRE_WHEELS = reg(WindFireWheels.NAME, WindFireWheels::new);
    public static final RegistryObject<Enchantment> SPREADING_SPORES = reg(SpreadingSpores.NAME, SpreadingSpores::new);
    public static final RegistryObject<Enchantment> THRILLING_THUNDER = reg(ThrillingThunder.NAME, ThrillingThunder::new);
    public static final RegistryObject<Enchantment> RECOIL = reg(Recoil.NAME, Recoil::new);
    public static final RegistryObject<Enchantment> AIR_ATTACK = reg(AirAttack.NAME, AirAttack::new);
    public static final RegistryObject<Enchantment> ERODING = reg(Eroding.NAME, Eroding::new);
    public static final RegistryObject<Enchantment> SANDER = reg(Sander.NAME, Sander::new);
    public static final RegistryObject<Enchantment> ADVANCED_PROTECTION = reg(AdvancedProtection.NAME, AdvancedProtection::new);
    public static final RegistryObject<Enchantment> MOUNTAIN_SUPREME_PROTECTION = reg(MountainSupremeProtection.NAME, MountainSupremeProtection::new);
    public static final RegistryObject<Enchantment> AFTERIMAGE = reg(Afterimage.NAME, Afterimage::new);
    public static final RegistryObject<Enchantment> STANDING_WALL = reg(StandingWall.NAME, StandingWall::new);
    public static final RegistryObject<Enchantment> CONDITION_OVERLOAD = reg(ConditionOverload.NAME, ConditionOverload::new);
    public static final RegistryObject<Enchantment> FEARLESS_CHALLENGER = reg(FearlessChallenger.NAME, FearlessChallenger::new);
    public static final RegistryObject<Enchantment> STREAMLINE = reg(Streamline.NAME, Streamline::new);
    public static final RegistryObject<Enchantment> HEAVY_ARROW = reg(HeavyArrow.NAME, HeavyArrow::new);
    public static final RegistryObject<Enchantment> DOWNWIND = reg(Downwind.NAME, Downwind::new);
    public static final RegistryObject<Enchantment> NIGHTMARE = reg(Nightmare.NAME, Nightmare::new);
    public static final RegistryObject<Enchantment> DEXTERITY = reg(Dexterity.NAME, Dexterity::new);
    public static final RegistryObject<Enchantment> CRACKED_CROWN = reg(CrackedCrown.NAME, CrackedCrown::new);

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }

    public static RegistryObject<Enchantment> reg(String name, Supplier<? extends Enchantment> sup) {
//        String lang = String.format("\"enchantment.fancyenchantments.%s\": \"%s\",", name, EnchUtils.getLangName(name));
//        String desc = String.format("\"enchantment.fancyenchantments.%s.desc\": \"\",", name);
//        System.out.println(lang);
//        System.out.println(desc);
        return ENCHANTMENTS.register(name, sup);
    }
}
