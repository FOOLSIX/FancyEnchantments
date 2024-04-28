package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class EaterOfSouls extends FEBaseEnchantment {
    private static final ModConfig.EaterOfSoulsOptions CONFIG = FancyEnchantments.getConfig().eaterOfSoulsOptions;

    public EaterOfSouls() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public Component getFullname(int level) {
        return ((MutableComponent) super.getFullname(level)).withStyle(ChatFormatting.DARK_PURPLE);
    }

    public void killcount(LivingDeathEvent e) {
        if (e.getSource().getEntity() instanceof Player player) {
            ItemStack tool = player.getMainHandItem();
            if (tool.getEnchantmentLevel(this) > 0) {
                e.getEntity().skipDropExperience();
                CompoundTag nbt = tool.getOrCreateTag();
                if (nbt.contains("eater_of_souls_killcount")) {
                    nbt.putInt("eater_of_souls_killcount", nbt.getInt("eater_of_souls_killcount") + 1);
                } else {
                    nbt.putInt("eater_of_souls_killcount", 1);
                }
            }
            if (player.getOffhandItem().getEnchantmentLevel(this) > 0) {
                e.getEntity().skipDropExperience();
            }
        }
    }

    @Override
    public float getDamageBonus(int level, MobType mobType, ItemStack enchantedItem) {
        if (level > 0) {
            CompoundTag nbt = enchantedItem.getTag();
            if (nbt != null && nbt.contains("eater_of_souls_killcount")) {
                return (float) (CONFIG.damageMultiplier * Math.sqrt(nbt.getInt("eater_of_souls_killcount")));
            }
        }
        return 0;
    }
}
