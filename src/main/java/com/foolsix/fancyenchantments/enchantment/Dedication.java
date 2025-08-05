package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.HolyEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.ItemAttributeModifierEventHandler;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

import static net.minecraft.world.entity.LivingEntity.getEquipmentSlotForItem;

public class Dedication extends HolyEnchantment implements ItemAttributeModifierEventHandler {
    private static final ModConfig.DedicationOptions CONFIG = FancyEnchantments.getConfig().dedicationOptions;
    private final UUID MODIFIER_UUID = UUID.fromString("1f06b7f6-d0e2-6eb2-a70b-e242be689e12");
    private final String TAG = "fe_dedication";


    public Dedication() {
        super(CONFIG, EnchantmentCategory.ARMOR, new EquipmentSlot[]{
                EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
        });
    }

    @Override
    public void doPostHurt(LivingEntity pTarget, Entity pAttacker, int pLevel) {
        for (ItemStack stack : pTarget.getArmorSlots()) {
            int level = stack.getEnchantmentLevel(this);
            if (level <= 0 || Math.random() < CONFIG.probability) continue;
            EnchUtils.removeEnchantment(stack, this);
            int lv = stack.getOrCreateTag().getInt(TAG);
            stack.getOrCreateTag().putInt(TAG, level + lv);
            break;
        }
    }

    @Override
    public void handleItemAttributeModifier(ItemAttributeModifierEvent e) {
        ItemStack stack = e.getItemStack();
        EquipmentSlot equipmentSlot = e.getSlotType();
        if (!stack.hasTag()) return;
        int level = stack.getOrCreateTag().getInt(TAG);
        if (level > 0 && equipmentSlot == getEquipmentSlotForItem(stack)) {
            e.addModifier(Attributes.ARMOR, new AttributeModifier(MODIFIER_UUID, TAG, level * CONFIG.armorBonusMultiplier, AttributeModifier.Operation.ADDITION));
            e.addModifier(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(MODIFIER_UUID, TAG, level * CONFIG.toughnessBonusMultiplier, AttributeModifier.Operation.ADDITION));
        }
    }
}
