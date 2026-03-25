package com.foolsix.fancyenchantments.compat.jei;

import com.foolsix.fancyenchantments.block.ModBlockReg;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ElementalEnchantingJeiCategory implements IRecipeCategory<ElementalEnchantingJeiRecipe> {
    public static final RecipeType<ElementalEnchantingJeiRecipe> RECIPE_TYPE =
            RecipeType.create(com.foolsix.fancyenchantments.FancyEnchantments.MODID, "elemental_enchanting", ElementalEnchantingJeiRecipe.class);

    private static final int WIDTH = 176;
    private static final int HEIGHT = 70;
    private static final int INPUT_X = 2;
    private static final int TABLE_X = 22;
    private static final int SLOT_Y = 24;
    private static final int ARROW_X = 43;
    private static final int TEXT_X = 56;
    private static final int TEXT_Y = 8;
    private static final int TEXT_WIDTH = 116;
    private static final int TEXT_HEIGHT = 50;
    private static final int COLUMN_GAP = 6;

    private final IDrawable background;
    private final IDrawable icon;

    public ElementalEnchantingJeiCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(WIDTH, HEIGHT);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlockReg.ELEMENTAL_ENCHANTING_TABLE_ITEM.get()));
    }

    @Override
    public @NotNull RecipeType<ElementalEnchantingJeiRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.fancyenchantments.elemental_enchanting_table");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ElementalEnchantingJeiRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, INPUT_X, SLOT_Y)
                .addIngredients(VanillaTypes.ITEM_STACK, recipe.inputs());
        builder.addSlot(RecipeIngredientRole.CATALYST, TABLE_X, SLOT_Y)
                .addItemStack(new ItemStack(ModBlockReg.ELEMENTAL_ENCHANTING_TABLE_ITEM.get()));
    }

    @Override
    public void draw(ElementalEnchantingJeiRecipe recipe, mezz.jei.api.gui.ingredient.@NotNull IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;
        guiGraphics.drawString(font, ">", ARROW_X, 29, 0x707070, false);
        guiGraphics.drawString(font, recipe.usageLabel(), TEXT_X, TEXT_Y, 0x303030, false);

        int linesPerColumn = Math.max(1, TEXT_HEIGHT / 10);
        int columnWidth = (TEXT_WIDTH - COLUMN_GAP) / 2;
        int index = 0;
        for (Component line : recipe.descriptionLines()) {
            int column = index / linesPerColumn;
            int row = index % linesPerColumn;
            int x = TEXT_X + column * (columnWidth + COLUMN_GAP);
            int y = TEXT_Y + 12 + row * 10;
            guiGraphics.drawString(font, font.substrByWidth(line, columnWidth).getString(), x, y, 0x404040, false);
            index++;
        }
    }
}
