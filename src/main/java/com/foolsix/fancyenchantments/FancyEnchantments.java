package com.foolsix.fancyenchantments;

import com.foolsix.fancyenchantments.command.ElementStatCommand;
import com.foolsix.fancyenchantments.effect.EffectReg;
import com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg;
import com.foolsix.fancyenchantments.events.CapabilityEvents;
import com.foolsix.fancyenchantments.events.EffectEvents;
import com.foolsix.fancyenchantments.events.EnchantmentEvents;
import com.foolsix.fancyenchantments.loot.LootModifierReg;
import com.foolsix.fancyenchantments.util.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(FancyEnchantments.MODID)
public class FancyEnchantments {
    public static final String MODID = "fancyenchantments";
    private static final ModConfig config;

    static {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }

    public FancyEnchantments() {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        EnchantmentReg.register(modEventBus);
        EffectReg.EFFECTS.register(modEventBus);
        LootModifierReg.LOOT_MODIFIER_SERIALIZERS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(new EnchantmentEvents());
        MinecraftForge.EVENT_BUS.register(new EffectEvents());
        MinecraftForge.EVENT_BUS.register(new CapabilityEvents());

        if (FMLEnvironment.dist.isClient() && FancyEnchantments.getConfig().enableModBookTexture) {
            modEventBus.addListener(ClientSetup::clientSetup);
        }

    }

    public static ModConfig getConfig() {
        return config;
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent e) {
        ElementStatCommand.register(e.getDispatcher());
    }


}
