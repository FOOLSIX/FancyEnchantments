package com.foolsix.fancyenchantments.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.EnchantmentTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element.AER;
import static com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element.AQUA;
import static com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element.IGNIS;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.ABYSSAL_MAELSTROM;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.ADVANCED_FLAME;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.ADVANCED_FIRE_ASPECT;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.ADVANCED_LOOTING;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.ADVANCED_PROTECTION;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.ADVANCED_SHARPNESS;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.AFTERIMAGE;

public final class EnchantmentTagsProvider extends net.minecraft.data.tags.EnchantmentTagsProvider {
    public EnchantmentTagsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider
    ) {
        this(output, lookupProvider, null);
    }

    public EnchantmentTagsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            @Nullable net.neoforged.neoforge.common.data.ExistingFileHelper existingFileHelper
    ) {
        super(output, lookupProvider, MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(EnchantmentTags.IN_ENCHANTING_TABLE)
                .add(ABYSSAL_MAELSTROM)
                .add(ADVANCED_FIRE_ASPECT)
                .add(ADVANCED_FLAME)
                .add(ADVANCED_LOOTING)
                .add(ADVANCED_PROTECTION)
                .add(ADVANCED_SHARPNESS);
        this.tag(EnchantmentTags.TREASURE)
                .add(AFTERIMAGE);
        this.tag(EnchantmentTags.NON_TREASURE)
                .add(ABYSSAL_MAELSTROM)
                .add(ADVANCED_FIRE_ASPECT)
                .add(ADVANCED_FLAME)
                .add(ADVANCED_LOOTING)
                .add(ADVANCED_PROTECTION)
                .add(ADVANCED_SHARPNESS);
        this.tag(EnchantmentTags.TRADEABLE)
                .add(ABYSSAL_MAELSTROM)
                .add(ADVANCED_FIRE_ASPECT)
                .add(ADVANCED_FLAME)
                .add(ADVANCED_LOOTING)
                .add(ADVANCED_PROTECTION)
                .add(ADVANCED_SHARPNESS)
                .add(AFTERIMAGE);
        this.tag(EnchantmentTags.BOW_EXCLUSIVE)
                .add(ADVANCED_FLAME);
        this.tag(EnchantmentTags.ARMOR_EXCLUSIVE)
                .add(ADVANCED_PROTECTION);
        this.tag(EnchantmentTags.DAMAGE_EXCLUSIVE)
                .add(ADVANCED_SHARPNESS);
        this.tag(AER.tag())
                .add(AFTERIMAGE);
        this.tag(AQUA.tag())
                .add(ABYSSAL_MAELSTROM);
        this.tag(IGNIS.tag())
                .add(ADVANCED_FIRE_ASPECT)
                .add(ADVANCED_FLAME);
    }
}
