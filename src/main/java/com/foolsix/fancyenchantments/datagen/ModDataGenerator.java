package com.foolsix.fancyenchantments.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        var lookup = event.getLookupProvider();
        var fileHelper = event.getExistingFileHelper();

        boolean client = event.includeClient();
        boolean server = event.includeServer();

        if (server) {
            gen.addProvider(true, new ModBlockTagProvider(gen.getPackOutput(), lookup, fileHelper));
        }
    }
}