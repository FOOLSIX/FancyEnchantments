package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.PURE_FATE;

@EventBusSubscriber(modid = MODID)
public final class PureFateHandler {
    @SubscribeEvent
    public static void onBabyEntitySpawn(BabyEntitySpawnEvent event) {
        if (!(event.getCausedByPlayer() instanceof ServerPlayer player)) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(PURE_FATE, player);
        if (level <= 0 || player.getRandom().nextDouble() >= Config.CURSE_REMOVAL_PROBABILITY.get() * level) {
            return;
        }

        for (ItemStack stack : player.getAllSlots()) {
            ItemEnchantments enchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
            var curse = enchantments.entrySet().stream()
                    .filter(e -> e.getKey().is(EnchantmentTags.CURSE))
                    .findFirst();
            if (curse.isPresent()) {
                EnchantmentHelper.updateEnchantments(
                        stack,
                        mutable -> mutable.removeIf(holder -> holder.is(EnchantmentTags.CURSE))
                );
                EnchUtils.generateSimpleParticleAroundEntity(player, ParticleTypes.HAPPY_VILLAGER);
                return;
            }
        }
    }
}
