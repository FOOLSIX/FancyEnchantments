package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.attachment.TimeToLiveHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public final class UnyieldingSpiritClientHandler {
    @SubscribeEvent
    public static void onRenderGuiLayerPost(RenderGuiLayerEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.options.hideGui || !VanillaGuiLayers.CROSSHAIR.equals(event.getName())) {
            return;
        }

        int ttl = TimeToLiveHelper.getTtl(minecraft.player);
        if (ttl < 0) {
            return;
        }

        Font font = minecraft.font;
        Component text = Component.translatable("enchantment.fancyenchantments.unyielding_spirit.hud")
                .append(" ")
                .append(String.valueOf(ttl / 20));
        int x = (event.getGuiGraphics().guiWidth() - font.width(text)) / 2;
        int y = event.getGuiGraphics().guiHeight() / 2 + 10;
        event.getGuiGraphics().drawString(font, text, x, y, 0xFFFFFF);
    }
}
