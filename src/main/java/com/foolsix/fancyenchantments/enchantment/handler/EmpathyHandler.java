package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.EMPATHY;

@EventBusSubscriber(modid = MODID)
public final class EmpathyHandler {
    @SubscribeEvent
    public static void onArrowLoose(ArrowLooseEvent event) {
        Player player = event.getEntity();
        int level = EnchUtils.getEnchantmentLevel(EMPATHY, event.getBow(), player.registryAccess());
        if (level <= 0) {
            return;
        }

        double force = event.getCharge() * Config.EMPATHY_SHOOT_POWER_MULTIPLIER.get();
        Vec3 look = player.getLookAngle();
        EnchUtils.pushLiving(player, look.x * force, look.y * force, look.z * force);
        event.getBow().hurtAndBreak(1, player, net.minecraft.world.entity.LivingEntity.getSlotForHand(player.getUsedItemHand()));
        event.setCanceled(true);
    }
}
