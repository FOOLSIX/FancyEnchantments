package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TwistedEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.LootBonusEnchantment;
import net.minecraftforge.event.entity.living.LootingLevelEvent;

public class GreedSupremeLooting extends TwistedEnchantment {
    private static final ModConfig.GreedySupremeLootingOptions CONFIG = FancyEnchantments.getConfig().greedySupremeLootingOptions;

    public GreedSupremeLooting() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem() instanceof AxeItem || super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean checkCompatibility(Enchantment pEnch) {
        return !(pEnch instanceof LootBonusEnchantment) && pEnch.isCompatibleWith(Enchantments.MOB_LOOTING) && super.checkCompatibility(pEnch);
    }

    public void lootingHandle(LootingLevelEvent e, Player player) {
        int level = player.getMainHandItem().getEnchantmentLevel(this);
        if (level > 0) {
            int lootingLevel = e.getLootingLevel();
            lootingLevel += CONFIG.lootLevelMultiplier * level;
            if (Math.random() < CONFIG.probabilityOfDoubling)
                lootingLevel += CONFIG.lootLevelMultiplier * level;
            e.setLootingLevel(lootingLevel);
        }
    }
}
