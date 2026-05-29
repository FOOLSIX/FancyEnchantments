package com.foolsix.fancyenchantments.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class FELootTableProvider extends net.minecraft.data.loot.LootTableProvider {
    public FELootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output,
                Set.of(),
                List.of(
                        new FELootTableProvider.SubProviderEntry(BlockLootTableProvider::new, LootContextParamSets.BLOCK)
                ),
                registries
        );
    }
}
