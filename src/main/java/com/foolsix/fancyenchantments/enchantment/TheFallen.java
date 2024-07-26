package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TwistedEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;


public class TheFallen extends TwistedEnchantment {
    public static final String NAME = "the_fallen";
    private static final ModConfig.TheFallenOptions CONFIG = FancyEnchantments.getConfig().theFallenOptions;
    private static final UUID THE_FALLEN_UUID = UUID.fromString("e216f084-d869-a173-8ff5-dfcbcde99956");

    public TheFallen() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public void addDamageBonus(ItemAttributeModifierEvent e) {
        if (e.getSlotType() == EquipmentSlot.MAINHAND) {
            ItemStack stack = e.getItemStack();
            int level = stack.getEnchantmentLevel(this);
            if (level > 0) {
                int curseCount = 0;
                for (Enchantment enchantment : stack.getAllEnchantments().keySet()) {
                    if (enchantment.isCurse()) {
                        ++curseCount;
                    }
                }
                e.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(THE_FALLEN_UUID,
                        "the_fallen_damage_bonus",
                        curseCount * level * CONFIG.damageMultiplier,
                        AttributeModifier.Operation.MULTIPLY_BASE));
            }
        }
    }
}
