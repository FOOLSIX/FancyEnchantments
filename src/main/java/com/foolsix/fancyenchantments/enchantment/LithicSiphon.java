package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TerraEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class LithicSiphon extends TerraEnchantment {
    private static final ModConfig.LithicSiphonOptions CONFIG = FancyEnchantments.getConfig().lithicSiphonOptions;
    public LithicSiphon() {
        super(CONFIG, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public void blockBreak(BlockEvent.BreakEvent e) {
        Player player = e.getPlayer();
        ItemStack stack = player.getMainHandItem();
        int fortuneLevel = stack.getEnchantmentLevel(Enchantments.BLOCK_FORTUNE);
        int level = stack.getEnchantmentLevel(this);
        if (level > 0 && e.getState().is(Tags.Blocks.STONE) && Math.random() <= CONFIG.probabilityPerLevel * level) {
            ItemStack newDrop = new ItemStack(ForgeRegistries.ITEMS.tags().getTag(Tags.Items.RAW_MATERIALS).getRandomElement(player.getRandom()).orElse(Items.AIR));
            int num = 1;
            if (fortuneLevel > 0) {
                double chance = (double) 1 / (fortuneLevel + 2);
                num += Math.max((int) (Math.random() / chance - 1), 0);
            }
            for (int i = 0; i < num; ++i) {
                Block.popResource(player.level(), e.getPos(), newDrop);
            }
        }
    }
}
