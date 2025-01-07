package com.foolsix.fancyenchantments.enchantment.handler;

import net.minecraftforge.event.ItemAttributeModifierEvent;

public interface ItemAttributeModifierEventHandler extends EventHandler{
    void handleItemAttributeModifier(ItemAttributeModifierEvent e);
}
