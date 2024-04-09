package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TerraEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.living.LivingEvent;

import static com.foolsix.fancyenchantments.enchantment.util.EnchIDs.SOLID_AS_A_ROCK_NAME;
import static com.foolsix.fancyenchantments.enchantment.util.EnchIDs.SOLID_AS_A_ROCK_UUID;

public class SolidAsARock extends TerraEnchantment {
    private static final ModConfig.SolidAsARockOptions CONFIG = FancyEnchantments.getConfig().solidAsARockOptions;

    public SolidAsARock() {
        super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return CONFIG.level;
    }

    @Override
    public int getMinCost(int pLevel) {
        return 5 + pLevel * 5;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 10;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.canPerformAction(ToolActions.SHIELD_BLOCK);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return CONFIG.level != 0;
    }

    public void addArmor(LivingEvent.LivingTickEvent e) {
        LivingEntity living = e.getEntity();
        AttributeInstance armorAttr = living.getAttribute(Attributes.ARMOR);
        AttributeInstance armorToughnessAttr = living.getAttribute(Attributes.ARMOR_TOUGHNESS);
        if (armorAttr != null) {
            armorAttr.removeModifier(SOLID_AS_A_ROCK_UUID);
        }
        if (armorToughnessAttr != null) {
            armorToughnessAttr.removeModifier(SOLID_AS_A_ROCK_UUID);
        }
        int level = EnchantmentHelper.getEnchantmentLevel(this, living);
        if (level > 0) {
            if (armorAttr != null) {
                armorAttr.addTransientModifier(new AttributeModifier(SOLID_AS_A_ROCK_UUID, SOLID_AS_A_ROCK_NAME, 1.0f + CONFIG.armorMultiplier * level, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
            if (armorToughnessAttr != null) {
                armorToughnessAttr.addTransientModifier(new AttributeModifier(SOLID_AS_A_ROCK_UUID, SOLID_AS_A_ROCK_NAME, 1.0f + CONFIG.toughnessMultiplier * level, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
    }
}
