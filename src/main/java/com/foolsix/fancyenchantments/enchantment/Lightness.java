package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AerEnchentment;
import com.foolsix.fancyenchantments.enchantment.util.EnchIDs;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.living.LivingEvent;


public class Lightness extends AerEnchentment {
    private static final ModConfig.LightnessOptions CONFIG = FancyEnchantments.getConfig().lightnessOptions;

    public Lightness() {
        super(Rarity.COMMON, EnchantmentCategory.WEAPON,
                new EquipmentSlot[]{EquipmentSlot.OFFHAND, EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.canPerformAction(ToolActions.SHIELD_BLOCK);
    }

    @Override
    public int getMaxLevel() {
        return CONFIG.level;
    }

    @Override
    public int getMinCost(int pLevel) {
        return 8 + pLevel * 5;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 15;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return CONFIG.level != 0;
    }

    public void livingEvent(LivingEvent.LivingTickEvent e) {
        LivingEntity living = e.getEntity();
        AttributeInstance moveSpeedAttr = e.getEntity().getAttribute(Attributes.MOVEMENT_SPEED);
        if (moveSpeedAttr != null) {
            moveSpeedAttr.removeModifier(EnchIDs.LIGHTNESS_UUID);
        }
        int l = living.getUseItem().getEnchantmentLevel(this);
        if (l > 0) {
            if (moveSpeedAttr != null) {
                moveSpeedAttr.addTransientModifier(new AttributeModifier(EnchIDs.LIGHTNESS_UUID, "lightness", CONFIG.speedMultiplier * l, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
    }
}


