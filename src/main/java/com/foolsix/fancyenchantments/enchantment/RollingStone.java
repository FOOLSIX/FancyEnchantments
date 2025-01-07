package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TerraEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.LivingHurtEventHandler;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public class RollingStone extends TerraEnchantment implements LivingHurtEventHandler {
    private static final ModConfig.RollingStoneOptions CONFIG = FancyEnchantments.getConfig().rollingStoneOptions;

    public RollingStone() {
        super(CONFIG, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public int getMinCost(int pLevel) {
        return 1 + pLevel * 8;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 45;
    }

    public void dealDamageWhileSprinting(TickEvent.PlayerTickEvent e) {
        Player player = e.player;
        int level = EnchantmentHelper.getEnchantmentLevel(this, player);
        if (player.isSprinting() && level > 0 && player.level() instanceof ServerLevel world) {
            List<Entity> entities = world.getEntities(player, player.getBoundingBox().inflate(0.3, 0.3, 0.3));
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity monster && EnchUtils.isHostileToLivingEntity(monster, player)) {
                    float v = player.getSpeed();
                    entity.hurt(player.damageSources().playerAttack(player), (level * CONFIG.damageMultiplier * v));
                    Vec3 pushAngel = new Vec3(monster.getX() - player.getX(), 0, monster.getZ() - player.getZ());
                    entity.push(pushAngel.x * v * 2, 0.2, pushAngel.z * v * 2);
                }
            }
        }
    }

    @Override
    public int getLivingHurtPriority() {
        return MULTIPLY;
    }

    @Override
    public void handleLivingHurtEvent(LivingHurtEvent e) {
        reduceDamageTakenWhileSprinting(e);
    }

    public void reduceDamageTakenWhileSprinting(LivingHurtEvent e) {
        int level = EnchantmentHelper.getEnchantmentLevel(this, e.getEntity());
        if (level > 0) {
            e.setAmount((e.getAmount() * Math.max(1 - CONFIG.damageReducer * level, CONFIG.lowerLimit)));
        }
    }
}
