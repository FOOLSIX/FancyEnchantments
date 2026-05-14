package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.BLOOD_FEED;

@EventBusSubscriber(modid = MODID)
public final class BloodFeedHandler {
    private static final String STACK_TAG = MODID + ":blood_feed";
    private static final ResourceLocation MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(MODID, "blood_feed/max_health");

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        ItemStack weapon = player.getMainHandItem();
        int level = EnchUtils.getEnchantmentLevel(BLOOD_FEED, weapon, player.registryAccess());
        if (level <= 0 || player.getRandom().nextDouble() >= Config.BLOOD_FEED_PROBABILITY_PER_LEVEL.get() * level) {
            return;
        }

        int next = Math.min(Config.BLOOD_FEED_CAP_PER_LEVEL.get() * level, getStoredValue(weapon) + 1);
        CustomData.update(DataComponents.CUSTOM_DATA, weapon, tag -> tag.putInt(STACK_TAG, next));
    }

    @SubscribeEvent
    public static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        int level = EnchUtils.getEnchantmentLevel(BLOOD_FEED, stack, null);
        if (level <= 0) {
            return;
        }

        int storedValue = getStoredValue(stack);
        if (storedValue <= 0) {
            return;
        }

        event.addModifier(
                Attributes.MAX_HEALTH,
                new AttributeModifier(MODIFIER_ID, storedValue, AttributeModifier.Operation.ADD_VALUE),
                net.minecraft.world.entity.EquipmentSlotGroup.HAND
        );
    }

    private static int getStoredValue(ItemStack stack) {
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        return data.contains(STACK_TAG) ? data.copyTag().getInt(STACK_TAG) : 0;
    }
}
