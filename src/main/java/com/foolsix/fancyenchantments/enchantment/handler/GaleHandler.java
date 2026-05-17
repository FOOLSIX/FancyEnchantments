package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.GALE;

@EventBusSubscriber(modid = MODID)
public final class GaleHandler {
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.isCanceled()) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack stack = player.getMainHandItem();
        int level = EnchUtils.getEnchantmentLevel(GALE, stack, player.registryAccess());
        if (level <= 0) {
            return;
        }

        int durationTicks = Config.GALE_DURATION_SECONDS.get() * 20;
        MobEffectInstance current = player.getEffect(MobEffects.DIG_SPEED);
        int amplifier = current == null ? 0 : Math.min(current.getAmplifier() + 1, level - 1);
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, durationTicks, amplifier));
    }
}
