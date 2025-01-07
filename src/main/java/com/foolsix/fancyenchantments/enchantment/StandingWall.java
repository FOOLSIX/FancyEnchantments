package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TerraEnchantment;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import static net.minecraftforge.common.ToolActions.SHIELD_BLOCK;

;

public class StandingWall extends TerraEnchantment {
    private static final ModConfig.StandingWallOptions CONFIG = FancyEnchantments.getConfig().standingWallOptions;

    public StandingWall() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.canPerformAction(SHIELD_BLOCK);
    }

    public void getAttacked(LivingAttackEvent e) {
        if (e.getEntity() instanceof Player player && e.getSource() != null && player.isBlocking()) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, player);
            Entity attacker = e.getSource().getEntity();
            if (level > 0 && attacker != null && EnchUtils.canBlock(player, attacker.position())) {
                player.getUseItem().hurtAndBreak(2, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
                player.level.playSound(null, player.blockPosition(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0F, 1.5F + player.level.random.nextFloat() * 0.4F);
                e.setCanceled(true);
            }
        }
    }
}
