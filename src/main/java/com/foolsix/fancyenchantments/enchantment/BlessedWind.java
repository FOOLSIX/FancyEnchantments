package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AerEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;

import java.util.UUID;

public class BlessedWind extends AerEnchantment {
    public static final String NAME = "blessed_wind";
    public static final UUID BLESSED_WIND_UUID = UUID.fromString("99ffaa05-0273-4e18-afa0-c4f608313859");
    private static final ModConfig.BlessedWindOptions CONFIG = FancyEnchantments.getConfig().blessedWindOptions;

    public BlessedWind() {
        super(CONFIG, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public int getMinCost(int pLevel) {
        return 5 + pLevel * 8;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 45;
    }

    public void speedBoostWhileSprinting(TickEvent.PlayerTickEvent e) {
        int level = EnchantmentHelper.getEnchantmentLevel(this, e.player);
        AttributeInstance moveSpeedAttr = e.player.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeInstance stepHeightAttribute = e.player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
        if (moveSpeedAttr != null) {
            moveSpeedAttr.removeModifier(BLESSED_WIND_UUID);
        }
        if (stepHeightAttribute != null) {
            stepHeightAttribute.removeModifier(BLESSED_WIND_UUID);
        }

        if (level > 0) {
            if (e.player.isSprinting()) {
                if (moveSpeedAttr != null) {
                    moveSpeedAttr.addTransientModifier(new AttributeModifier(BLESSED_WIND_UUID, NAME, CONFIG.sprintSpeedMultiplier * level, AttributeModifier.Operation.ADDITION));
                }
                if (stepHeightAttribute != null) {
                    stepHeightAttribute.addTransientModifier(new AttributeModifier(BLESSED_WIND_UUID, NAME, level, AttributeModifier.Operation.ADDITION));
                }
            } else {
                if (moveSpeedAttr != null) {
                    moveSpeedAttr.addTransientModifier(new AttributeModifier(BLESSED_WIND_UUID, NAME, CONFIG.walkSpeedMultiplier * level, AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }

}
