package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AerEnchantment;
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

import java.util.UUID;


public class Lightness extends AerEnchantment {
    public static final UUID LIGHTNESS_UUID = UUID.fromString("44376746-5129-47fd-850e-dc89ac2d0cdf");
    public static final String NAME = "lightness";
    private static final ModConfig.LightnessOptions CONFIG = FancyEnchantments.getConfig().lightnessOptions;

    public Lightness() {
        super(CONFIG, EnchantmentCategory.WEAPON,
                new EquipmentSlot[]{EquipmentSlot.OFFHAND, EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.canPerformAction(ToolActions.SHIELD_BLOCK);
    }

    @Override
    public int getMinCost(int pLevel) {
        return 8 + pLevel * 5;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 15;
    }

    public void livingEvent(LivingEvent.LivingTickEvent e) {
        LivingEntity living = e.getEntity();
        AttributeInstance moveSpeedAttr = e.getEntity().getAttribute(Attributes.MOVEMENT_SPEED);
        if (moveSpeedAttr != null) {
            moveSpeedAttr.removeModifier(LIGHTNESS_UUID);
        }
        int l = living.getUseItem().getEnchantmentLevel(this);
        if (l > 0) {
            if (moveSpeedAttr != null) {
                moveSpeedAttr.addTransientModifier(new AttributeModifier(LIGHTNESS_UUID, NAME, CONFIG.speedMultiplier * l, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
    }
}


