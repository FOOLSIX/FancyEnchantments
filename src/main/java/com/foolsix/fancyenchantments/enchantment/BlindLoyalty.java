package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;

import java.util.Optional;

public class BlindLoyalty extends FEBaseEnchantment {
    private static final ModConfig.BlindLoyaltyOptions CONFIG = FancyEnchantments.getConfig().blindLoyaltyOptions;
    private final String TAG_NAME = "fe_blind_loyalty";
    public BlindLoyalty() {
        super(CONFIG, EnchantmentCategory.VANISHABLE, new EquipmentSlot[]{EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS,EquipmentSlot.FEET,EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxCost(int pLevel) {
        return 35;
    }

    public void addTag(LivingEquipmentChangeEvent e) {
        if (e.getEntity() instanceof Player && e.getTo().getEnchantmentLevel(this) > 0) {
            e.getTo().getOrCreateTag().putUUID(TAG_NAME, e.getEntity().getUUID());
        }
    }

    public void getBack(EntityJoinLevelEvent e) {
        if (e.getEntity() instanceof ItemEntity itemEntity && itemEntity.level instanceof ServerLevel world) {
            ItemStack stack = itemEntity.getItem();
            if (stack.isEmpty()) return;
            if (stack.getEnchantmentLevel(this) > 0) {
                Optional<Player> player = Optional.ofNullable(world.getPlayerByUUID(stack.getOrCreateTag().getUUID(TAG_NAME)));
                player.ifPresent(p -> {
                    if (p.addItem(stack)) {
                        itemEntity.remove(Entity.RemovalReason.DISCARDED);
                    }
                });
            }
        }
    }
}
