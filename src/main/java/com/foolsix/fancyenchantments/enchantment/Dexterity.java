package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AerEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.ItemAttributeModifierEventHandler;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class Dexterity extends AerEnchantment implements ItemAttributeModifierEventHandler {
    public static final String NAME = "dexterity";
    private final UUID MODIFIER_UUID = UUID.fromString("540ca84e-feca-ad00-0ee0-0aef44014cec");
    private static final ModConfig.DexterityOptions CONFIG = FancyEnchantments.getConfig().dexterityOptions;

    public Dexterity() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public void handleItemAttributeModifier(ItemAttributeModifierEvent e) {
        addRange(e);
    }

    public void addRange(ItemAttributeModifierEvent e) {
        int level = e.getItemStack().getEnchantmentLevel(this);
        if (level > 0 && e.getSlotType() == EquipmentSlot.MAINHAND) {
            e.addModifier(ForgeMod.ATTACK_RANGE.get(), new AttributeModifier(MODIFIER_UUID, "dexterity modifier", level * CONFIG.rangeMultiplierPerLevel, AttributeModifier.Operation.ADDITION));
        }
    }
}
