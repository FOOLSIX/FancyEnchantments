package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.HolyEnchantment;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class Purifying extends HolyEnchantment {
    private static final ModConfig.PurifyingOptions CONFIG = FancyEnchantments.getConfig().purifyingOptions;

    public Purifying() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int pLevel) {
        return 20 + pLevel * 2;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 50;
    }

    @Override
    protected boolean checkCompatibility(Enchantment pOther) {
        return super.checkCompatibility(pOther) && pOther != Enchantments.SMITE;
    }

    public float getDamageBonus(int level, MobType mobType, ItemStack enchantedItem) {
        if (mobType == MobType.UNDEAD)
            return CONFIG.addon * level;
        return 0;
    }


    public void purify(LivingDeathEvent e) {
        if (e.getSource().getEntity() instanceof ServerPlayer player
                && EnchantmentHelper.getEnchantmentLevel(this, player) > 0
                && e.getEntity() instanceof ZombieVillager zombie) {

            EnchUtils.generateSimpleParticleAroundEntity(zombie, ParticleTypes.HAPPY_VILLAGER);
            zombie.setHealth(zombie.getMaxHealth());
            zombie.startConverting(player.getUUID(), 0);
        }
    }


}
