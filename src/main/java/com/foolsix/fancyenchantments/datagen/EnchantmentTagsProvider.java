package com.foolsix.fancyenchantments.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.EnchantmentTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element.IGNIS;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.ADVANCED_FIRE_ASPECT;

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
                .add(ADVANCED_FIRE_ASPECT);
        this.tag(IGNIS.tag())
                .add(ADVANCED_FIRE_ASPECT);
    }
}
