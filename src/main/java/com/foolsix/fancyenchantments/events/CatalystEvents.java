package com.foolsix.fancyenchantments.events;

import com.foolsix.fancyenchantments.resource.catalyst.CatalystResourceLoader;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = MODID)
public class CatalystEvents {
    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent e) {
        e.addListener(CatalystResourceLoader.INSTANCE);
    }
}
