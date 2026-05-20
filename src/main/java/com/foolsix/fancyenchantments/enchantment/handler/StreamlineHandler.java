package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.STREAMLINE;

@EventBusSubscriber(modid = MODID)
public final class StreamlineHandler {
    private static final String TAG = "fe_streamline";

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;

        if (event.getEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof LivingEntity shooter) {
            int level = EnchUtils.getEnchantmentLevel(STREAMLINE, shooter);
            if (level > 0) {
                arrow.addTag(TAG);
                Vec3 direction = shooter.getViewVector(0.1f).scale(Config.STREAMLINE_SPEED_MULTIPLIER_PER_LEVEL.get() * level);
                arrow.push(direction.x(), direction.y(), direction.z());
            }
        }
    }
}
