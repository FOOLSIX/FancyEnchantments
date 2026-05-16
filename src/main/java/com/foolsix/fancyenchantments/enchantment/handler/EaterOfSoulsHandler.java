package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.EATER_OF_SOULS;

@EventBusSubscriber(modid = MODID)
public final class EaterOfSoulsHandler {
    private static final String KILL_COUNT_TAG = MODID + ":eater_of_souls_killcount";
    private static final ResourceLocation MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(MODID, "eater_of_souls/attack_damage");

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        ItemStack mainHand = player.getMainHandItem();
        int mainLevel = EnchUtils.getEnchantmentLevel(EATER_OF_SOULS, mainHand, player.registryAccess());
        if (mainLevel > 0) {
            event.getEntity().skipDropExperience();
            CustomData.update(DataComponents.CUSTOM_DATA, mainHand, tag -> tag.putInt(KILL_COUNT_TAG, getKillCount(mainHand) + 1));
        }

        if (EnchUtils.getEnchantmentLevel(EATER_OF_SOULS, player.getOffhandItem(), player.registryAccess()) > 0) {
            event.getEntity().skipDropExperience();
        }
    }

    @SubscribeEvent
    public static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        int level = EnchUtils.getEnchantmentLevel(EATER_OF_SOULS, stack, null);
        if (level <= 0) {
            return;
        }

        double bonus = Math.min(
                Config.EATER_OF_SOULS_DAMAGE_MULTIPLIER.get() * Math.sqrt(getKillCount(stack)) * level,
                Config.EATER_OF_SOULS_CAP.get()
        );
        if (bonus <= 0.0D) {
            return;
        }

        event.addModifier(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(MODIFIER_ID, bonus, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND
        );
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        var registries = event.getContext().registries();
        if (registries == null) {
            return;
        }

        if (EnchUtils.getEnchantmentLevel(EATER_OF_SOULS, event.getItemStack(), registries) <= 0) {
            return;
        }

        int killCount = getKillCount(event.getItemStack());
        if (killCount > 0) {
            event.getToolTip().add(Component.literal("Kill Count: ").append(String.valueOf(killCount)).withStyle(ChatFormatting.DARK_PURPLE));
        }
    }

    private static int getKillCount(ItemStack stack) {
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        return data.contains(KILL_COUNT_TAG) ? data.copyTag().getInt(KILL_COUNT_TAG) : 0;
    }
}
