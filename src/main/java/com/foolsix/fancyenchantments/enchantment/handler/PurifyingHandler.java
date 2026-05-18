package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.PURIFYING;

@EventBusSubscriber(modid = MODID)
public final class PurifyingHandler {
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)
                || !(event.getEntity() instanceof ZombieVillager zombie)) {
            return;
        }

        if (EnchUtils.getEnchantmentLevel(PURIFYING, player) <= 0) {
            return;
        }

        EnchUtils.generateSimpleParticleAroundEntity(zombie, ParticleTypes.HAPPY_VILLAGER);
        zombie.setHealth(zombie.getMaxHealth());
        zombie.startConverting(player.getUUID(), 0);
    }
}
