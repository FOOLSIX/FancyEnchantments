package com.foolsix.fancyenchantments.block;

import com.foolsix.fancyenchantments.block.table.ElementalEnchantmentMenu;
import com.foolsix.fancyenchantments.block.table.ElementalEnchantmentTableBlock;
import com.foolsix.fancyenchantments.block.table.ElementalEnchantmentTableBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

public final class ModBlockReg {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);

    public static final DeferredBlock<Block> ELEMENTAL_ENCHANTING_TABLE =
            BLOCKS.register("elemental_enchanting_table", ElementalEnchantmentTableBlock::new);
    public static final DeferredItem<BlockItem> ELEMENTAL_ENCHANTING_TABLE_ITEM =
            ITEMS.register("elemental_enchanting_table",
                    () -> new BlockItem(ELEMENTAL_ENCHANTING_TABLE.get(), new Item.Properties()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ElementalEnchantmentTableBlockEntity>> ELEMENTAL_ENCHANTMENT_TABLE_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("elemental_enchantment_table",
                    () -> BlockEntityType.Builder.of(ElementalEnchantmentTableBlockEntity::new, ELEMENTAL_ENCHANTING_TABLE.get()).build(null));
    public static final DeferredHolder<MenuType<?>, MenuType<ElementalEnchantmentMenu>> ELEMENTAL_ENCHANTMENT_MENU =
            MENUS.register("elemental_enchantment_menu",
                    () -> IMenuTypeExtension.create(ElementalEnchantmentMenu::new));

    private ModBlockReg() {
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        MENUS.register(eventBus);
        BLOCK_ENTITY_TYPES.register(eventBus);
    }

    @EventBusSubscriber(modid = MODID)
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
