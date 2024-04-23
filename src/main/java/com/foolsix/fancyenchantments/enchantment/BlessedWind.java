package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AerEnchentment;
import com.foolsix.fancyenchantments.enchantment.util.EnchIDs;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;

public class BlessedWind extends AerEnchentment {
    private static final ModConfig.BlessedWindOptions CONFIG = FancyEnchantments.getConfig().blessedWind;

    public BlessedWind() {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public int getMaxLevel() {
        return CONFIG.level;
    }

    @Override
    public int getMinCost(int pLevel) {
        return 5 + pLevel * 8;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 45;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return CONFIG.level != 0;
    }

    public void speedBoostWhileSprinting(TickEvent.PlayerTickEvent e) {
        int level = EnchantmentHelper.getEnchantmentLevel(this, e.player);
        AttributeInstance moveSpeedAttr = e.player.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeInstance stepHeightAttribute = e.player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
        if (moveSpeedAttr != null) {
            moveSpeedAttr.removeModifier(EnchIDs.BLESSED_WIND_UUID);
        }
        if (stepHeightAttribute != null) {
            stepHeightAttribute.removeModifier(EnchIDs.BLESSED_WIND_UUID);
        }

        if (level > 0) {
            if (e.player.isSprinting()) {
                if (moveSpeedAttr != null) {
                    moveSpeedAttr.addTransientModifier(new AttributeModifier(EnchIDs.BLESSED_WIND_UUID, EnchIDs.BLESSED_WIND_NAME, CONFIG.sprintSpeedMultiplier * level, AttributeModifier.Operation.ADDITION));
                }
                if (stepHeightAttribute != null) {
                    stepHeightAttribute.addTransientModifier(new AttributeModifier(EnchIDs.BLESSED_WIND_UUID, EnchIDs.BLESSED_WIND_NAME, level, AttributeModifier.Operation.ADDITION));
                }
            } else {
                if (moveSpeedAttr != null) {
                    moveSpeedAttr.addTransientModifier(new AttributeModifier(EnchIDs.BLESSED_WIND_UUID, EnchIDs.BLESSED_WIND_NAME, CONFIG.walkSpeedMultiplier * level, AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }

}
