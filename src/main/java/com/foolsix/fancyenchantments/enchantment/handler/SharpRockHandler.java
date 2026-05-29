package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.SHARP_ROCK;

@EventBusSubscriber(modid = MODID)
public final class SharpRockHandler {
    private static final ResourceLocation SHARP_ROCK_ID = ResourceLocation.fromNamespaceAndPath(MODID, "sharp_rock/attack_damage");
    private static final String ARMOR_TAG = "sharp_rock_armor";

    @SubscribeEvent
    public static void onPlayerTickPost(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.tickCount % 20 != 0) return;

        ItemStack stack = player.getMainHandItem();
        int level = EnchUtils.getEnchantmentLevel(SHARP_ROCK, stack, player.registryAccess());
        if (level > 0) {
            var armorAttr = player.getAttribute(Attributes.ARMOR);
            double armor = armorAttr != null ? armorAttr.getValue() : 0;
            stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY,
                    data -> data.update(tag -> tag.putDouble(ARMOR_TAG, armor)));
        } else if (stack.has(DataComponents.CUSTOM_DATA)) {
            stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY,
                    data -> data.update(tag -> tag.remove(ARMOR_TAG)));
        }
    }

    @SubscribeEvent
    public static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        if (!stack.is(Tags.Items.TOOLS_SHIELD) && !stack.is(Tags.Items.MELEE_WEAPON_TOOLS)) return;

        int level = EnchUtils.getEnchantmentLevel(SHARP_ROCK, stack, null);
        if (level <= 0) return;

        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        double armor = customData.copyTag().getDouble(ARMOR_TAG);
        if (armor <= 0) return;

        double multiplier = stack.is(Tags.Items.TOOLS_SHIELD) ? Config.SHARP_ROCK_SHIELD_DAMAGE_MULTIPLIER.get() : Config.SHARP_ROCK_WEAPON_DAMAGE_MULTIPLIER.get();

        event.addModifier(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(
                        SHARP_ROCK_ID,
                        armor * level * multiplier,
                        AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.MAINHAND
        );
    }
}
