package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TwistedEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.EATER_OF_SOULS;

public class EaterOfSouls extends TwistedEnchantment {
    public static final String NAME = "eater_of_souls";
    private static final ModConfig.EaterOfSoulsOptions CONFIG = FancyEnchantments.getConfig().eaterOfSoulsOptions;
    private static final String KILL_COUNT = MODID + " eater_of_souls_killcount";

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
            return Math.min(CONFIG.damageMultiplier * (float) Math.sqrt(enchantedItem.getOrCreateTag().getInt(KILL_COUNT)) * level, CONFIG.cap);
        }
        return 0;
    }

    public void tooltip(ItemTooltipEvent e) {
        ItemStack itemStack = e.getItemStack();
        if (itemStack.getEnchantmentLevel(EATER_OF_SOULS.get()) > 0) {
            CompoundTag nbt = itemStack.getOrCreateTag();
            if (nbt.contains(KILL_COUNT)) {
                e.getToolTip().add(Component.translatable("Kill Count:").append(String.valueOf(nbt.getInt(KILL_COUNT))).withStyle(ChatFormatting.DARK_PURPLE));
            }
        }
    }
}
