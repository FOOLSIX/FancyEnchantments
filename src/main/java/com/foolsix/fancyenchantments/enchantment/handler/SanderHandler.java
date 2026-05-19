package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.SANDER;

@EventBusSubscriber(modid = MODID)
public final class SanderHandler {
    private static final ResourceLocation SANDER_ID = ResourceLocation.fromNamespaceAndPath(MODID, "sander/attack_damage");

    @SubscribeEvent
    public static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        int level = EnchUtils.getEnchantmentLevel(SANDER, stack, null);
        if (level <= 0 || stack.getMaxDamage() <= 0) return;

        double addon = (double) stack.getDamageValue() / stack.getMaxDamage();
        event.addModifier(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(
                        SANDER_ID,
                        addon * level * Config.SANDER_DAMAGE_MULTIPLIER.get(),
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                ),
                EquipmentSlotGroup.MAINHAND
        );
    }
}
