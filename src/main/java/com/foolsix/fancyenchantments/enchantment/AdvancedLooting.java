package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LootingLevelEvent;

public class AdvancedLooting extends Enchantment {
    private static final ModConfig.AdvancedLootingOptions CONFIG = FancyEnchantments.getConfig().advancedLootingOptions;

    public AdvancedLooting() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return CONFIG.level;
    }

    @Override
    public int getMinCost(int pLevel) {
        return 10 + pLevel * 10;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 10;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return CONFIG.level != 0;
    }

    @Override
    public boolean checkCompatibility(Enchantment pEnch) {
        return pEnch != Enchantments.MOB_LOOTING && super.checkCompatibility(pEnch);
    }

    public void lootingHandle(LootingLevelEvent e, Player player) {
        int level = player.getMainHandItem().getEnchantmentLevel(this);
        if (level > 0) {
            int lootingLevel = e.getLootingLevel();
            lootingLevel += CONFIG.lootingLevelMultiplier * level;
            if (Math.random() < ((double) level / (level + 5)))
                lootingLevel += level;
            e.setLootingLevel(lootingLevel);
        }
    }
}
