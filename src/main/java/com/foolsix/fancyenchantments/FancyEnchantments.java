package com.foolsix.fancyenchantments;

import com.foolsix.fancyenchantments.attachment.AttachmentReg;
import com.foolsix.fancyenchantments.block.BlockReg;
import com.foolsix.fancyenchantments.effect.EffectReg;
import com.foolsix.fancyenchantments.enchantment.effect.EnchantmentEffectReg;
import com.foolsix.fancyenchantments.item.ModItemReg;
import com.foolsix.fancyenchantments.enchantment.util.ElementConditionManager;
import com.foolsix.fancyenchantments.loot.LootModifierReg;
import com.foolsix.fancyenchantments.resource.catalyst.CatalystResourceLoader;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.slf4j.Logger;

@Mod(FancyEnchantments.MODID)
public class FancyEnchantments {
    public static final String MODID = "fancyenchantments";
    public static final Logger LOGGER = LogUtils.getLogger();

    public FancyEnchantments(IEventBus modEventBus, ModContainer modContainer) {
        ModItemReg.register(modEventBus);
        BlockReg.register(modEventBus);
        EffectReg.register(modEventBus);
        EnchantmentEffectReg.register(modEventBus);
        AttachmentReg.register(modEventBus);
        LootModifierReg.register(modEventBus);

        NeoForge.EVENT_BUS.addListener(AddReloadListenerEvent.class, event -> event.addListener(
                new ElementConditionManager()
        ));
        NeoForge.EVENT_BUS.addListener(AddReloadListenerEvent.class, event -> event.addListener(
                new CatalystResourceLoader()
        ));
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
