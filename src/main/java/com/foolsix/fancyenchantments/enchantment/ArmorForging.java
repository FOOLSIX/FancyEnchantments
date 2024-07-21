package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.tags.DamageTypeTags;
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

public class ArmorForging extends FEBaseEnchantment {
    public static final String NAME = "armor_forging";
    private static final ModConfig.ArmorForgingOptions CONFIG = FancyEnchantments.getConfig().armorForgingOptions;
    private final String TAG_NAME = MODID + ":forging_value";
    private final UUID ARMOR_FORGING_UUID = UUID.fromString("006063c0-0b72-5ec1-4688-bc3975081020");

    public ArmorForging() {
        super(CONFIG, EnchantmentCategory.WEARABLE,
                new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
    }

    @Override
    public Component getFullname(int level) {
        Component name = super.getFullname(level);
        if (name instanceof MutableComponent mutableName) {
            mutableName.setStyle(Style.EMPTY.withColor(EnchUtils.gradualColor(16755200, 5635925, 120)));
        }
        return name;
    }

    public void hurtForging(LivingHurtEvent e) {
        DamageSource source = e.getSource();
        if (source == null || source.is(DamageTypeTags.BYPASSES_ARMOR) || source.is(DamageTypeTags.BYPASSES_ENCHANTMENTS))
            return;
        if (e.getEntity() instanceof Player player) {
            for (ItemStack stack : player.getArmorSlots()) {
                int level = stack.getEnchantmentLevel(this);
                if (level > 0) {
                    CompoundTag nbt = stack.getOrCreateTag();
                    int forgingValue = nbt.getInt(TAG_NAME);
                    if (forgingValue < level * CONFIG.forgingValue){
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
        if (level > 0 && e.getSlotType() == getEquipmentSlotForItem(stack)) {
            int forgeValue = stack.getOrCreateTag().getInt(TAG_NAME);
            e.addModifier(Attributes.ARMOR, new AttributeModifier(ARMOR_FORGING_UUID, NAME, (double) forgeValue / CONFIG.armorBase, AttributeModifier.Operation.MULTIPLY_BASE));
            e.addModifier(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_FORGING_UUID, NAME, (double) forgeValue / CONFIG.toughnessBase, AttributeModifier.Operation.MULTIPLY_BASE));

        }
    }
}