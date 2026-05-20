package com.foolsix.fancyenchantments.enchantment.EssentiaEnch;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

public enum Element {
    AER(ChatFormatting.YELLOW, 0xFFFF55, "element/aer"),
    AQUA(ChatFormatting.AQUA, 0x55FFFF, "element/aqua"),
    IGNIS(ChatFormatting.GOLD, 0xFFAA00, "element/ignis"),
    TERRA(ChatFormatting.GREEN, 0x55FF55, "element/terra"),
    HOLY(ChatFormatting.WHITE, 0xFFFFFF, "element/holy"),
    TWISTED(ChatFormatting.DARK_PURPLE, 0xAA00AA, "element/twisted");

    public static final Codec<Element> CODEC = Codec.STRING.xmap(
            s -> valueOf(s.toUpperCase()),
            e -> e.name().toLowerCase()
    );

    private final ChatFormatting chatFormatting;
    private final int color;
    private final TagKey<Enchantment> tag;

    Element(ChatFormatting chatFormatting, int color, String tagPath) {
        this.chatFormatting = chatFormatting;
        this.color = color;
        this.tag = TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(MODID, tagPath));
    }

    public ChatFormatting chatFormatting() {
        return this.chatFormatting;
    }

    public int color() {
        return this.color;
    }

    public TagKey<Enchantment> tag() {
        return this.tag;
    }

    public boolean conflictsWith(Element other) {
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
