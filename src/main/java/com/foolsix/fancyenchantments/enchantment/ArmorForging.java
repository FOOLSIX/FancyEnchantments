package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.LivingHurtEventHandler;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.UUID;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static net.minecraft.world.entity.LivingEntity.getEquipmentSlotForItem;

public class ArmorForging extends FEBaseEnchantment implements LivingHurtEventHandler {
    public static final String NAME = "armor_forging";
    private static final ModConfig.ArmorForgingOptions CONFIG = FancyEnchantments.getConfig().armorForgingOptions;
    private final String TAG_NAME = MODID + ":forging_value";
    private final UUID ARMOR_FORGING_HEAD_UUID = UUID.fromString("006063c0-0b72-5ec1-4688-bc3975081020");
    private final UUID ARMOR_FORGING_CHEST_UUID = UUID.fromString("29a13209-2a40-4801-bcf2-18d32402b586");
    private final UUID ARMOR_FORGING_LEGS_UUID = UUID.fromString("ecf543d3-5b74-46e0-ad27-24476a393aa0");
    private final UUID ARMOR_FORGING_FEET_UUID = UUID.fromString("0a0c3862-dd6c-4c2a-9b9a-b6f3a34cf822");



    public ArmorForging() {
        super(CONFIG, EnchantmentCategory.WEARABLE,
                new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
    }

    @Override
    public Component getFullname(int pLevel) {
        return EnchUtils.getMixedColorFullName(super.getFullname(pLevel), EnchUtils.Element.IGNIS, EnchUtils.Element.TERRA);
    }

    @Override
    public void handleLivingHurtEvent(LivingHurtEvent e) {
        hurtForging(e);
    }

    public void hurtForging(LivingHurtEvent e) {
        DamageSource source = e.getSource();
        if (source == null || source.isBypassArmor() || source.isBypassEnchantments()) return;
        if (e.getEntity() instanceof Player player) {
            for (ItemStack stack : player.getArmorSlots()) {
                int level = stack.getEnchantmentLevel(this);
                if (level > 0) {
                    CompoundTag nbt = stack.getOrCreateTag();
                    int forgingValue = nbt.getInt(TAG_NAME);
                    if (forgingValue < level * CONFIG.forgingValue) {
                        nbt.putInt(TAG_NAME, Math.min(forgingValue + (int) Math.ceil(e.getAmount()), level * CONFIG.forgingValue));
                        break;
                    }
                }
            }
        }
    }

    public void modifyArmor(ItemAttributeModifierEvent e) {
        ItemStack stack = e.getItemStack();
        int level = stack.getEnchantmentLevel(this);
        EquipmentSlot equipmentSlot = e.getSlotType();
        if (level > 0 && equipmentSlot == getEquipmentSlotForItem(stack)) {
            int forgeValue = stack.getOrCreateTag().getInt(TAG_NAME);
            e.addModifier(Attributes.ARMOR, new AttributeModifier(getUUIDBySlot(equipmentSlot), NAME, (double) forgeValue / CONFIG.armorBase, AttributeModifier.Operation.MULTIPLY_BASE));
            e.addModifier(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(getUUIDBySlot(equipmentSlot), NAME, (double) forgeValue / CONFIG.toughnessBase, AttributeModifier.Operation.MULTIPLY_BASE));
        }
    }

    private UUID getUUIDBySlot(EquipmentSlot slot) {
        UUID uid = ARMOR_FORGING_FEET_UUID;
        switch (slot) {
            case HEAD -> uid = ARMOR_FORGING_HEAD_UUID;
            case CHEST -> uid = ARMOR_FORGING_CHEST_UUID;
            case LEGS -> uid = ARMOR_FORGING_LEGS_UUID;
        }
        return uid;
    }
}
