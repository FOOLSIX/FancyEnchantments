package com.foolsix.fancyenchantments.datagen;

import com.foolsix.fancyenchantments.damage.FEDamageTypes;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, FEDamageTypes::bootstrap);

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        var lookup = event.getLookupProvider();
        var fileHelper = event.getExistingFileHelper();

        boolean client = event.includeClient();
        boolean server = event.includeServer();

        if (server) {
            gen.addProvider(event.includeServer(), new ModBlockTagProvider(gen.getPackOutput(), lookup, fileHelper));
            gen.addProvider(event.includeServer(), new FEDamageTypeTagProvider(gen.getPackOutput(), lookup, fileHelper));
        }
    }
}
