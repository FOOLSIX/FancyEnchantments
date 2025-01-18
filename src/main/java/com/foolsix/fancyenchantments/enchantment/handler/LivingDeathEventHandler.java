package com.foolsix.fancyenchantments.enchantment.handler;

import net.minecraftforge.event.entity.living.LivingDeathEvent;

public interface LivingDeathEventHandler extends EventHandler{
    void handleLivingDeathEvent(LivingDeathEvent e);
}
