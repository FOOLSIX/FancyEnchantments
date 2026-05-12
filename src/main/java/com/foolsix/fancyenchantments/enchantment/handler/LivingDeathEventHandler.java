package com.foolsix.fancyenchantments.enchantment.handler;

import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public interface LivingDeathEventHandler extends EventHandler {
    void handleLivingDeathEvent(LivingDeathEvent event);
}
