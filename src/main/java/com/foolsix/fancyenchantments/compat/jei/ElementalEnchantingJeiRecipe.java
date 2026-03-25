package com.foolsix.fancyenchantments.compat.jei;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record ElementalEnchantingJeiRecipe(ResourceLocation id, List<ItemStack> inputs, Component usageLabel, List<Component> descriptionLines) {
}
