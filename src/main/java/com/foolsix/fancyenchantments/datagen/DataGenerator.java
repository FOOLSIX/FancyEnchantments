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
        if (event.includeServer()) {
            event.createDatapackRegistryObjects(BUILDER);
            event.createProvider(EnchantmentTagsProvider::new);
        }
    }
}
