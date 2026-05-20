package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.effect.EffectReg;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.THRILLING_THUNDER;

@EventBusSubscriber(modid = MODID)
public final class ThrillingThunderHandler {
    @SubscribeEvent
    public static void onLivingDamagePost(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof Player player) || !(event.getEntity() instanceof LivingEntity living)) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(THRILLING_THUNDER, player);
        if (level <= 0 || living.getRandom().nextDouble() >= Config.THRILLING_THUNDER_PROBABILITY_PER_LEVEL.get() * level) {
            return;
        }

        living.addEffect(new MobEffectInstance(EffectReg.TREMBLING, level * 20, level - 1));
    }
}
