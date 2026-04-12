package com.foolsix.fancyenchantments.datagen;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.block.ModBlockReg;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {

    public ModBlockTagProvider(PackOutput output,
                               CompletableFuture<HolderLookup.Provider> lookupProvider,
                               ExistingFileHelper helper) {
        super(output, lookupProvider, FancyEnchantments.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlockReg.ELEMENTAL_ENCHANTING_TABLE.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlockReg.ELEMENTAL_ENCHANTING_TABLE.get());
    }
}