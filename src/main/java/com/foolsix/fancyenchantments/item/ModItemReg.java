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

    public static final RegistryObject<Item> AER_CATALYST =
            ITEMS.register("aer_catalyst", () -> new CatalystItem(new Item.Properties()));
    public static final RegistryObject<Item> AQUA_CATALYST =
            ITEMS.register("aqua_catalyst", () -> new CatalystItem(new Item.Properties()));
    public static final RegistryObject<Item> IGNIS_CATALYST =
            ITEMS.register("ignis_catalyst", () -> new CatalystItem(new Item.Properties()));
    public static final RegistryObject<Item> TERRA_CATALYST =
            ITEMS.register("terra_catalyst", () -> new CatalystItem(new Item.Properties()));
    public static final RegistryObject<Item> HOLY_CATALYST =
            ITEMS.register("holy_catalyst", () -> new CatalystItem(new Item.Properties()));
    public static final RegistryObject<Item> TWISTED_CATALYST =
            ITEMS.register("twisted_catalyst", () -> new CatalystItem(new Item.Properties()));

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
