package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.ARMOR_FORGING;

@EventBusSubscriber(modid = MODID)
public final class ArmorForgingHandler {
    private static final String FORGING_VALUE_TAG = MODID + ":forging_value";

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getSource().is(DamageTypeTags.BYPASSES_ARMOR) || event.getSource().is(DamageTypeTags.BYPASSES_ENCHANTMENTS)) {
            return;
        }
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        java.util.List<ItemStack> forgeableArmor = new java.util.ArrayList<>();
        int forgingValueCapPerLevel = Config.ARMOR_FORGING_VALUE_CAP_PER_LEVEL.get();
        for (ItemStack stack : player.getArmorSlots()) {
            int level = EnchUtils.getEnchantmentLevel(ARMOR_FORGING, stack, player.registryAccess());
            if (level > 0 && getForgingValue(stack) < level * forgingValueCapPerLevel) {
                forgeableArmor.add(stack);
            }
        }

        if (forgeableArmor.isEmpty()) {
            return;
        }

        int forgingPerArmor = (int) (event.getAmount() / forgeableArmor.size()) + 1;
        for (ItemStack armor : forgeableArmor) {
            int level = EnchUtils.getEnchantmentLevel(ARMOR_FORGING, armor, player.registryAccess());
            int maxValue = level * forgingValueCapPerLevel;
            int newValue = Math.min(getForgingValue(armor) + forgingPerArmor, maxValue);
            setForgingValue(armor, newValue);
        }
    }

    @SubscribeEvent
    public static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        int level = EnchUtils.getEnchantmentLevel(ARMOR_FORGING, stack, null);
        if (level <= 0) {
            return;
        }

        EquipmentSlot slot = EnchUtils.getEquipmentSlot(stack);

        int forgingValue = getForgingValue(stack);
        if (forgingValue <= 0) {
            return;
        }

        ResourceLocation modifierId = ResourceLocation.fromNamespaceAndPath(MODID, "armor_forging/" + slot.getSerializedName());
        EquipmentSlotGroup slotGroup = EquipmentSlotGroup.bySlot(slot);
        int armorBase = Config.ARMOR_FORGING_ARMOR_BASE.get();
        int toughnessBase = Config.ARMOR_FORGING_TOUGHNESS_BASE.get();
        event.addModifier(
                Attributes.ARMOR,
                new AttributeModifier(modifierId, (double) forgingValue / armorBase, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                slotGroup
        );
        event.addModifier(
                Attributes.ARMOR_TOUGHNESS,
                new AttributeModifier(modifierId, (double) forgingValue / toughnessBase, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                slotGroup
        );
    }

    private static int getForgingValue(ItemStack stack) {
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        return data.contains(FORGING_VALUE_TAG) ? data.copyTag().getInt(FORGING_VALUE_TAG) : 0;
    }

    private static void setForgingValue(ItemStack stack, int forgingValue) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt(FORGING_VALUE_TAG, forgingValue));
    }
}
