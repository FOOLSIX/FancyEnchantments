package com.foolsix.fancyenchantments.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.EnchantmentTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element.*;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.*;

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
                .add(ADVANCED_SHARPNESS)
                .add(AILMENT_DEVOURER)
                .add(AIR_ATTACK)
                .add(BLESSED_WIND)
                .add(BLIND_LOYALTY)
                .add(BUBBLE_SHIELD)
                .add(CALMER)
                .add(CONDITION_OVERLOAD)
                .add(COUNTERATTACK)
                .add(CURSED_GAZE)
                .add(DEDICATION)
                .add(DELAYED_EXECUTION);
        this.tag(EnchantmentTags.TREASURE)
                .add(AFTERIMAGE)
                .add(ARMOR_FORGING)
                .add(BEYOND_THE_FLASH)
                .add(BLOOD_FEED)
                .add(BLOOD_SACRIFICE)
                .add(BLOODTHIRSTY)
                .add(BULLYING)
                .add(CHARGE)
                .add(CRACKED_CROWN)
                .add(CUMBERSOME);
        this.tag(EnchantmentTags.NON_TREASURE)
                .add(ABYSSAL_MAELSTROM)
                .add(ADVANCED_FIRE_ASPECT)
                .add(ADVANCED_FLAME)
                .add(ADVANCED_LOOTING)
                .add(ADVANCED_PROTECTION)
                .add(ADVANCED_SHARPNESS)
                .add(AILMENT_DEVOURER)
                .add(AIR_ATTACK)
                .add(BLESSED_WIND)
                .add(BLIND_LOYALTY)
                .add(BUBBLE_SHIELD)
                .add(CALMER)
                .add(CONDITION_OVERLOAD)
                .add(COUNTERATTACK)
                .add(CURSED_GAZE)
                .add(DEDICATION)
                .add(DELAYED_EXECUTION);
        this.tag(EnchantmentTags.TRADEABLE)
                .add(ABYSSAL_MAELSTROM)
                .add(ADVANCED_FIRE_ASPECT)
                .add(ADVANCED_FLAME)
                .add(ADVANCED_LOOTING)
                .add(ADVANCED_PROTECTION)
                .add(ADVANCED_SHARPNESS)
                .add(AFTERIMAGE)
                .add(AILMENT_DEVOURER)
                .add(AIR_ATTACK)
                .add(BEYOND_THE_FLASH)
                .add(BLESSED_WIND)
                .add(BLIND_LOYALTY)
                .add(BLOODTHIRSTY)
                .add(BUBBLE_SHIELD)
                .add(BULLYING)
                .add(CALMER)
                .add(CHARGE)
                .add(CONDITION_OVERLOAD)
                .add(COUNTERATTACK)
                .add(CRACKED_CROWN)
                .add(CUMBERSOME)
                .add(CURSED_GAZE)
                .add(DEDICATION)
                .add(DELAYED_EXECUTION);
        this.tag(EnchantmentTags.CURSE)
                .add(CRACKED_CROWN)
                .add(CUMBERSOME);
        this.tag(EnchantmentTags.BOW_EXCLUSIVE)
                .add(ADVANCED_FLAME);
        this.tag(EnchantmentTags.ARMOR_EXCLUSIVE)
                .add(ADVANCED_PROTECTION);
        this.tag(EnchantmentTags.DAMAGE_EXCLUSIVE)
                .add(ADVANCED_SHARPNESS);
        this.tag(AER.tag())
                .add(AFTERIMAGE)
                .add(AIR_ATTACK)
                .add(BLESSED_WIND);
        this.tag(AQUA.tag())
                .add(ABYSSAL_MAELSTROM)
                .add(BUBBLE_SHIELD)
                .add(CALMER);
        this.tag(HOLY.tag())
                .add(AILMENT_DEVOURER)
                .add(CRACKED_CROWN)
                .add(DEDICATION);
        this.tag(IGNIS.tag())
                .add(ADVANCED_FIRE_ASPECT)
                .add(ADVANCED_FLAME);
        this.tag(TERRA.tag())
                .add(CUMBERSOME);
        this.tag(TWISTED.tag())
                .add(BLOOD_SACRIFICE)
                .add(BLOODTHIRSTY)
                .add(BULLYING)
                .add(CURSED_GAZE);
    }
}
