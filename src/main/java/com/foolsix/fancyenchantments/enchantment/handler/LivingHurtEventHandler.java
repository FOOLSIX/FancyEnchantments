package com.foolsix.fancyenchantments.enchantment.handler;

import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public interface LivingHurtEventHandler extends EventHandler {
    void handleLivingHurtEvent(LivingIncomingDamageEvent event);

    default int getLivingHurtPriority() {
        return NOT_MODIFY;
    }
}
