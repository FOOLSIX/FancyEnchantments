package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

public class Empathy extends Enchantment {
    private static final ModConfig.EmpathyOptions CONFIG = FancyEnchantments.getConfig().empathyOptions;

    public Empathy() {
        super(Rarity.RARE, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean isCurse() {
        return CONFIG.isCurse;
    }

    @Override
    public boolean isTreasureOnly() {
        return CONFIG.isTreasure;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return CONFIG.level != 0;
    }

    public void throwPlayer(ArrowLooseEvent e) {
        Player player = e.getEntity();
        int level = EnchantmentHelper.getEnchantmentLevel(this, player);
        if (level > 0) {
            double f = e.getCharge() * CONFIG.shootSpeedMultiplier;
            System.out.println(f);
            Vec3 look = player.getLookAngle();
            player.push(look.x * f, look.y * f, look.z * f);
            e.setCanceled(true);
        }
    }
}
