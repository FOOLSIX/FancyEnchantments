package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.DEDICATION;

@EventBusSubscriber(modid = MODID)
public final class DedicationHandler {
    private static final String TAG = MODID + ":dedication";

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        for (ItemStack stack : event.getEntity().getArmorSlots()) {
            int level = EnchUtils.getEnchantmentLevel(DEDICATION, stack, event.getEntity().registryAccess());
            if (level <= 0 || event.getEntity().getRandom().nextDouble() < Config.DEDICATION_PROBABILITY.get()) {
                continue;
            }

            setStoredLevel(stack, getStoredLevel(stack) + level);
            removeEnchantment(stack, DEDICATION.location().toString());
            break;
        }
    }

    @SubscribeEvent
    public static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        int storedLevel = getStoredLevel(stack);
        if (storedLevel <= 0) {
            return;
        }

        EquipmentSlot slot = EnchUtils.getEquipmentSlot(stack);
        if (slot == null) {
            return;
        }

        ResourceLocation armorId = ResourceLocation.fromNamespaceAndPath(MODID, "dedication/" + slot.getSerializedName() + "/armor");
        ResourceLocation toughnessId = ResourceLocation.fromNamespaceAndPath(MODID, "dedication/" + slot.getSerializedName() + "/toughness");
        event.addModifier(
                Attributes.ARMOR,
                new AttributeModifier(armorId, storedLevel * Config.DEDICATION_ARMOR_BONUS_MULTIPLIER.get(), AttributeModifier.Operation.ADD_VALUE),
                net.minecraft.world.entity.EquipmentSlotGroup.bySlot(slot)
        );
        event.addModifier(
                Attributes.ARMOR_TOUGHNESS,
                new AttributeModifier(toughnessId, storedLevel * Config.DEDICATION_TOUGHNESS_BONUS_MULTIPLIER.get(), AttributeModifier.Operation.ADD_VALUE),
                net.minecraft.world.entity.EquipmentSlotGroup.bySlot(slot)
        );
    }

    private static int getStoredLevel(ItemStack stack) {
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        return data.contains(TAG) ? data.copyTag().getInt(TAG) : 0;
    }

    private static void setStoredLevel(ItemStack stack, int level) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt(TAG, level));
    }

    private static void removeEnchantment(ItemStack stack, String enchantmentId) {
        EnchantmentHelper.updateEnchantments(
                stack,
                enchantments -> enchantments.removeIf(
                        holder -> holder.unwrapKey().map(key -> key.location().toString().equals(enchantmentId)).orElse(false)
                )
        );
    }
}
