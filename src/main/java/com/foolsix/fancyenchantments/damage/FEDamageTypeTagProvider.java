package com.foolsix.fancyenchantments.damage;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;

import java.util.concurrent.CompletableFuture;

public class FEDamageTypeTagProvider extends TagsProvider<DamageType> {

    public FEDamageTypeTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, Registries.DAMAGE_TYPE, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(FEDamageSource.GIVE_UP);

        this.tag(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(FEDamageSource.GIVE_UP);

        this.tag(DamageTypeTags.BYPASSES_EFFECTS).add(FEDamageSource.GIVE_UP);

        this.tag(DamageTypeTags.BYPASSES_RESISTANCE).add(FEDamageSource.GIVE_UP);
    }
}
