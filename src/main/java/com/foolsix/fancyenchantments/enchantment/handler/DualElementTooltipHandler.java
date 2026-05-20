package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public final class DualElementTooltipHandler {
    private DualElementTooltipHandler() {
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getContext().registries() == null) {
            return;
        }

        ItemEnchantments directEnchantments = event.getItemStack().getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        ItemEnchantments storedEnchantments = event.getItemStack().getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);
        if (directEnchantments.isEmpty() && storedEnchantments.isEmpty()) {
            return;
        }

        long gameTime = Minecraft.getInstance().level != null ? Minecraft.getInstance().level.getGameTime() : 0L;
        replaceDualElementLines(event, directEnchantments, gameTime);
        replaceDualElementLines(event, storedEnchantments, gameTime);
    }

    private static void replaceDualElementLines(ItemTooltipEvent event, ItemEnchantments enchantments, long gameTime) {
        for (var entry : enchantments.entrySet()) {
            Holder<Enchantment> enchantment = entry.getKey();
            Element first = null;
            Element second = null;
            for (Element element : Element.values()) {
                if (!enchantment.is(element.tag())) {
                    continue;
                }
                if (first == null) {
                    first = element;
                } else {
                    second = element;
                    break;
                }
            }
            if (first == null || second == null) {
                continue;
            }

            Component original = Enchantment.getFullname(enchantment, entry.getIntValue());
            String originalText = original.getString();
            for (int index = 0; index < event.getToolTip().size(); ++index) {
                if (event.getToolTip().get(index).getString().equals(originalText)) {
                    event.getToolTip().set(index, EnchUtils.getMixedColorFullName(original, first, second, gameTime));
                    break;
                }
            }
        }
    }
}
