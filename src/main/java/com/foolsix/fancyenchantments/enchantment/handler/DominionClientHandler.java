package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.DOMINION;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public final class DominionClientHandler {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        var registries = event.getContext().registries();
        if (registries == null) {
            return;
        }

        var enchantments = registries.lookupOrThrow(Registries.ENCHANTMENT);
        var dominion = enchantments.getOrThrow(DOMINION);
        int level = EnchUtils.enchantmentsOn(event.getItemStack()).getLevel(dominion);
        if (level <= 0) {
            level = EnchUtils.storedEnchantmentsOn(event.getItemStack()).getLevel(dominion);
        }
        if (level <= 0) {
            return;
        }

        Component original = Enchantment.getFullname(dominion, level);
        int color = Minecraft.getInstance().level != null
                ? Mth.hsvToRgb((Minecraft.getInstance().level.getGameTime() % 360L) / 360.0F, 1.0F, 1.0F)
                : 0;
        MutableComponent replacement = Component.translatable(Util.makeDescriptionId("enchantment", DOMINION.location())).setStyle(Style.EMPTY.withColor(color));
        if (level != 1) {
            replacement.append(CommonComponents.SPACE).append(Component.translatable("enchantment.level." + level));
        }

        for (int index = 0; index < event.getToolTip().size(); ++index) {
            if (event.getToolTip().get(index).getString().equals(original.getString())) {
                event.getToolTip().set(index, replacement);
                return;
            }
        }
    }
}
