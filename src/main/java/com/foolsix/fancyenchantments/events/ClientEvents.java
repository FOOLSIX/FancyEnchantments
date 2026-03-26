package com.foolsix.fancyenchantments.events;

import com.foolsix.fancyenchantments.capability.TimeToLiveCapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = MODID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void displayTTL(RenderGuiOverlayEvent.Post e) {
        if (e.getOverlay() != VanillaGuiOverlay.CROSSHAIR.type()) return;
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;
        GuiGraphics gui = e.getGuiGraphics();
        player.getCapability(TimeToLiveCapabilityProvider.PLAYER_TTL).ifPresent(timeToLiveCapability -> {
            int ttl = timeToLiveCapability.getTtl();
            if (ttl <= 0) return;
            String text = String.format("%s:%.2f", I18n.get("enchantment.fancyenchantments.unyielding_spirit.hud"), (float) ttl / 20);
            int centerX = mc.getWindow().getGuiScaledWidth() / 2;
            int centerY = mc.getWindow().getGuiScaledHeight() / 2;
            gui.drawString(
                    mc.font,
                    text,
                    centerX - mc.font.width(text) / 2,
                    centerY + 20,
                    0xFF0000
            );
        });
    }
}
