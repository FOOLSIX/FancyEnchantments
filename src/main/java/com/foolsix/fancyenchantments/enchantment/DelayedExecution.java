package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.effect.EffectReg;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class DelayedExecution extends FEBaseEnchantment {
    private static final ModConfig.DelayedExecutionOptions CONFIG = FancyEnchantments.getConfig().delayedExecutionOptions;

    public DelayedExecution() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack) {
        return stack.getItem() instanceof AxeItem;
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int level) {
        if (pTarget instanceof LivingEntity victim && pAttacker instanceof Player player) {
            if (player.getAttackStrengthScale(0.5F) > 0.95) {
                MobEffectInstance instance = victim.getEffect(EffectReg.PRISON_CAGE.get());
                if (instance == null)
                    victim.addEffect(new MobEffectInstance(EffectReg.PRISON_CAGE.get(), CONFIG.duration * 20, level - 1));
            }
        }
    }

}
