package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.AILMENT_DEVOURER;

@EventBusSubscriber(modid = MODID)
public final class AilmentDevourerHandler {
    @SubscribeEvent
    public static void onMobEffectAdded(MobEffectEvent.Added event) {
        MobEffectInstance instance = event.getEffectInstance();
        LivingEntity livingEntity = event.getEntity();
        if (instance == null || instance.isInfiniteDuration() || instance.getEffect().value().getCategory() != MobEffectCategory.HARMFUL) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(AILMENT_DEVOURER, livingEntity);
        if (level <= 0) {
            return;
        }
        instance.duration /= (level + 1);
    }
}
