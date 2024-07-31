package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TerraEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.TickEvent;

import java.util.UUID;


public class SharpRock extends TerraEnchantment {
    public static final String NAME = "sharp_rock";
    private static final ModConfig.SharpRockOptions CONFIG = FancyEnchantments.getConfig().sharpRockOptions;
    private final String ARMOR_TAG = NAME + "_armor";
    private final UUID SHARP_ROCK_UUID = UUID.fromString("0cb3ecd9-2b13-91f8-8d44-8e2da1ee0958");

    public SharpRock() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return super.canApplyAtEnchantingTable(stack) || stack.getItem() instanceof ShieldItem;
    }

    public void updateDamage(TickEvent.PlayerTickEvent e) {
        if (e.player.tickCount % 20 == 0) {
            ItemStack stack = e.player.getMainHandItem();
            int level = stack.getEnchantmentLevel(this);
            if (level > 0) {
                AttributeInstance armorAttr = e.player.getAttribute(Attributes.ARMOR);
                double armor = armorAttr == null ? 0 : armorAttr.getValue();
                stack.getOrCreateTag().putDouble(ARMOR_TAG, armor);
            } else if (stack.getTag() != null && stack.getTag().contains(ARMOR_TAG)) {
                stack.getTag().remove(ARMOR_TAG);
            }
        }
    }

    @Override
    public float getDamageBonus(int level, MobType mobType, ItemStack enchantedItem) {
        return (float) (enchantedItem.getOrCreateTag().getDouble(ARMOR_TAG) * level * CONFIG.damageMultiplierForOthers);
    }

    public void attachAttributes(ItemAttributeModifierEvent e) {
        if (e.getSlotType() == EquipmentSlot.MAINHAND) {
            ItemStack stack = e.getItemStack();
            int level = stack.getEnchantmentLevel(this);
            if (level > 0 && stack.getItem() instanceof ShieldItem) {
                double damageMultiplier = CONFIG.damageMultiplierForShield;
                e.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(SHARP_ROCK_UUID,
                        NAME,
                        stack.getOrCreateTag().getDouble(ARMOR_TAG) * level * damageMultiplier,
                        AttributeModifier.Operation.ADDITION));
            }
        }
    }
}
