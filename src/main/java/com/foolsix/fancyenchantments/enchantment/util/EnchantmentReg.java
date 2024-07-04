package com.foolsix.fancyenchantments.enchantment.util;

import com.foolsix.fancyenchantments.enchantment.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

public class EnchantmentReg {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MODID);

    public static final RegistryObject<Enchantment> LIGHTNESS =
            ENCHANTMENTS.register(Lightness.NAME, Lightness::new);

    public static final RegistryObject<Enchantment> COUNTERATTACK =
            ENCHANTMENTS.register(Counterattack.NAME, Counterattack::new);

    public static final RegistryObject<Enchantment> ADVANCED_SHARPNESS =
            ENCHANTMENTS.register(AdvancedSharpness.NAME, AdvancedSharpness::new);

    public static final RegistryObject<Enchantment> ADVANCED_LOOTING =
            ENCHANTMENTS.register(AdvancedLooting.NAME, AdvancedLooting::new);

    public static final RegistryObject<Enchantment> HUNGRY =
            ENCHANTMENTS.register(Hungry.NAME, Hungry::new);

    public static final RegistryObject<Enchantment> REFLECTING =
            ENCHANTMENTS.register(Reflecting.NAME, Reflecting::new);

    public static final RegistryObject<Enchantment> GIFT_OF_FIRE =
            ENCHANTMENTS.register(GiftOfFire.NAME, GiftOfFire::new);

    public static final RegistryObject<Enchantment> PYROMANIAC =
            ENCHANTMENTS.register(Pyromaniac.NAME, Pyromaniac::new);

    public static final RegistryObject<Enchantment> EATER_OF_SOULS =
            ENCHANTMENTS.register(EaterOfSouls.NAME, EaterOfSouls::new);

    public static final RegistryObject<Enchantment> SOLID_AS_A_ROCK =
            ENCHANTMENTS.register(SolidAsARock.NAME, SolidAsARock::new);

    public static final RegistryObject<Enchantment> OCEAN_CURRENT =
            ENCHANTMENTS.register(OceanCurrent.NAME, OceanCurrent::new);

    public static final RegistryObject<Enchantment> OVERFLOW =
            ENCHANTMENTS.register(Overflow.NAME, Overflow::new);

    public static final RegistryObject<Enchantment> FIRE_DISASTER =
            ENCHANTMENTS.register(FireDisaster.NAME, FireDisaster::new);

    public static final RegistryObject<Enchantment> THE_FALLEN =
            ENCHANTMENTS.register(TheFallen.NAME, TheFallen::new);

    public static final RegistryObject<Enchantment> EMPATHY =
            ENCHANTMENTS.register(Empathy.NAME, Empathy::new);

    public static final RegistryObject<Enchantment> ROLLING_STONE =
            ENCHANTMENTS.register(RollingStone.NAME, RollingStone::new);

    public static final RegistryObject<Enchantment> BLESSED_WIND =
            ENCHANTMENTS.register(BlessedWind.NAME, BlessedWind::new);

    public static final RegistryObject<Enchantment> HEAVY_BLOW =
            ENCHANTMENTS.register(HeavyBlow.NAME, HeavyBlow::new);

    public static final RegistryObject<Enchantment> WIND_BLADE =
            ENCHANTMENTS.register(WindBlade.NAME, WindBlade::new);

    public static final RegistryObject<Enchantment> FLOATING =
            ENCHANTMENTS.register(Floating.NAME, Floating::new);

    public static final RegistryObject<Enchantment> CUMBERSOME =
            ENCHANTMENTS.register(Cumbersome.NAME, Cumbersome::new);

    public static final RegistryObject<Enchantment> BULLYING =
            ENCHANTMENTS.register(Bullying.NAME, Bullying::new);

    public static final RegistryObject<Enchantment> DOMINION =
            ENCHANTMENTS.register(Dominion.NAME, Dominion::new);

    public static final RegistryObject<Enchantment> BLOODTHIRSTY =
            ENCHANTMENTS.register(Bloodthirsty.NAME, Bloodthirsty::new);

    public static final RegistryObject<Enchantment> FALLING_STONE =
            ENCHANTMENTS.register(FallingStone.NAME, FallingStone::new);

    public static final RegistryObject<Enchantment> FEATHER_FALL =
            ENCHANTMENTS.register(FeatherFall.NAME, FeatherFall::new);

    public static final RegistryObject<Enchantment> UNYIELDING_SPIRIT =
            ENCHANTMENTS.register(UnyieldingSpirit.NAME, UnyieldingSpirit::new);

    public static final RegistryObject<Enchantment> CURSED_GAZE =
            ENCHANTMENTS.register(CursedGaze.NAME, CursedGaze::new);

    public static final RegistryObject<Enchantment> CALMER =
            ENCHANTMENTS.register(Calmer.NAME, Calmer::new);

    public static final RegistryObject<Enchantment> PURIFYING =
            ENCHANTMENTS.register(Purifying.NAME, Purifying::new);

    public static final RegistryObject<Enchantment> FEINT_ATTACK =
            ENCHANTMENTS.register(FeintAttack.NAME, FeintAttack::new);

    public static final RegistryObject<Enchantment> DUELLIST =
            ENCHANTMENTS.register(DuellistsPrerogative.NAME, DuellistsPrerogative::new);

    public static final RegistryObject<Enchantment> PALADINS_SHIELD =
            ENCHANTMENTS.register(PaladinsShield.NAME, PaladinsShield::new);

    public static final RegistryObject<Enchantment> EUCHARIST =
            ENCHANTMENTS.register(Eucharist.NAME, Eucharist::new);

    public static final RegistryObject<Enchantment> MELTER =
            ENCHANTMENTS.register(Melter.NAME, Melter::new);

//    public static final RegistryObject<Enchantment> LAVA_BURST =
//            ENCHANTMENTS.register(LavaBurst.NAME, LavaBurst::new);


}
