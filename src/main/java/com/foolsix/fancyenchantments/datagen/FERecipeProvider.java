package com.foolsix.fancyenchantments.datagen;

import com.foolsix.fancyenchantments.block.BlockReg;
import com.foolsix.fancyenchantments.item.ModItemReg;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class FERecipeProvider extends RecipeProvider {

    public FERecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemReg.AER_CATALYST.get())
                .pattern("LFL")
                .pattern("FEF")
                .pattern("LFL")
                .define('L', Items.LAPIS_LAZULI)
                .define('F', Items.FEATHER)
                .define('E', Items.ENDER_PEARL)
                .unlockedBy("has_ender_pearl", has(Items.ENDER_PEARL))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemReg.AQUA_CATALYST.get())
                .pattern("LPL")
                .pattern("PEP")
                .pattern("LPL")
                .define('L', Items.LAPIS_LAZULI)
                .define('P', Items.PRISMARINE_CRYSTALS)
                .define('E', Items.ENDER_PEARL)
                .unlockedBy("has_ender_pearl", has(Items.ENDER_PEARL))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemReg.HOLY_CATALYST.get())
                .pattern("LGL")
                .pattern("GEG")
                .pattern("LGL")
                .define('L', Items.LAPIS_LAZULI)
                .define('G', Items.GLOWSTONE_DUST)
                .define('E', Items.ENDER_PEARL)
                .unlockedBy("has_ender_pearl", has(Items.ENDER_PEARL))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemReg.IGNIS_CATALYST.get())
                .pattern("LBL")
                .pattern("BEB")
                .pattern("LBL")
                .define('L', Items.LAPIS_LAZULI)
                .define('B', Items.BLAZE_ROD)
                .define('E', Items.ENDER_PEARL)
                .unlockedBy("has_ender_pearl", has(Items.ENDER_PEARL))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemReg.TERRA_CATALYST.get())
                .pattern("LDL")
                .pattern("DED")
                .pattern("LDL")
                .define('L', Items.LAPIS_LAZULI)
                .define('D', Items.DIAMOND)
                .define('E', Items.ENDER_PEARL)
                .unlockedBy("has_ender_pearl", has(Items.ENDER_PEARL))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemReg.TWISTED_CATALYST.get())
                .pattern("LWL")
                .pattern("WEW")
                .pattern("LWL")
                .define('L', Items.LAPIS_LAZULI)
                .define('W', Items.WITHER_SKELETON_SKULL)
                .define('E', Items.ENDER_PEARL)
                .unlockedBy("has_ender_pearl", has(Items.ENDER_PEARL))
                .save(output);


        ShapedRecipeBuilder.shaped(
                        RecipeCategory.DECORATIONS,
                        BlockReg.ELEMENTAL_ENCHANTING_TABLE.get()
                )
                .pattern("APC")
                .pattern("BED")
                .pattern("OOO")
                .define('A', ModItemReg.AER_CATALYST.get())
                .define('B', ModItemReg.AQUA_CATALYST.get())
                .define('C', ModItemReg.IGNIS_CATALYST.get())
                .define('D', ModItemReg.TERRA_CATALYST.get())
                .define('P', Items.ENDER_PEARL)
                .define('E', Items.ENCHANTING_TABLE)
                .define('O', Items.OBSIDIAN)
                .unlockedBy(
                        "has_enchanting_table",
                        has(Items.ENCHANTING_TABLE)
                )
                .save(output);
    }
}
