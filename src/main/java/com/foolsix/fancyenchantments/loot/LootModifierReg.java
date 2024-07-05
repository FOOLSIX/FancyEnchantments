package com.foolsix.fancyenchantments.loot;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

public class LootModifierReg {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MODID);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> SPECIAL_LOOT_MODIFIER_REGISTRY =
            LOOT_MODIFIER_SERIALIZERS.register("special_loot_modifier", SpecialLootModifier.CODEC);
}
