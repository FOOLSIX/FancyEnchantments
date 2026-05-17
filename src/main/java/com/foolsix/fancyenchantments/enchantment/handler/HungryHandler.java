package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import java.util.HashSet;
import java.util.Set;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.HUNGRY;

@EventBusSubscriber(modid = MODID)
public final class HungryHandler {
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(HUNGRY, attacker);
        if (level <= 0) {
            return;
        }

        Set<ItemEntity> extraDrops = new HashSet<>();
        for (ItemEntity item : event.getDrops()) {
            if (item.getItem().getFoodProperties(attacker) == null) {
                continue;
            }

            for (int roll = 0; roll < level; ++roll) {
                if (attacker.getRandom().nextDouble() < Config.HUNGRY_PROBABILITY.get() * level) {
                    extraDrops.add(item.copy());
                }
            }
        }

        event.getDrops().addAll(extraDrops);
    }
}
