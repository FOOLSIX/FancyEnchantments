package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AerEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.TickEvent;

import java.util.UUID;

import static com.foolsix.fancyenchantments.enchantment.util.EnchUtils.MOD_NAME_PREFIX;


public class Floating extends AerEnchantment {
    public static final ModConfig.FloatingOptions CONFIG = FancyEnchantments.getConfig().floatingOptions;
    public static final String NAME = "floating";
    public static final UUID FLOATING_UUID = UUID.fromString("0c8df40f-fdf1-41cc-a3ea-e1477459fce1");

    public Floating() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    public void damageReduce(TickEvent.PlayerTickEvent e) {
        Player player = e.player;
        AttributeInstance damageAttr = player.getAttribute(Attributes.ATTACK_DAMAGE);
        if (damageAttr != null)
            damageAttr.removeModifier(FLOATING_UUID);
        if (player.getMainHandItem().getEnchantmentLevel(this) > 0) {
            if (player.getDeltaMovement().x == 0.0 && player.getDeltaMovement().z == 0.0
                    && (player.isOnGround() || player.getDeltaMovement().y == 0.0)) {
                if (damageAttr != null) {
                    damageAttr.addTransientModifier(new AttributeModifier(FLOATING_UUID,
                            MOD_NAME_PREFIX + NAME,
                            -CONFIG.damageReducer,
                            AttributeModifier.Operation.MULTIPLY_TOTAL));
                }
            }
        }
    }
}
