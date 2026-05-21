package com.foolsix.fancyenchantments;

import com.foolsix.fancyenchantments.block.ModBlockReg;
import com.foolsix.fancyenchantments.block.table.ElementalEnchantmentScreen;
import com.foolsix.fancyenchantments.block.table.ElementalEnchantmentTableRenderer;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEEnchantments;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = FancyEnchantments.MODID, value = Dist.CLIENT)
public final class ClientSetup {
    private ClientSetup() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            if (!Config.ENABLE_MOD_BOOK_TEXTURE.get()) {
                return;
            }

            ItemProperties.register(
                    Items.ENCHANTED_BOOK,
                    ResourceLocation.fromNamespaceAndPath(FancyEnchantments.MODID, "enchanted_book"),
                    (stack, level, entity, seed) -> {
                        var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(stack);
                        if (enchantments.isEmpty()) {
                            return 0.0F;
                        }

                        var enchantment = enchantments.entrySet().iterator().next().getKey();
                        var element = EnchUtils.elementOf(enchantment);
                        if (element != null) {
                            return 1.0F + element.ordinal() * 0.1F;
                        }

                        return FEEnchantments.isFancyEnchantment(enchantment) ? 2.0F : 0.0F;
                    }
            );
        });
    }

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModBlockReg.ELEMENTAL_ENCHANTMENT_MENU.get(), ElementalEnchantmentScreen::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockReg.ELEMENTAL_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), ElementalEnchantmentTableRenderer::new);
    }
}
