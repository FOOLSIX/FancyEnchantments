package com.foolsix.fancyenchantments.block;

import com.foolsix.fancyenchantments.menu.ElementalEnchantmentMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

public final class ModBlockReg {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

    public static final RegistryObject<Block> ELEMENTAL_ENCHANTING_TABLE =
            BLOCKS.register("elemental_enchanting_table", ElementalEnchantmentTableBlock::new);
    public static final RegistryObject<Item> ELEMENTAL_ENCHANTING_TABLE_ITEM =
            ITEMS.register("elemental_enchanting_table",
                    () -> new BlockItem(ELEMENTAL_ENCHANTING_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<MenuType<ElementalEnchantmentMenu>> ELEMENTAL_ENCHANTMENT_MENU =
            MENUS.register("elemental_enchantment_menu",
                    () -> net.minecraftforge.common.extensions.IForgeMenuType.create(
                            (IContainerFactory<ElementalEnchantmentMenu>) (windowId, inventory, data) ->
                                    new ElementalEnchantmentMenu(windowId, inventory, data.readBlockPos())));

    private ModBlockReg() {
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        MENUS.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ModEvents {
        private ModEvents() {
        }

        @SubscribeEvent
        public static void buildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
                event.accept(ELEMENTAL_ENCHANTING_TABLE_ITEM);
            }
        }
    }
}
