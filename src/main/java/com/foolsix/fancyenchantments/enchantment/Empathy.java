package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

public class Empathy extends FEBaseEnchantment {
    private static final ModConfig.EmpathyOptions CONFIG = FancyEnchantments.getConfig().empathyOptions;

    public Empathy() {
        super(CONFIG, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    public void throwPlayer(ArrowLooseEvent e) {
        Player player = e.getEntity();
        int level = e.getEntity().getUseItem().getEnchantmentLevel(this);
        if (level > 0) {
            double f = e.getCharge() * CONFIG.shootPowerMultiplier;
            Vec3 look = player.getLookAngle();
            player.push(look.x * f, look.y * f, look.z * f);
            player.getUseItem().hurtAndBreak(1, player, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
            e.setCanceled(true);
        }
    }
}
