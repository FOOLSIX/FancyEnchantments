package com.foolsix.fancyenchantments.enchantment.handler;

import net.minecraftforge.event.entity.living.LivingHurtEvent;

public interface LivingHurtEventHandler extends EventHandler{
    void handleLivingHurtEvent(LivingHurtEvent e);

    default int getLivingHurtPriority() {
        return NOT_MODIFY;
    }
}
