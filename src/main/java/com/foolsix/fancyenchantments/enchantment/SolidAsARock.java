package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TerraEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.ItemAttributeModifierEventHandler;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

import static net.minecraftforge.common.ToolActions.SHIELD_BLOCK;

public class SolidAsARock extends TerraEnchantment implements ItemAttributeModifierEventHandler {
    public static final String NAME = "solid_as_a_rock";
    public static final UUID SOLID_AS_A_ROCK_UUID = UUID.fromString("95a86f29-19c7-4b4f-8c6b-77747f10019b");
    private static final ModConfig.SolidAsARockOptions CONFIG = FancyEnchantments.getConfig().solidAsARockOptions;

    public SolidAsARock() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMinCost(int pLevel) {
        return 8 + pLevel * 5;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.canPerformAction(SHIELD_BLOCK);
    }

    @Override
    public void handleItemAttributeModifier(ItemAttributeModifierEvent e) {
        addArmor(e);
    }

    public void addArmor(ItemAttributeModifierEvent e) {
        int level = e.getItemStack().getEnchantmentLevel(this);
        ItemStack stack = e.getItemStack();
        if (!(stack.getItem() instanceof ShieldItem) && !stack.canPerformAction(SHIELD_BLOCK))
            return;
        if (level > 0 && (e.getSlotType() == EquipmentSlot.MAINHAND || e.getSlotType() == EquipmentSlot.OFFHAND)) {
            e.addModifier(Attributes.ARMOR, new AttributeModifier(SOLID_AS_A_ROCK_UUID, NAME, CONFIG.armorMultiplier * level, AttributeModifier.Operation.MULTIPLY_TOTAL));
            e.addModifier(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(SOLID_AS_A_ROCK_UUID, NAME, CONFIG.toughnessMultiplier * level, AttributeModifier.Operation.MULTIPLY_TOTAL));
            e.addModifier(Attributes.MOVEMENT_SPEED,  new AttributeModifier(SOLID_AS_A_ROCK_UUID, NAME, -CONFIG.movementSpeedReducer * level, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
    }
}
