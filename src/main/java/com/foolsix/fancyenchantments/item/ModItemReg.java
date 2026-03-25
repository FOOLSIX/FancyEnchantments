package com.foolsix.fancyenchantments.item;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

public final class ModItemReg {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> IGNIS_CATALYST =
            ITEMS.register("ignis_catalyst", () -> new IgnisCatalystItem(new Item.Properties()));

    private ModItemReg() {
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ModEvents {
        private ModEvents() {
        }

        @SubscribeEvent
        public static void buildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
                event.accept(IGNIS_CATALYST);
            }
        }
    }
}
