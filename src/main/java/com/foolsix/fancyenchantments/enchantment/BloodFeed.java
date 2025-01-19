package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.ItemAttributeModifierEventHandler;
import com.foolsix.fancyenchantments.enchantment.handler.LivingDeathEventHandler;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.UUID;

public class BloodFeed extends FEBaseEnchantment implements LivingDeathEventHandler, ItemAttributeModifierEventHandler {
    private static final ModConfig.BloodFeedOptions CONFIG = FancyEnchantments.getConfig().bloodFeedOptions;
    private final UUID UUID = java.util.UUID.fromString("35549e52-4f4b-41a1-9f56-956813a0a237");
    private final String TAG = "blood_feed";
    public BloodFeed() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public Component getFullname(int pLevel) {
        return EnchUtils.getMixedColorFullName(super.getFullname(pLevel), EnchUtils.Element.AQUA, EnchUtils.Element.TWISTED);
    }

    @Override
    public void handleLivingDeathEvent(LivingDeathEvent e) {
        if (e.getSource().getEntity() instanceof Player player) {
            ItemStack tool = player.getMainHandItem();
            int level = tool.getEnchantmentLevel(this);
            if (level > 0) {
                if (Math.random() < CONFIG.probabilityPerLevel * level) {
                    CompoundTag nbt = tool.getOrCreateTag();
                    nbt.putInt(TAG, Math.min(CONFIG.CapPerLevel * level, nbt.getInt(TAG) + 1));
                }
            }
        }
    }

    @Override
    public void handleItemAttributeModifier(ItemAttributeModifierEvent e) {
        ItemStack stack = e.getItemStack();
        if ((e.getSlotType() == EquipmentSlot.MAINHAND || e.getSlotType() == EquipmentSlot.OFFHAND) && stack.getEnchantmentLevel(this) > 0) {
            e.addModifier(Attributes.MAX_HEALTH, new AttributeModifier(UUID, "blood_feed", stack.getOrCreateTag().getInt(TAG), AttributeModifier.Operation.ADDITION));
        }
    }
}
