package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;

public class WindFireWheels extends FEBaseEnchantment {
    private static final ModConfig.WindFireWheelsOptions CONFIG = FancyEnchantments.getConfig().windFireWheelsOptions;

    public WindFireWheels() {
        super(CONFIG, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public Component getFullname(int pLevel) {
        return EnchUtils.getMixedColorFullName(super.getFullname(pLevel), EnchUtils.Element.AER, EnchUtils.Element.IGNIS);
    }

    @OnlyIn(Dist.CLIENT)
    public void SprintAndFly(TickEvent.PlayerTickEvent e) {
        Player player = e.player;
        int level = EnchantmentHelper.getEnchantmentLevel(this, player);
        if (level > 0 && player.isSprinting() && !player.isOnGround()) {
            Vec3 movement = player.getDeltaMovement();
            double y = Minecraft.getInstance().options.keyJump.isDown() ? 0.3 : 0;
            Vec3 look = player.getLookAngle();
            player.setDeltaMovement(movement.x(), y, movement.z());
            player.push(look.x * CONFIG.speedMultiplier, 0, look.z * CONFIG.speedMultiplier);
        }
    }

    public void avoidFallDamage(TickEvent.PlayerTickEvent e) {
        Player player = e.player;
        int level = EnchantmentHelper.getEnchantmentLevel(this, player);
        if (level > 0 && player.isSprinting() && !player.isOnGround()) {
            player.resetFallDistance();
            EnchUtils.generateSimpleParticleAroundEntity(player, ParticleTypes.FLAME, 1, 0, 0.1, 0, 0);
        }
    }
}
