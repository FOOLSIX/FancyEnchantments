package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TerraEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public class RollingStone extends TerraEnchantment {
    private static final ModConfig.RollingStoneOptions CONFIG = FancyEnchantments.getConfig().rollingStoneOptions;

    public RollingStone() {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public int getMaxLevel() {
        return CONFIG.level;
    }

    @Override
    public int getMinCost(int pLevel) {
        return 1 + pLevel * 8;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 45;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return CONFIG.level != 0;
    }

    public void dealDamageWhileSprinting(TickEvent.PlayerTickEvent e) {
        Player player = e.player;
        int level = EnchantmentHelper.getEnchantmentLevel(this, player);
        if (player.isSprinting() && level > 0 && player.level instanceof ServerLevel world) {
            List<Entity> entities = world.getEntities(player, player.getBoundingBox().expandTowards(0.3, 0.3, 0.3));
            for (Entity entity : entities) {
                if (entity instanceof Monster monster) {
                    if (entity instanceof NeutralMob neutralMob && !neutralMob.isAngryAt(player)) {
                        continue;
                    }
                    float v = player.getSpeed();
                    entity.hurt(DamageSource.GENERIC, (float) (level * CONFIG.damageMultiplier + v * CONFIG.damageBonusMultiplier));
                    Vec3 pushAngel = new Vec3(monster.getX() - player.getX(), 0, monster.getZ() - player.getZ());
                    entity.push(pushAngel.x * v * 2, 0.2, pushAngel.z * 2);
                }
            }
        }
    }

    public void reduceDamageTakenWhileSprinting(LivingHurtEvent e) {
        int level = EnchantmentHelper.getEnchantmentLevel(this, e.getEntity());
        if (level > 0) {
            e.setAmount((e.getAmount() * (float) (1 - CONFIG.damageReducer * level)));
        }
    }
}
