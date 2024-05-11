package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TwistedEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class EaterOfSouls extends TwistedEnchantment {
    private static final ModConfig.EaterOfSoulsOptions CONFIG = FancyEnchantments.getConfig().eaterOfSoulsOptions;
    private static final String KILL_COUNT = "eater_of_souls_killcount";

    public EaterOfSouls() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public void killcount(LivingDeathEvent e) {
        if (e.getSource().getEntity() instanceof Player player) {
            ItemStack tool = player.getMainHandItem();
            //On main hand to get kill count
            if (tool.getEnchantmentLevel(this) > 0) {
                e.getEntity().skipDropExperience();
                CompoundTag nbt = tool.getOrCreateTag();
                nbt.putInt(KILL_COUNT, nbt.getInt(KILL_COUNT) + 1);
            }
            //On offhand still cancel drop experience
            if (player.getOffhandItem().getEnchantmentLevel(this) > 0) {
                e.getEntity().skipDropExperience();
            }
        }
    }

    @Override
    public float getDamageBonus(int level, MobType mobType, ItemStack enchantedItem) {
        if (level > 0) {
            return CONFIG.damageMultiplier * enchantedItem.getOrCreateTag().getInt(KILL_COUNT);
        }
        return 0;
    }
}
