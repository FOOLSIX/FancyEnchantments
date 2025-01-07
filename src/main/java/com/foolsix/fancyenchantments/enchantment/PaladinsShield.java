package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.HolyEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.LivingHurtEventHandler;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;
import java.util.Objects;

public class PaladinsShield extends HolyEnchantment implements LivingHurtEventHandler {
    public static final String NAME = "paladins_shield";
    private static final ModConfig.PaladinsShieldOptions CONFIG = FancyEnchantments.getConfig().paladinsShieldOptions;

    public PaladinsShield() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
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

    public void whenHurtTransDamage(LivingDamageEvent e) {
        //if the damage caused by this or other enchantment, do not handle it
        if (e.getSource().isBypassEnchantments()) return;

        LivingEntity victim = e.getEntity();
        List<Player> transTo = null;
        if (victim instanceof OwnableEntity ownable) {
            transTo = victim.level.getEntitiesOfClass(Player.class, victim.getBoundingBox().inflate(5),
                    player -> Objects.equals(ownable.getOwnerUUID(), player.getUUID())
                            && EnchantmentHelper.getEnchantmentLevel(this, player) > 0);
        } else if (victim instanceof ServerPlayer) {
            transTo = victim.level.getEntitiesOfClass(Player.class, victim.getBoundingBox().inflate(5),
                    player -> EnchantmentHelper.getEnchantmentLevel(this, player) > 0);
        }
        if (transTo != null && !transTo.isEmpty()) {
            Player player = transTo.get(0);
            float damage = e.getAmount() * CONFIG.damageTransferRatio * CONFIG.transferredDamageRatio;
            if (damage < player.getHealth() && damage < player.getMaxHealth() * CONFIG.upperLimit) {
                player.hurt(e.getSource().bypassArmor().bypassEnchantments(), damage);
                e.setAmount(e.getAmount() * (1 - CONFIG.damageTransferRatio));
            }
        }
    }

    @Override
    public int getLivingHurtPriority() {
        return MULTIPLY;
    }

    @Override
    public void handleLivingHurtEvent(LivingHurtEvent e) {
        reduceDamage(e);
    }

    public void reduceDamage(LivingHurtEvent e) {
        if (e.getEntity() instanceof Player player) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, player);
            if (level > 0) {
                e.setAmount(e.getAmount() * (1 - CONFIG.baseDamageReductionRatio * level));
            }
        }
    }
}
