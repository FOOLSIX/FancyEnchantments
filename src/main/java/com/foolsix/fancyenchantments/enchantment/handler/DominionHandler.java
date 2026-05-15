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
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.DOMINION;

@EventBusSubscriber(modid = MODID)
public final class DominionHandler {
    private static final ResourceLocation MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(MODID, "dominion/attack_damage");

    @SubscribeEvent
    public static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        int level = EnchUtils.getEnchantmentLevel(DOMINION, stack, null);
        if (level <= 0) {
            return;
        }

        int levelSum = 0;
        for (var entry : EnchUtils.enchantmentsOn(stack).entrySet()) {
            levelSum += entry.getIntValue();
        }

        if (levelSum > 0) {
            event.addModifier(
                    Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(MODIFIER_ID, levelSum * Config.DOMINION_DAMAGE_MULTIPLIER.get(), AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.MAINHAND
            );
        }
    }
}
