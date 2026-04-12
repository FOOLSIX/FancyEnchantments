package com.foolsix.fancyenchantments.datagen;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.damage.FEDamageSource;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class FEDamageTypeTagProvider extends TagsProvider<DamageType> {

    public FEDamageTypeTagProvider(PackOutput packOutput,
                                   CompletableFuture<HolderLookup.Provider> lookupProvider,
                                   ExistingFileHelper existingFileHelper) {
        super(packOutput, Registries.DAMAGE_TYPE, lookupProvider, FancyEnchantments.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(FEDamageSource.GIVE_UP);

        this.tag(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(FEDamageSource.GIVE_UP);

        this.tag(DamageTypeTags.BYPASSES_EFFECTS).add(FEDamageSource.GIVE_UP);

        this.tag(DamageTypeTags.BYPASSES_RESISTANCE).add(FEDamageSource.GIVE_UP);
    }
}
