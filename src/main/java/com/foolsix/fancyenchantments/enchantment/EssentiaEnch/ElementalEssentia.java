package com.foolsix.fancyenchantments.enchantment.EssentiaEnch;

import net.minecraft.ChatFormatting;

public enum ElementalEssentia {
    AER(ChatFormatting.YELLOW, 0xFFFF55),
    AQUA(ChatFormatting.AQUA, 0x55FFFF),
    IGNIS(ChatFormatting.GOLD, 0xFFAA00),
    TERRA(ChatFormatting.GREEN, 0x55FF55),
    HOLY(ChatFormatting.WHITE, 0xFFFFFF),
    TWISTED(ChatFormatting.DARK_PURPLE, 0xAA00AA);

    private final ChatFormatting chatFormatting;
    private final int color;

    ElementalEssentia(ChatFormatting chatFormatting, int color) {
        this.chatFormatting = chatFormatting;
        this.color = color;
    }

    public ChatFormatting chatFormatting() {
        return this.chatFormatting;
    }

    public int color() {
        return this.color;
    }

    public boolean conflictsWith(ElementalEssentia other) {
        return switch (this) {
            case AER -> other == TERRA;
            case TERRA -> other == AER;
            case AQUA -> other == IGNIS;
            case IGNIS -> other == AQUA;
            case HOLY -> other == TWISTED;
            case TWISTED -> other == HOLY;
        };
    }
}
