package com.foolsix.fancyenchantments.enchantment.handler;

import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

public interface ItemAttributeModifierEventHandler extends EventHandler {
    void handleItemAttributeModifier(ItemAttributeModifierEvent event);
}
