package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.BUBBLE_SHIELD;

@EventBusSubscriber(modid = MODID)
public final class BubbleShieldHandler {
    private static final double AIR_SUPPLY_RATIO = 0.8D;
    private static final double COST_RATIO = 0.7D;
    private static final double MULTIPLIER = 0.1D;

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof net.minecraft.world.entity.player.Player player)) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(BUBBLE_SHIELD, player);
        if (level <= 0 || player.getAirSupply() < player.getMaxAirSupply() * AIR_SUPPLY_RATIO) {
            return;
        }

        int reducer = (int) (player.getAirSupply() * COST_RATIO * MULTIPLIER * level);
        player.setAirSupply((int) Math.max(0, player.getAirSupply() * (1.0D - COST_RATIO)));
        event.setAmount(Math.max(0.0F, event.getAmount() - reducer));
    }
}
