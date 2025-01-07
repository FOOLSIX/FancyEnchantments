package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TerraEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.ItemAttributeModifierEventHandler;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class Sander extends TerraEnchantment implements ItemAttributeModifierEventHandler {
    public static final String NAME = "sander";
    private static final ModConfig.SanderOptions CONFIG = FancyEnchantments.getConfig().sanderOptions;
    private static final UUID SANDER_UUID = UUID.fromString("2a23fe50-0957-3417-2f33-4b880fa7cf26");

    public Sander() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public void handleItemAttributeModifier(ItemAttributeModifierEvent e) {
        attribute(e);
    }

    public void attribute(ItemAttributeModifierEvent e) {
        if (e.getSlotType() == EquipmentSlot.MAINHAND) {
            ItemStack stack = e.getItemStack();
            int level = stack.getEnchantmentLevel(this);
            if (level > 0 && stack.getMaxDamage() > 0) {
                double addon = (double) stack.getDamageValue() / stack.getMaxDamage();
                e.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(SANDER_UUID, NAME, addon * level * CONFIG.damageMultiplier, AttributeModifier.Operation.MULTIPLY_BASE));
            }
        }
    }
}
