package com.foolsix.fancyenchantments.item;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

public final class ModItemReg {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> AER_CATALYST =
            ITEMS.register("aer_catalyst", () -> new CatalystItem(new Item.Properties()));
    public static final DeferredItem<Item> AQUA_CATALYST =
            ITEMS.register("aqua_catalyst", () -> new CatalystItem(new Item.Properties()));
    public static final DeferredItem<Item> IGNIS_CATALYST =
            ITEMS.register("ignis_catalyst", () -> new CatalystItem(new Item.Properties()));
    public static final DeferredItem<Item> TERRA_CATALYST =
            ITEMS.register("terra_catalyst", () -> new CatalystItem(new Item.Properties()));
    public static final DeferredItem<Item> HOLY_CATALYST =
            ITEMS.register("holy_catalyst", () -> new CatalystItem(new Item.Properties()));
    public static final DeferredItem<Item> TWISTED_CATALYST =
            ITEMS.register("twisted_catalyst", () -> new CatalystItem(new Item.Properties()));

    private ModItemReg() {
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    @EventBusSubscriber(modid = MODID)
    public static final class ModEvents {
        private ModEvents() {
        }

        @SubscribeEvent
        public static void buildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
                event.accept(AER_CATALYST);
                event.accept(AQUA_CATALYST);
                event.accept(IGNIS_CATALYST);
                event.accept(TERRA_CATALYST);
                event.accept(HOLY_CATALYST);
                event.accept(TWISTED_CATALYST);
            }
        }
    }
}
