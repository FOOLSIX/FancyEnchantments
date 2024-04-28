package com.foolsix.fancyenchantments.enchantment.util;

import com.foolsix.fancyenchantments.enchantment.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchIDs.*;

public class EnchantmentReg {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MODID);

    public static final RegistryObject<Enchantment> LIGHTNESS =
            ENCHANTMENTS.register(LIGHTNESS_NAME, Lightness::new);
    public static final RegistryObject<Enchantment> COUNTERATTACK =
            ENCHANTMENTS.register(COUNTERATTACK_NAME, Counterattack::new);

    public static final RegistryObject<Enchantment> ADVANCED_SHARPNESS =
            ENCHANTMENTS.register(ADVANCED_SHARPNESS_NAME, AdvancedSharpness::new);
    public static final RegistryObject<Enchantment> ADVANCED_LOOTING =
            ENCHANTMENTS.register(ADVANCED_LOOTING_NAME, AdvancedLooting::new);

    public static final RegistryObject<Enchantment> REFLECTING =
            ENCHANTMENTS.register(REFLECTING_NAME, Reflecting::new);

    public static final RegistryObject<Enchantment> GIFT_OF_FIRE =
            ENCHANTMENTS.register(GIFT_OF_FIRE_NAME, GiftOfFire::new);

    public static final RegistryObject<Enchantment> PYROMANIAC =
            ENCHANTMENTS.register(PYROMANIAC_NAME, Pyromaniac::new);
    public static final RegistryObject<Enchantment> EATER_OF_SOULS =
            ENCHANTMENTS.register(EATER_OF_SOULS_NAME, EaterOfSouls::new);

    public static final RegistryObject<Enchantment> SOLID_AS_A_ROCK =
            ENCHANTMENTS.register(SOLID_AS_A_ROCK_NAME, SolidAsARock::new);

    public static final RegistryObject<Enchantment> OCEAN_CURRENT =
            ENCHANTMENTS.register(OCEAN_CURRENT_NAME, OceanCurrent::new);

    public static final RegistryObject<Enchantment> OVERFLOW =
            ENCHANTMENTS.register(OVERFLOW_NAME, Overflow::new);

    public static final RegistryObject<Enchantment> FIRE_DISASTER =
            ENCHANTMENTS.register(FIRE_DISASTER_NAME, FireDisaster::new);

    public static final RegistryObject<Enchantment> THE_FALLEN =
            ENCHANTMENTS.register(THE_FALLEN_NAME, TheFallen::new);

    public static final RegistryObject<Enchantment> EMPATHY =
            ENCHANTMENTS.register(EMPATHY_NAME, Empathy::new);

    public static final RegistryObject<Enchantment> ROLLING_STONE =
            ENCHANTMENTS.register(ROLLING_STONE_NAME, RollingStone::new);

    public static final RegistryObject<Enchantment> BLESSED_WIND =
            ENCHANTMENTS.register(BLESSED_WIND_NAME, BlessedWind::new);

    public static final RegistryObject<Enchantment> HEAVY_BLOW =
            ENCHANTMENTS.register(HEAVY_BLOW_NAME, HeavyBlow::new);

    public static final RegistryObject<Enchantment> WIND_BLADE =
            ENCHANTMENTS.register(WIND_BLADE_NAME, WindBlade::new);
}
