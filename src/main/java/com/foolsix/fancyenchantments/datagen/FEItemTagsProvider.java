package com.foolsix.fancyenchantments.datagen;

import com.foolsix.fancyenchantments.tag.FETags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

public class FEItemTagsProvider extends net.minecraft.data.tags.ItemTagsProvider {
    public FEItemTagsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            CompletableFuture<TagLookup<Block>> blockTags,
            ExistingFileHelper existingFileHelper
    ) {
        super(output, lookupProvider, blockTags, MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(FETags.Items.UPGRADE_MATERIALS)
                .add(Items.DIAMOND)
                .add(Items.EMERALD);
    }
}
