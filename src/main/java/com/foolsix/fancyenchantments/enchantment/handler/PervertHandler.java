package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.PERVERT;

@EventBusSubscriber(modid = MODID)
public final class PervertHandler {
    @SubscribeEvent
    public static void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)
                || event.getSlot() != EquipmentSlot.LEGS) {
            return;
        }
        ItemStack equipped = event.getTo();

        int level = EnchUtils.getEnchantmentLevel(PERVERT, equipped, player.registryAccess());
        if (level <= 0 || player.getRandom().nextDouble() >= Config.DROP_PROBABILITY.get()) {
            return;
        }

        player.drop(equipped.copy(), false);
        equipped.setCount(0);
    }
}
