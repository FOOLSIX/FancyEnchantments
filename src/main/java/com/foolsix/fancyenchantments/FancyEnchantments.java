package com.foolsix.fancyenchantments;

import com.foolsix.fancyenchantments.command.ElementStatCommand;
import com.foolsix.fancyenchantments.effect.EffectReg;
import com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg;
import com.foolsix.fancyenchantments.events.CapabilityEvents;
import com.foolsix.fancyenchantments.events.EffectEvents;
import com.foolsix.fancyenchantments.events.EnchantmentEvents;
import com.foolsix.fancyenchantments.util.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FancyEnchantments.MODID)
public class FancyEnchantments {
    public static final String MODID = "fancyenchantments";
    private static ModConfig config;

    public FancyEnchantments() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(new EnchantmentEvents());
        MinecraftForge.EVENT_BUS.register(new EffectEvents());
        MinecraftForge.EVENT_BUS.register(new CapabilityEvents());


        EnchantmentReg.ENCHANTMENTS.register(modEventBus);
        EffectReg.EFFECTS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ModConfig getConfig() {
        return config;
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent e) {
        ElementStatCommand.register(e.getDispatcher());
    }
}
