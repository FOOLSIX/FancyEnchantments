package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ToolActions;

import static com.foolsix.fancyenchantments.effect.EffectReg.CRIT_RATE_BOOST;


public class Counterattack extends FEBaseEnchantment {
    public static final String NAME = "counterattack";
    private static final ModConfig.CounterattackOptions CONFIG = FancyEnchantments.getConfig().counterattackOptions;

    public Counterattack() {
        super(CONFIG, EnchantmentCategory.WEAPON,
                new EquipmentSlot[]{EquipmentSlot.OFFHAND, EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int pLevel) {
        return 5 + pLevel * 5;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 10;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.canPerformAction(ToolActions.SHIELD_BLOCK);
    }

    public void getBuff(Player player) {
        int level = EnchantmentHelper.getEnchantmentLevel(this, player);
        if (player.isBlocking() && level > 0) {
            player.addEffect(new MobEffectInstance(CRIT_RATE_BOOST.get(), 30, level));
        }
    }

}
