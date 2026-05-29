package com.foolsix.fancyenchantments.compat.jei;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.block.BlockReg;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element;
import com.foolsix.fancyenchantments.enchantment.util.ElementConditionManager;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.resource.catalyst.Catalyst;
import com.foolsix.fancyenchantments.tag.FETags;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.text.NumberFormat;

@JeiPlugin
public final class EnchantmentJeiPlugin implements IModPlugin {
    private static final String JEI_LOG_PREFIX = "[FancyEnchantments/JEI] ";

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
        try {
            IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
            registration.addRecipeCategories(new ElementalEnchantingJeiCategory(guiHelper));
        } catch (Exception exception) {
            FancyEnchantments.LOGGER.error(JEI_LOG_PREFIX + "Failed to register JEI categories.", exception);
        }
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        try {
            registration.addRecipeCatalyst(new ItemStack(BlockReg.ELEMENTAL_ENCHANTING_TABLE_ITEM.get()), ElementalEnchantingJeiCategory.RECIPE_TYPE);
        } catch (Exception exception) {
            FancyEnchantments.LOGGER.error(JEI_LOG_PREFIX + "Failed to register JEI recipe catalysts.", exception);
        }
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        try {
            registration.addRecipes(ElementalEnchantingJeiCategory.RECIPE_TYPE, this.getElementalEnchantingRecipes());
        } catch (Exception exception) {
            FancyEnchantments.LOGGER.error(JEI_LOG_PREFIX + "Failed to register elemental enchanting JEI recipes.", exception);
        }

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            return;
        }

        HolderLookup.RegistryLookup<Enchantment> enchantments = minecraft.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        for (Holder.Reference<Enchantment> enchantment : enchantments.listElements().toList()) {
            try {
                int maxLevel = enchantment.value().getMaxLevel();
                if (maxLevel <= 0) {
                    continue;
                }

                Component fullName = Enchantment.getFullname(enchantment, 1);
                String enchantmentKey = fullName.getString();
                if (enchantmentKey.isBlank()) {
                    continue;
                }

                List<Component> description = new ArrayList<>();
                String descriptionKey = enchantment.value().description().getString();
                ResourceKey<Enchantment> enchantmentKeyRef = enchantment.key();
                if (enchantmentKeyRef != null) {
                    descriptionKey = "enchantment."
                            + enchantmentKeyRef.location().getNamespace()
                            + "."
                            + enchantmentKeyRef.location().getPath()
                            + ".desc";
                }
                String descriptionText = I18n.get(descriptionKey);
                if (!descriptionKey.equals(descriptionText) && Config.JEI_ENABLE_DESCRIPTION.get()) {
                    description.add(Component.translatable(descriptionKey));
                }

                MutableComponent metaLine = Component.empty();
                if (Config.JEI_ENABLE_MAX_LEVEL.get()) {
                    metaLine.append(Component.translatable(this.getJeiTranslationKey("max_level"), maxLevel));
                }
                if (Config.JEI_ENABLE_RARITY.get()) {
                    if (!metaLine.getSiblings().isEmpty() || !metaLine.getString().isEmpty()) {
                        metaLine.append(CommonComponents.SPACE);
                    }
                    metaLine.append(Component.translatable(this.getJeiTranslationKey("rarity"), this.getRarityName(enchantment.value().getWeight())));
                }
                if (Config.JEI_ENABLE_MAX_LEVEL.get() || Config.JEI_ENABLE_RARITY.get()) {
                    description.add(metaLine);
                }
                this.appendConditionInfo(description, enchantment.key().location());

                List<ItemStack> books = new ArrayList<>();
                for (int level = 1; level <= maxLevel; ++level) {
                    books.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, level)));
                }

                if (!description.isEmpty()) {
                    registration.addIngredientInfo(books, VanillaTypes.ITEM_STACK, description.toArray(Component[]::new));
                }
            } catch (Exception exception) {
                FancyEnchantments.LOGGER.error(JEI_LOG_PREFIX + "Failed to register JEI ingredient info for enchantment {}.", enchantment, exception);
            }
        }
    }

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(FancyEnchantments.MODID, "enchantment");
    }

    private List<ElementalEnchantingJeiRecipe> getElementalEnchantingRecipes() {
        List<ElementalEnchantingJeiRecipe> recipes = new ArrayList<>();

        try {
            Catalyst.catalystDataMap.entrySet().stream()
                    .map(this::createCatalystRecipe)
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparing(recipe -> recipe.id().toString()))
                    .forEach(recipes::add);
        } catch (Exception exception) {
            FancyEnchantments.LOGGER.error(JEI_LOG_PREFIX + "Failed to build catalyst JEI recipes.", exception);
        }

        for (Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(FETags.Items.UPGRADE_MATERIALS)) {
            Item item = holder.value();
            if (item != null) {
                ElementalEnchantingJeiRecipe recipe = this.createUpgradeRecipe(item);
                if (recipe != null && recipes.stream().noneMatch(existing -> existing.id().equals(recipe.id()))) {
                    recipes.add(recipe);
                }
            }
        }
        recipes.sort(Comparator.comparing(recipe -> recipe.id().toString()));
        return recipes;
    }

    private ElementalEnchantingJeiRecipe createCatalystRecipe(Map.Entry<String, Map<Catalyst, Integer>> entry) {
        if (entry == null || entry.getKey() == null || entry.getValue() == null || entry.getValue().isEmpty()) {
            return null;
        }

        ResourceLocation itemId = ResourceLocation.tryParse(entry.getKey());
        if (itemId == null) {
            return null;
        }

        Item item = BuiltInRegistries.ITEM.get(itemId);
        ResourceLocation registryKey = BuiltInRegistries.ITEM.getKey(item);
        if (item == Items.AIR || registryKey == null) {
            return null;
        }

        List<Component> lines = new ArrayList<>();
        entry.getValue().entrySet().stream()
                .filter(dataEntry -> dataEntry.getKey() != null && dataEntry.getValue() != null)
                .sorted(Map.Entry.<Catalyst, Integer>comparingByValue().reversed())
                .forEach(dataEntry -> lines.add(Component.translatable(
                        "screen.fancyenchantments.elemental_enchanting_table.catalyst_slot.entry",
                        this.getCatalystDisplayName(dataEntry.getKey()),
                        dataEntry.getValue())));

        if (lines.isEmpty()) {
            return null;
        }

        return new ElementalEnchantingJeiRecipe(
                ResourceLocation.fromNamespaceAndPath(FancyEnchantments.MODID, "elemental_enchanting/catalyst/" + registryKey.getNamespace() + "/" + registryKey.getPath()),
                List.of(new ItemStack(item)),
                Component.translatable("jei." + FancyEnchantments.MODID + ".elemental_enchanting.usage.catalyst"),
                lines);
    }

    private ElementalEnchantingJeiRecipe createUpgradeRecipe(Item item) {
        ResourceLocation registryKey = BuiltInRegistries.ITEM.getKey(item);
        if (item == Items.AIR || registryKey == null) {
            return null;
        }

        return new ElementalEnchantingJeiRecipe(
                ResourceLocation.fromNamespaceAndPath(FancyEnchantments.MODID, "elemental_enchanting/upgrade/" + registryKey.getNamespace() + "/" + registryKey.getPath()),
                List.of(new ItemStack(item)),
                Component.translatable("jei." + FancyEnchantments.MODID + ".elemental_enchanting.usage.upgrade"),
                List.of(Component.translatable("jei." + FancyEnchantments.MODID + ".elemental_enchanting.upgrades", 2)));
    }

    private String getCatalystTranslationKey(Catalyst catalyst) {
        return switch (catalyst) {
            case AER -> "screen.fancyenchantments.elemental_enchanting_table.catalyst.aer";
            case AQUA -> "screen.fancyenchantments.elemental_enchanting_table.catalyst.aqua";
            case IGNIS -> "screen.fancyenchantments.elemental_enchanting_table.catalyst.ignis";
            case TERRA -> "screen.fancyenchantments.elemental_enchanting_table.catalyst.terra";
            case HOLY -> "screen.fancyenchantments.elemental_enchanting_table.catalyst.holy";
            case TWISTED -> "screen.fancyenchantments.elemental_enchanting_table.catalyst.twisted";
            case TREASURE -> "screen.fancyenchantments.elemental_enchanting_table.catalyst.treasure";
            case SPECIAL -> "screen.fancyenchantments.elemental_enchanting_table.catalyst.special";
        };
    }

    private String getRarityName(int weight) {
        if (weight <= 1) {
            return "very_rare";
        }
        if (weight <= 2) {
            return "rare";
        }
        if (weight <= 5) {
            return "uncommon";
        }
        return "common";
    }

    private void appendConditionInfo(List<Component> description, ResourceLocation enchantmentId) {
        int[] condition = ElementConditionManager.getCondition(enchantmentId);
        double chance = ElementConditionManager.getChance(enchantmentId);
        if (!this.hasConditionInfo(enchantmentId, condition, chance)) {
            return;
        }

        description.add(Component.translatable(this.getJeiTranslationKey("condition")));

        MutableComponent values = Component.empty();
        for (int index = 0; index < Math.min(EnchUtils.ELEMENT_COUNT, condition.length); ++index) {
            if (condition[index] <= 0 || index >= Element.values().length) {
                continue;
            }

            Element element = Element.values()[index];
            if (!values.getSiblings().isEmpty() || !values.getString().isEmpty()) {
                values.append(CommonComponents.SPACE);
            }
            values.append(this.getElementDisplayName(element))
                    .append(Component.literal(": " + condition[index]));
        }

        if (values.getSiblings().isEmpty() && values.getString().isEmpty()) {
            description.add(Component.literal("NONE"));
        } else {
            description.add(values);
        }

        description.add(Component.translatable(this.getJeiTranslationKey("probability_from_chest"), this.getPercent(chance)));
    }

    private boolean hasConditionInfo(ResourceLocation enchantmentId, int[] condition, double chance) {
        if (chance > 0.0D || ElementConditionManager.getConditionalEnchantments().contains(enchantmentId)) {
            return true;
        }

        for (int value : condition) {
            if (value > 0) {
                return true;
            }
        }
        return false;
    }

    private String getPercent(double value) {
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat.format(value);
    }

    private String getJeiTranslationKey(String key) {
        return "enchantment." + FancyEnchantments.MODID + "." + key;
    }

    private Component getElementDisplayName(Element element) {
        String name = element.name().toLowerCase(Locale.ROOT);
        String displayName = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        return Component.literal(displayName).withStyle(element.chatFormatting());
    }

    private Component getCatalystDisplayName(Catalyst catalyst) {
        return switch (catalyst) {
            case AER -> this.getElementDisplayName(Element.AER);
            case AQUA -> this.getElementDisplayName(Element.AQUA);
            case IGNIS -> this.getElementDisplayName(Element.IGNIS);
            case TERRA -> this.getElementDisplayName(Element.TERRA);
            case HOLY -> this.getElementDisplayName(Element.HOLY);
            case TWISTED -> this.getElementDisplayName(Element.TWISTED);
            case TREASURE, SPECIAL -> Component.literal(catalyst.name().toLowerCase(Locale.ROOT));
        };
    }
}
