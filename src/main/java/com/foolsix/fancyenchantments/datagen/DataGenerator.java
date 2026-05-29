package com.foolsix.fancyenchantments.datagen;

import com.foolsix.fancyenchantments.FancyEnchantments;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = FancyEnchantments.MODID)
public final class DataGenerator {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, EnchantmentGenerator::bootstrap);

    private DataGenerator() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        var existingFileHelper = event.getExistingFileHelper();
        var blockTagsProvider = new BlockTagsProvider(packOutput, lookupProvider, existingFileHelper);

        if (event.includeServer()) {
            generator.addProvider(true, blockTagsProvider);
            event.createDatapackRegistryObjects(BUILDER);
            event.createProvider(EnchantmentTagsProvider::new);
            event.createProvider(ElementConditionProvider::new);
            event.createProvider(FERecipeProvider::new);
            event.createProvider(LootModifierProvider::new);
            generator.addProvider(true, new FEItemTagsProvider(packOutput, lookupProvider,blockTagsProvider.contentsGetter(), existingFileHelper));
            generator.addProvider(true, new FELootTableProvider(packOutput, lookupProvider));
        }
    }
}
