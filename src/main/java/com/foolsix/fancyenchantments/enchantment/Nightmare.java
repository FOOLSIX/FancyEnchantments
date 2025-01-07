package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TwistedEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;

public class Nightmare extends TwistedEnchantment {
    private static final ModConfig.NightmareOptions CONFIG = FancyEnchantments.getConfig().nightmareOptions;

    public Nightmare() {
        super(CONFIG, EnchantmentCategory.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD});
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    public void makeExplosion(PlayerSleepInBedEvent e) {
        Player player = e.getEntity();
        int level = EnchantmentHelper.getEnchantmentLevel(this, player);
        if (level > 0) {
            BlockPos position = e.getPos();
            player.level().explode(null, player.damageSources().badRespawnPointExplosion(position.getCenter()), null, position.getX(), position.getY(), position.getZ(), level * CONFIG.explodeRadiusMultiplier, false, Level.ExplosionInteraction.BLOCK);
        }
    }
}
