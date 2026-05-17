package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.LITHIC_SIPHON;

@EventBusSubscriber(modid = MODID)
public final class LithicSiphonHandler {
    @SubscribeEvent
    public static void onBlockDrops(BlockDropsEvent event) {
        if (event.isCanceled()) {
            return;
        }

        if (!(event.getBreaker() instanceof Player player)) {
            return;
        }

        ItemStack stack = event.getTool();
        int level = EnchUtils.getEnchantmentLevel(LITHIC_SIPHON, stack, player.registryAccess());
        if (level <= 0 || !event.getState().is(Tags.Blocks.STONES)) {
            return;
        }

        event.getDrops().removeIf(drop -> {
            ItemStack droppedStack = drop.getItem();
            return droppedStack.is(Tags.Items.STONES) || droppedStack.is(Tags.Items.COBBLESTONES);
        });

        if (player.getRandom().nextDouble() > Config.LITHIC_SIPHON_PROBABILITY_PER_LEVEL.get() * level) {
            return;
        }

        HolderSet.Named<net.minecraft.world.item.Item> rawMaterials = player.registryAccess()
                .lookupOrThrow(Registries.ITEM)
                .getOrThrow(Tags.Items.RAW_MATERIALS);
        ItemStack newDrop = new ItemStack(rawMaterials.getRandomElement(player.getRandom()).orElseGet(() -> player.registryAccess()
                .lookupOrThrow(Registries.ITEM)
                .getOrThrow(BuiltInRegistries.ITEM.getResourceKey(Items.AIR).orElseThrow())));

        int fortuneLevel = stack.getEnchantmentLevel(player.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE));
        int amount = 1;
        if (fortuneLevel > 0) {
            double chance = 1.0D / (fortuneLevel + 2.0D);
            amount += Math.max((int) (player.getRandom().nextDouble() / chance - 1.0D), 0);
        }

        for (int index = 0; index < amount; ++index) {
            event.getDrops().add(new ItemEntity(
                    event.getLevel(),
                    event.getPos().getX() + 0.5D,
                    event.getPos().getY() + 0.5D,
                    event.getPos().getZ() + 0.5D,
                    newDrop.copy()
            ));
        }
    }
}
