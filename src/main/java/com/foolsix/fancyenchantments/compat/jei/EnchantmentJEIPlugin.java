package com.foolsix.fancyenchantments.compat.jei;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.block.ModBlockReg;
import com.foolsix.fancyenchantments.block.table.ElementalEnchantmentMenu;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.resource.catalyst.Catalyst;
import com.foolsix.fancyenchantments.util.ModConfig;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.IntStream;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.mojang.text2speech.Narrator.LOGGER;

//Reference JEI Enchantment Info

@JeiPlugin
public class EnchantmentJEIPlugin implements IModPlugin {
    private static final ModConfig.JEIInfoOptions CONFIG = FancyEnchantments.getConfig().jeiInfoOptions;
    private static final String JEI_LOG_PREFIX = "[FancyEnchantments/JEI] ";

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration reg) {
        try {
            IGuiHelper guiHelper = reg.getJeiHelpers().getGuiHelper();
            reg.addRecipeCategories(new ElementalEnchantingJeiCategory(guiHelper));
        } catch (Exception exception) {
            LOGGER.error(JEI_LOG_PREFIX + "Failed to register JEI categories.", exception);
        }
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration reg) {
        try {
            Item tableItem = ModBlockReg.ELEMENTAL_ENCHANTING_TABLE_ITEM.get();
            reg.addRecipeCatalyst(new ItemStack(tableItem), ElementalEnchantingJeiCategory.RECIPE_TYPE);
        } catch (Exception exception) {
            LOGGER.error(JEI_LOG_PREFIX + "Failed to register JEI recipe catalysts.", exception);
        }
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration reg) {
        try {
            reg.addRecipes(ElementalEnchantingJeiCategory.RECIPE_TYPE, this.getElementalEnchantingRecipes());
        } catch (Exception exception) {
            LOGGER.error(JEI_LOG_PREFIX + "Failed to register elemental enchanting JEI recipes.", exception);
        }

        boolean escapePercents = ModList.get().getModContainerById(ModIds.MINECRAFT_ID)
                .map(container -> container.getModInfo().getVersion().getMinorVersion() == 15)
                .orElse(false);
        ForgeRegistries.ENCHANTMENTS.getValues().forEach(enchantment -> {
            try {
                if (enchantment == null || enchantment.getMaxLevel() == 0) {
                    return;
                }
                String enchantmentKey = enchantment.getDescriptionId();
                if (enchantmentKey.isBlank()) {
                    return;
                }

                StringBuilder description = new StringBuilder();
                String descriptionKey = enchantmentKey + "." + "desc";
                String descriptionTemp = I18n.get(descriptionKey);
                if (escapePercents) {
                    descriptionTemp = descriptionTemp.replace("%", "%%");
                }

                if (!descriptionKey.equals(descriptionTemp)) {
                    if (descriptionTemp.startsWith("Format error: ")) {
                        descriptionTemp = descriptionTemp.substring(14);
                        String warning = getWarning(enchantmentKey, descriptionKey, descriptionTemp);
                        LOGGER.warn(warning);
                    }
                    if (CONFIG.enableDescription) {
                        description.append(descriptionTemp).append('\n');
                    }
                }

                int maxLevel = enchantment.getMaxLevel();
                if (CONFIG.enableMaxLevel) {
                    description.append(I18n.get(getLangKey("max_level"), maxLevel)).append(' ');
                }
                if (CONFIG.enableRarity) {
                    description.append(I18n.get(getLangKey("rarity"), enchantment.getRarity().toString()));
                }
                if (CONFIG.enableMaxLevel || CONFIG.enableRarity) {
                    description.append('\n');
                }
                if (enchantment instanceof FEBaseEnchantment fe && fe.getChestGenerationProbability() > 0) {
                    description.append(I18n.get(getLangKey("condition")));
                    final int[] condition = fe.getChestGenerationCondition();
                    StringBuilder tmp = new StringBuilder();
                    if (condition != null) {
                        for (int i = 0; i < Math.min(EnchUtils.ELEMENT_COUNT, condition.length); ++i) {
                            if (condition[i] > 0 && i < EnchUtils.Element.values().length) {
                                EnchUtils.Element element = EnchUtils.Element.values()[i];
                                String name = element.toString().charAt(0) + element.toString().substring(1).toLowerCase();
                                tmp.append(EnchUtils.Element.getChatFormatting(element))
                                        .append(name)
                                        .append(": ")
                                        .append(condition[i])
                                        .append(" ");
                            }
                        }
                    }
                    if (tmp.isEmpty()) {
                        description.append("NONE");
                    } else {
                        description.append(tmp);
                    }
                    description.append("\n").append(I18n.get(getLangKey("probability_from_chest"), getPercent(fe.getChestGenerationProbability())));
                }
                boolean hasApotheosis = ModList.get().getModContainerById("apotheosis").isPresent();
                List<ItemStack> books = IntStream.range(1, maxLevel + (hasApotheosis ? 9 : 1)).mapToObj(i -> {
                    ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                    EnchantedBookItem.addEnchantment(book, new EnchantmentInstance(enchantment, i));
                    return book;
                }).toList();
                if (!description.isEmpty()) {
                    reg.addIngredientInfo(books, VanillaTypes.ITEM_STACK, Component.literal(description.toString()));
                }
            } catch (Exception exception) {
                LOGGER.error(JEI_LOG_PREFIX + "Failed to register JEI ingredient info for enchantment {}.", enchantment, exception);
            }
        });
    }

    private static String getWarning(String enchantmentKey, String descriptionKey, String descriptionTemp) {
        String warning = String.format("""
                        A formatting error occurred while getting the description for '%s'.
                        The lang key '%s' returned the following errant description string:
                        [%s]""",
                enchantmentKey, descriptionKey, descriptionTemp);
        if (descriptionTemp.contains("%")) {
            warning += """

                    Since the description contains a '%' symbol, it's possible that it was not properly escaped.
                    Please ensure that all descriptions contain '%%', rather than '%'.""";
        }
        return warning;
    }

    private static String getPercent(double data) {
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat.format(data);
    }

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(MODID, "enchantment");
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
            LOGGER.error(JEI_LOG_PREFIX + "Failed to build catalyst JEI recipes.", exception);
        }

        var tags = ForgeRegistries.ITEMS.tags();

        if (tags == null) {
            return recipes;
        }
        tags.getTag(ElementalEnchantmentMenu.UPGRADE_MATERIALS)
                .stream()
                .filter(Objects::nonNull)
                .distinct()
                .sorted(Comparator.comparing(item -> {
                    ResourceLocation key = ForgeRegistries.ITEMS.getKey(item);
                    return key == null ? "" : key.toString();
                }))
                .map(this::createUpgradeRecipe)
                .filter(Objects::nonNull)
                .forEach(recipes::add);
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
        Item item = ForgeRegistries.ITEMS.getValue(itemId);
        ResourceLocation registryKey = ForgeRegistries.ITEMS.getKey(item);
        if (item == null || registryKey == null) {
            return null;
        }

        List<Component> lines = new ArrayList<>();
        entry.getValue().entrySet().stream()
                .filter(dataEntry -> dataEntry.getKey() != null && dataEntry.getValue() != null)
                .sorted(Map.Entry.<Catalyst, Integer>comparingByValue().reversed())
                .forEach(dataEntry -> lines.add(Component.translatable(
                        "screen.fancyenchantments.elemental_enchanting_table.catalyst_slot.entry",
                        Component.translatable(this.getCatalystTranslationKey(dataEntry.getKey())),
                        dataEntry.getValue())));

        if (lines.isEmpty()) {
            return null;
        }

        return new ElementalEnchantingJeiRecipe(
                new ResourceLocation(MODID, "elemental_enchanting/catalyst/" + registryKey.getNamespace() + "/" + registryKey.getPath()),
                List.of(new ItemStack(item)),
                Component.translatable("jei." + MODID + ".elemental_enchanting.usage.catalyst"),
                lines);
    }

    private ElementalEnchantingJeiRecipe createUpgradeRecipe(Item item) {
        if (item == null) {
            return null;
        }
        ResourceLocation registryKey = ForgeRegistries.ITEMS.getKey(item);
        if (registryKey == null) {
            return null;
        }
        return new ElementalEnchantingJeiRecipe(
                new ResourceLocation(MODID, "elemental_enchanting/upgrade/" + registryKey.getNamespace() + "/" + registryKey.getPath()),
                List.of(new ItemStack(item)),
                Component.translatable("jei." + MODID + ".elemental_enchanting.usage.upgrade"),
                List.of(Component.translatable("jei." + MODID + ".elemental_enchanting.upgrades", 2)));
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

    private String getLangKey(String name) {
        return String.join(".", "enchantment", MODID, name);
    }
}
