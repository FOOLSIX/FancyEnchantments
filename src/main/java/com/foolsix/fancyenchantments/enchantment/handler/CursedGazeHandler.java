package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.CURSED_GAZE;

@EventBusSubscriber(modid = MODID)
public final class CursedGazeHandler {
    @SubscribeEvent
    public static void onPlayerTickPre(PlayerTickEvent.Pre event) {
        int level = EnchUtils.getEnchantmentLevel(CURSED_GAZE, event.getEntity());
        if (level <= 0) {
            return;
        }

        LivingEntity target = EnchUtils.getLookAtLivingEntity(event.getEntity(), 1.0F, Math.min(Config.CURSED_GAZE_BASE_DISTANCE.get() * level, 128.0D));
        if (target == null) {
            return;
        }

        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, level - 1));
        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, level - 1));
    }
}
