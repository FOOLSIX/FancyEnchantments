package com.foolsix.fancyenchantments.client;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.ElementalEssentia;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEEnchantments;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;
import java.util.Optional;

@EventBusSubscriber(modid = FancyEnchantments.MODID, value = Dist.CLIENT)
public final class ClientTooltipEvents {
    private ClientTooltipEvents() {
    }

    @SubscribeEvent
    public static void colorFancyEnchantments(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        colorEnchantments(event.getToolTip(), stack.getOrDefault(net.minecraft.core.component.DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY));
        colorEnchantments(event.getToolTip(), stack.getOrDefault(net.minecraft.core.component.DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY));
    }

    private static void colorEnchantments(List<Component> tooltip, ItemEnchantments enchantments) {
        for (var entry : enchantments.entrySet()) {
            Holder<Enchantment> enchantment = entry.getKey();
            ElementalEssentia element = FEEnchantments.elementOf(enchantment);
            if (element != null) {
                replaceEnchantmentLine(tooltip, enchantment, entry.getIntValue(), element);
            }
        }
    }

    private static void replaceEnchantmentLine(List<Component> tooltip, Holder<Enchantment> enchantment, int level, ElementalEssentia element) {
        Optional<String> translationKey = enchantment.unwrapKey()
                .map(key -> "enchantment." + key.location().getNamespace() + "." + key.location().getPath());
        if (translationKey.isEmpty()) {
            return;
        }

        String translatedName = Component.translatable(translationKey.get()).getString();
        for (int index = 0; index < tooltip.size(); index++) {
            Component line = tooltip.get(index);
            if (ChatFormatting.stripFormatting(line.getString()).startsWith(translatedName)) {
                tooltip.set(index, elementalEnchantmentName(translationKey.get(), level, element));
                return;
            }
        }
    }

    private static Component elementalEnchantmentName(String translationKey, int level, ElementalEssentia element) {
        Component name = EnchUtils.getElementStyledName(
                Component.translatable(translationKey),
                element
        );
        if (level > 0) {
            return name.copy().append(" ").append(Component.translatable("enchantment.level." + level).withStyle(element.chatFormatting()));
        }
        return name;
    }
}
