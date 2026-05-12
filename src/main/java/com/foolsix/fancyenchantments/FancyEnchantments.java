package com.foolsix.fancyenchantments;

import com.foolsix.fancyenchantments.block.ModBlockReg;
import com.foolsix.fancyenchantments.effect.EffectReg;
import com.foolsix.fancyenchantments.item.ModItemReg;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(FancyEnchantments.MODID)
public class FancyEnchantments {
    public static final String MODID = "fancyenchantments";
    public static final Logger LOGGER = LogUtils.getLogger();

    public FancyEnchantments(IEventBus modEventBus, ModContainer modContainer) {
        ModItemReg.register(modEventBus);
        ModBlockReg.register(modEventBus);
        EffectReg.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
