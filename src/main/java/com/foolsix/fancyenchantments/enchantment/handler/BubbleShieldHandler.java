package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.BUBBLE_SHIELD;

@EventBusSubscriber(modid = MODID)
public final class BubbleShieldHandler {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof net.minecraft.world.entity.player.Player player)) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(BUBBLE_SHIELD, player);
        double airSupplyRatio = Config.BUBBLE_SHIELD_AIR_SUPPLY_RATIO.get();
        if (level <= 0 || player.getAirSupply() < player.getMaxAirSupply() * airSupplyRatio) {
            return;
        }

        double costRatio = Config.BUBBLE_SHIELD_COST_RATIO.get();
        int reducer = (int) (player.getAirSupply() * costRatio * Config.BUBBLE_SHIELD_DAMAGE_MULTIPLIER.get() * level);
        player.setAirSupply((int) Math.max(0, player.getAirSupply() * (1.0D - costRatio)));
        event.setAmount(Math.max(0.0F, event.getAmount() - reducer));
    }
}
