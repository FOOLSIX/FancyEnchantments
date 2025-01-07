package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TwistedEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.HashSet;

public class Hungry extends TwistedEnchantment {
    private static final ModConfig.HungryOptions CONFIG = FancyEnchantments.getConfig().hungryOptions;

    public Hungry() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int pLevel) {
        return 3 + pLevel * 5;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 10;
    }

    public void dropFoods(LivingDropsEvent e) {
        if (e.getSource().getEntity() instanceof LivingEntity living) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, living);
            if (level > 0) {
                HashSet<ItemEntity> set = new HashSet<>();
                for (var item : e.getDrops()) {
                    if (item.getItem().isEdible()) {
                        for (int i = 0; i < level; ++i)
                            if (Math.random() < CONFIG.probability * level)
                                set.add(item.copy());
                    }
                }
                e.getDrops().addAll(set);
            }
        }
    }
}
