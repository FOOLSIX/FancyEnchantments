package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.BLIND_LOYALTY;

@EventBusSubscriber(modid = MODID)
public final class BlindLoyaltyHandler {
    private static final String OWNER_TAG = MODID + ":blind_loyalty_owner";

    @SubscribeEvent
    public static void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (event.getTo().isEmpty() || EnchUtils.getEnchantmentLevel(BLIND_LOYALTY, event.getTo(), player.registryAccess()) <= 0) {
            return;
        }

        CustomData.update(DataComponents.CUSTOM_DATA, event.getTo(), tag -> tag.putUUID(OWNER_TAG, player.getUUID()));
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }
        if (!(event.getEntity() instanceof ItemEntity itemEntity) || !(event.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }

        var stack = itemEntity.getItem();
        if (stack.isEmpty() || EnchUtils.getEnchantmentLevel(BLIND_LOYALTY, stack, serverLevel.registryAccess()) <= 0) {
            return;
        }

        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if (!data.contains(OWNER_TAG)) {
            return;
        }

        Player player = serverLevel.getPlayerByUUID(data.copyTag().getUUID(OWNER_TAG));
        if (player != null && player.addItem(stack)) {
            itemEntity.remove(Entity.RemovalReason.DISCARDED);
        }
    }
}
