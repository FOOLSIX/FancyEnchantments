package com.foolsix.fancyenchantments.compat.jei;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.text.NumberFormat;
import java.util.List;
import java.util.stream.IntStream;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.mojang.text2speech.Narrator.LOGGER;

//Reference JEI Enchantment Info

@JeiPlugin
public class EnchantmentJEIPlugin implements IModPlugin {
    private static final ModConfig.JEIInfoOptions CONFIG = FancyEnchantments.getConfig().jeiInfoOptions;

    @Override
    public void registerRecipes(IRecipeRegistration reg) {
        boolean escapePercents = ModList.get().getModContainerById(ModIds.MINECRAFT_ID).orElseThrow().getModInfo().getVersion().getMinorVersion() == 15;
        ForgeRegistries.ENCHANTMENTS.forEach(enchantment -> {
            String enchantmentKey = enchantment.getDescriptionId();
            StringBuilder description = new StringBuilder();
            String descriptionKey = enchantmentKey + "." + "desc";
            String descriptionTemp = I18n.get(descriptionKey);
            if (escapePercents)
                descriptionTemp = descriptionTemp.replace("%", "%%");

            if (!descriptionKey.equals(descriptionTemp)) {
                if (descriptionTemp.startsWith("Format error: ")) {
                    descriptionTemp = descriptionTemp.substring(14);
                    String warning = getWarning(enchantmentKey, descriptionKey, descriptionTemp);
                    LOGGER.warn(warning);
                }
                if (CONFIG.enableDescription)
                    description.append(descriptionTemp).append('\n');
            }

            int maxLevel = enchantment.getMaxLevel();
            if (CONFIG.enableDescription)
                description.append(I18n.get(getLangKey("max_level"), maxLevel)).append(' ');
            if (CONFIG.enableRarity)
                description.append(I18n.get(getLangKey("rarity"), enchantment.getRarity().toString()));
            if (CONFIG.enableMaxLevel || CONFIG.enableRarity) description.append('\n');
            if (enchantment instanceof FEBaseEnchantment fe && fe.isSpecialLoot()) {
                description.append(I18n.get(getLangKey("condition")));
                final int[] condition = fe.getChestGenerationCondition();
                for (int i = 0; i < EnchUtils.ELEMENT_COUNT; ++i) {
                    if (condition[i] > 0) {
                        EnchUtils.Element element = EnchUtils.Element.values()[i];
                        String name = element.toString().charAt(0) + element.toString().substring(1).toLowerCase();
                        description.append(EnchUtils.Element.getChatFormatting(element))
                                .append(name)
                                .append(": ")
                                .append(condition[i])
                                .append(" ");
                    }
                }
                description.append("\n").append(I18n.get(getLangKey("probability_from_chest"), getPercent(fe.getChestGenerationProbability())));
            }

            List<ItemStack> books = IntStream.range(1, maxLevel + 1).mapToObj(i -> {
                ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                EnchantedBookItem.addEnchantment(book, new EnchantmentInstance(enchantment, i));
                return book;
            }).toList();
            if (!description.isEmpty()) {
                reg.addIngredientInfo(books, VanillaTypes.ITEM_STACK, Component.literal(description.toString()));
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
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MODID, "enchantment");
    }

    private String getLangKey(String name) {
        return String.join(".", "enchantment", MODID, name);
    }
}
