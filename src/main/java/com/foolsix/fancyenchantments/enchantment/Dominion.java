package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class Dominion extends FEBaseEnchantment {
    private static final ModConfig.DominionOptions CONFIG = FancyEnchantments.getConfig().dominionOptions;

    public Dominion() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public Component getFullname(int level) {
        Component name = super.getFullname(level);
        if (name instanceof MutableComponent mutableName) {
            mutableName.setStyle(Style.EMPTY.withColor(Minecraft.getInstance().level != null ? Mth.hsvToRgb((Minecraft.getInstance().level.getGameTime() % 360) / 360f, 1, 1) : 0x0));
        }

        return name;
    }

    @Override
    public float getDamageBonus(int level, MobType mobType, ItemStack enchantedItem) {
        int levelSum = 0;
        for (int l : enchantedItem.getAllEnchantments().values()) {
            levelSum += l;
        }
        return levelSum * CONFIG.damageMultiplier;
    }
}
