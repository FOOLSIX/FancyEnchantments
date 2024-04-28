package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import static net.minecraft.world.item.Item.BASE_ATTACK_DAMAGE_UUID;


public class TheFallen extends FEBaseEnchantment {
    private static final ModConfig.TheFallenOptions CONFIG = FancyEnchantments.getConfig().theFallenOptions;

    public TheFallen() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public Component getFullname(int level) {
        return ((MutableComponent) super.getFullname(level)).withStyle(ChatFormatting.DARK_PURPLE);
    }

    @Override
    public float getDamageBonus(int level, MobType mobType, ItemStack enchantedItem) {
        float damage = 5.0f;

        for (AttributeModifier modifier : enchantedItem.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE)) {
            if (modifier.getId().equals(BASE_ATTACK_DAMAGE_UUID)) {
                damage = (float) modifier.getAmount();
                break;
            }
        }
        int curseCount = 0;
        for (Enchantment enchantment : enchantedItem.getAllEnchantments().keySet()) {
            if (enchantment.isCurse()) {
                ++curseCount;
            }
        }
        return damage * curseCount * level * CONFIG.damageMultiplier;
    }
}
