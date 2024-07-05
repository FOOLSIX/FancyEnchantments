package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TerraEnchantment;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;

import java.util.List;

public class FallingStone extends TerraEnchantment {
    public static final String NAME = "falling_stone";
    private static final ModConfig.FallingStoneOptions CONFIG = FancyEnchantments.getConfig().fallingStoneOptions;

    public FallingStone() {
        super(CONFIG, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public int getMinCost(int pLevel) {
        return 5 + pLevel * 5;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 10;
    }

    public void doFallingDamage(TickEvent.PlayerTickEvent e) {
        Player player = e.player;
        int level = EnchantmentHelper.getEnchantmentLevel(this, player);
        if (!player.onGround() && player.fallDistance > 2) {
            List<Entity> entities = player.level().getEntities(player, player.getBoundingBox().inflate(0.3 * level, 0.3, 0.3 * level));
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity monster && EnchUtils.isHostileToLivingEntity(monster, player)) {
                    float fallDist = player.fallDistance;
                    monster.hurt(player.damageSources().playerAttack(player), fallDist * (1 + CONFIG.damageMultiplier * level));
                    Vec3 pushAngel = new Vec3(monster.getX() - player.getX(), 0, monster.getZ() - player.getZ());
                    entity.push(pushAngel.x, 0.1, pushAngel.z);
                    player.fallDistance = 0.0f;
                }
            }
        }
    }
}
