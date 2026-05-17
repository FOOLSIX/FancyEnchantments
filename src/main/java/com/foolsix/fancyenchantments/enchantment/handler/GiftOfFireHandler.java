package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.GIFT_OF_FIRE;

@EventBusSubscriber(modid = MODID)
public final class GiftOfFireHandler {
    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        Entity attacker = event.getSource().getEntity();
        Entity victim = event.getEntity();
        if (!(attacker instanceof LivingEntity living)) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(GIFT_OF_FIRE, living);
        if (level <= 0) {
            return;
        }
        if (victim.isOnFire()) {
            EnchUtils.generateSimpleParticleAroundEntity(victim, ParticleTypes.LAVA);
            event.setAmount((float) (event.getAmount() + level * Config.GIFT_OF_FIRE_BENEFICIAL_MULTIPLIER.get()));
        }
    }
}
