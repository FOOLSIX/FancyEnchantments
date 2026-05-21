package com.foolsix.fancyenchantments.block.table;

import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.resource.catalyst.Catalyst;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.core.Holder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ElementalEnchantmentScreen extends AbstractContainerScreen<ElementalEnchantmentMenu> {
    private static final ResourceLocation ENCHANTING_TABLE_TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/gui/container/enchanting_table.png");
    static final int VANILLA_WIDTH = 176;
    static final int PANEL_WIDTH = 132;
    static final int SIDE_PANEL_X = VANILLA_WIDTH + 8;
    static final int SIDE_PANEL_WIDTH = 118;
    static final int RIGHT_COLUMN_LEFT = 58;
    static final int RIGHT_COLUMN_WIDTH = SIDE_PANEL_WIDTH - RIGHT_COLUMN_LEFT;
    static final int BIAS_LIST_TOP = 22;
    static final int BIAS_LIST_TEXT_TOP = BIAS_LIST_TOP + 12;
    static final int BIAS_LIST_LINE_HEIGHT = 9;
    static final int BIAS_LIST_VISIBLE_LINES = 3;
    static final int BIAS_LIST_HEIGHT = BIAS_LIST_VISIBLE_LINES * BIAS_LIST_LINE_HEIGHT;
    static final int TOGGLE_BUTTON_SIZE = 10;
    static final int TOGGLE_BUTTON_MARGIN = 2;
    static final int SPECIAL_LIST_TOP = 86;
    static final int SPECIAL_LIST_TEXT_TOP = SPECIAL_LIST_TOP + 12;
    static final int SPECIAL_LIST_LINE_HEIGHT = 9;
    static final int SPECIAL_LIST_VISIBLE_LINES = 5;
    static final int SPECIAL_LIST_HEIGHT = SPECIAL_LIST_VISIBLE_LINES * SPECIAL_LIST_LINE_HEIGHT;
    static final int OFFER_LEFT = 59;
    static final int OFFER_TOP = 14;
    static final int OFFER_WIDTH = 109;
    static final int OFFER_HEIGHT = 19;
    static final int CATALYST_SLOT_LEFT = ElementalEnchantmentMenu.CATALYST_SLOT_X - 1;
    static final int CATALYST_SLOT_TOP = ElementalEnchantmentMenu.CATALYST_SLOT_Y;
    static final int UPGRADE_SLOT_LEFT = ElementalEnchantmentMenu.UPGRADE_SLOT_X - 1;
    static final int UPGRADE_SLOT_TOP = ElementalEnchantmentMenu.UPGRADE_SLOT_Y;
    static final int APPLY_BUTTON_WIDTH = UPGRADE_SLOT_LEFT + ElementalEnchantmentMenu.SLOT_SPACING - CATALYST_SLOT_LEFT;
    static final int APPLY_BUTTON_HEIGHT = 8;
    static final int APPLY_BUTTON_LEFT = CATALYST_SLOT_LEFT;
    static final int APPLY_BUTTON_TOP = UPGRADE_SLOT_TOP - APPLY_BUTTON_HEIGHT - 2;
    private int biasScrollOffset;
    private int specialEnchantScrollOffset;
    private boolean sidePanelCollapsed;

    public ElementalEnchantmentScreen(ElementalEnchantmentMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageHeight = 166;
        this.inventoryLabelY = this.imageHeight - 94;
        this.updateLayout();
    }

    @Override
    protected void init() {
        this.updateLayout();
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        final int sidePanelTop = 8;
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int left = this.leftPos;
        int top = this.topPos;
        guiGraphics.blit(ENCHANTING_TABLE_TEXTURE, left, top, 0, 0, VANILLA_WIDTH, this.imageHeight);
        if (!this.sidePanelCollapsed) {
            guiGraphics.fill(left + VANILLA_WIDTH, top, left + this.imageWidth, top + this.imageHeight, 0xCC121212);
            guiGraphics.fill(left + VANILLA_WIDTH + 4, top + 4, left + this.imageWidth - 4, top + this.imageHeight - 4, 0xAA1E1E1E);
        }
        this.renderUpgradeSlotBackground(guiGraphics, left + CATALYST_SLOT_LEFT, top + CATALYST_SLOT_TOP);
        this.renderUpgradeSlotBackground(guiGraphics, left + UPGRADE_SLOT_LEFT, top + UPGRADE_SLOT_TOP);
        this.renderApplyUpgradeButton(guiGraphics, mouseX, mouseY, left + APPLY_BUTTON_LEFT, top + APPLY_BUTTON_TOP);
        this.renderSidePanelToggleButton(guiGraphics, mouseX, mouseY, left + this.getToggleButtonLeft(), top + this.getToggleButtonTop());

        for (int i = 0; i < 3; ++i) {
            int optionTop = top + OFFER_TOP + i * OFFER_HEIGHT;
            int optionLeft = left + OFFER_LEFT;
            int optionRight = Math.min(optionLeft + OFFER_WIDTH, left + VANILLA_WIDTH);
            int optionWidth = optionRight - optionLeft;
            boolean enabled = this.menu.canAffordOffer(i);
            int color = enabled ? 0xFF8F6D45 : 0xFF4A4A4A;
            int outerBorderColor = enabled ? 0xFF2B1D12 : 0xFF2F2F2F;
            int innerHighlightColor = enabled ? 0xFFD8BF88 : 0xFF7A7A7A;
            int innerShadowColor = enabled ? 0xFF5F4427 : 0xFF3A3A3A;

            if (isHovering(OFFER_LEFT, OFFER_TOP + i * OFFER_HEIGHT, optionWidth, OFFER_HEIGHT, mouseX, mouseY) && enabled) {
                color = 0xFFB08A57;
                outerBorderColor = 0xFF3C2918;
                innerHighlightColor = 0xFFF0DCA7;
                innerShadowColor = 0xFF7A5A36;
            }

            guiGraphics.fill(optionLeft, optionTop, optionRight, optionTop + OFFER_HEIGHT, color);
            guiGraphics.fill(optionLeft, optionTop, optionRight, optionTop + 1, outerBorderColor);
            guiGraphics.fill(optionLeft, optionTop + OFFER_HEIGHT - 1, optionRight, optionTop + OFFER_HEIGHT, outerBorderColor);
            guiGraphics.fill(optionLeft, optionTop, optionLeft + 1, optionTop + OFFER_HEIGHT, outerBorderColor);
            guiGraphics.fill(optionRight - 1, optionTop, optionRight, optionTop + OFFER_HEIGHT, outerBorderColor);
            if (optionWidth > 2 && OFFER_HEIGHT > 2) {
                guiGraphics.fill(optionLeft + 1, optionTop + 1, optionRight - 1, optionTop + 2, innerHighlightColor);
                guiGraphics.fill(optionLeft + 1, optionTop + 1, optionLeft + 2, optionTop + OFFER_HEIGHT - 1, innerHighlightColor);
                guiGraphics.fill(optionLeft + 1, optionTop + OFFER_HEIGHT - 2, optionRight - 1, optionTop + OFFER_HEIGHT - 1, innerShadowColor);
                guiGraphics.fill(optionRight - 2, optionTop + 1, optionRight - 1, optionTop + OFFER_HEIGHT - 1, innerShadowColor);
            }

            EnchantmentInstance offer = this.menu.getOffer(i);
            if (offer != null) {
                Component offerText = Enchantment.getFullname(offer.enchantment, offer.level);
                int textWidth = optionWidth - 8;
                guiGraphics.drawString(this.font, this.getTrimmedSequence(offerText, textWidth), optionLeft + 4, optionTop + 6, 0xFFFFFF, false);
                guiGraphics.drawString(this.font, String.valueOf(this.menu.getCost(i)), optionRight - 14, optionTop + 6, 0x80FF20, false);
            }
        }

        if (!this.sidePanelCollapsed) {
            this.renderSidePanel(guiGraphics, left + SIDE_PANEL_X, top + sidePanelTop);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, 12, 5, 0x404040, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, 8, this.inventoryLabelY, 0x404040, false);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        if (!this.isHoveringCatalystSlot(mouseX, mouseY)) {
            this.renderTooltip(guiGraphics, mouseX, mouseY);
        }
        this.renderOfferTooltips(guiGraphics, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovering(this.getToggleButtonLeft(), this.getToggleButtonTop(), TOGGLE_BUTTON_SIZE, TOGGLE_BUTTON_SIZE, mouseX, mouseY)) {
            this.sidePanelCollapsed = !this.sidePanelCollapsed;
            this.updateLayout();
            this.init();
            return true;
        }
        if (isHovering(APPLY_BUTTON_LEFT, APPLY_BUTTON_TOP, APPLY_BUTTON_WIDTH, APPLY_BUTTON_HEIGHT, mouseX, mouseY)) {
            if (this.menu.canStore() && this.minecraft != null && this.minecraft.gameMode != null) {
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, ElementalEnchantmentMenu.APPLY_UPGRADE_BUTTON_ID);
                return true;
            }
            return super.mouseClicked(mouseX, mouseY, button);
        }
        int left = this.leftPos + OFFER_LEFT;
        int top = this.topPos + OFFER_TOP;
        for (int i = 0; i < 3; ++i) {
            if (!this.menu.canAffordOffer(i)) {
                continue;
            }
            double relativeX = mouseX - left;
            double relativeY = mouseY - (top + i * OFFER_HEIGHT);
            if (relativeX >= 0.0D && relativeY >= 0.0D && relativeX < OFFER_WIDTH && relativeY < OFFER_HEIGHT) {
                if (this.minecraft != null && this.minecraft.gameMode != null) {
                    this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, i);
                }
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (!this.sidePanelCollapsed && this.isMouseOverBiasList(mouseX, mouseY)) {
            int maxOffset = Math.max(0, this.getActiveCatalystSummaryLines().size() - BIAS_LIST_VISIBLE_LINES);
            this.biasScrollOffset = Math.max(0, Math.min(maxOffset, this.biasScrollOffset - (int) Math.signum(scrollY)));
            return true;
        }
        if (!this.sidePanelCollapsed && this.isMouseOverSpecialList(mouseX, mouseY)) {
            int maxOffset = Math.max(0, this.getPossibleSpecialEnchantments(this.getDisplayedElementStats()).size() - SPECIAL_LIST_VISIBLE_LINES);
            this.specialEnchantScrollOffset = Math.max(0, Math.min(maxOffset, this.specialEnchantScrollOffset - (int) Math.signum(scrollY)));
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    private void renderSidePanel(GuiGraphics guiGraphics, int panelLeft, int panelTop) {
        final int elementStatsTop = 14;
        final int elementStatsLineHeight = 10;
        guiGraphics.drawString(this.font, Component.translatable("screen.fancyenchantments.elemental_enchanting_table.element_stats"), panelLeft, panelTop, 0xE0E0E0, false);

        int rightColumnLeft = panelLeft + RIGHT_COLUMN_LEFT;
        Component totalLevelText = Component.translatable("screen.fancyenchantments.elemental_enchanting_table.total_level", this.menu.getBookshelfCount(), this.menu.getUpgradeBonus());
        guiGraphics.drawString(this.font, this.getTrimmedSequence(totalLevelText, RIGHT_COLUMN_WIDTH), rightColumnLeft, panelTop, 0xC8C8C8, false);
        guiGraphics.drawString(this.font, Component.translatable("screen.fancyenchantments.elemental_enchanting_table.catalyst_slot.bias"), rightColumnLeft, panelTop + BIAS_LIST_TOP, 0xE0E0E0, false);

        List<Component> biasLines = this.getActiveCatalystSummaryLines();
        int maxBiasOffset = Math.max(0, biasLines.size() - BIAS_LIST_VISIBLE_LINES);
        if (this.biasScrollOffset > maxBiasOffset) {
            this.biasScrollOffset = maxBiasOffset;
        }
        if (biasLines.isEmpty()) {
            guiGraphics.drawString(this.font, Component.translatable("screen.fancyenchantments.elemental_enchanting_table.none"), rightColumnLeft, panelTop + BIAS_LIST_TEXT_TOP, 0x8A8A8A, false);
        } else {
            int biasStartIndex = this.biasScrollOffset;
            int biasEndIndex = Math.min(biasLines.size(), biasStartIndex + BIAS_LIST_VISIBLE_LINES);
            for (int i = biasStartIndex; i < biasEndIndex; ++i) {
                int lineIndex = i - biasStartIndex;
                guiGraphics.drawString(this.font, this.getTrimmedSequence(biasLines.get(i), RIGHT_COLUMN_WIDTH), rightColumnLeft, panelTop + BIAS_LIST_TEXT_TOP + lineIndex * BIAS_LIST_LINE_HEIGHT, 0xC8C8C8, false);
            }
        }

        int[] elementStats = this.getDisplayedElementStats();
        if (this.minecraft != null && this.minecraft.player != null) {
            for (Element element : Element.values()) {
                Component line = Component.literal(element.name().charAt(0) + element.name().substring(1).toLowerCase() + ": " + elementStats[element.ordinal()]).withStyle(element.chatFormatting());
                guiGraphics.drawString(this.font, line, panelLeft, panelTop + elementStatsTop + element.ordinal() * elementStatsLineHeight, 0xFFFFFF, false);
            }
        }

        int listTop = panelTop + SPECIAL_LIST_TOP;
        guiGraphics.drawString(this.font, Component.translatable("screen.fancyenchantments.elemental_enchanting_table.special_loot"), panelLeft, listTop, 0xE0E0E0, false);

        List<Component> possibleEnchantments = this.getPossibleSpecialEnchantments(elementStats);
        int maxOffset = Math.max(0, possibleEnchantments.size() - SPECIAL_LIST_VISIBLE_LINES);
        if (this.specialEnchantScrollOffset > maxOffset) {
            this.specialEnchantScrollOffset = maxOffset;
        }
        if (possibleEnchantments.isEmpty()) {
            guiGraphics.drawString(this.font, Component.translatable("screen.fancyenchantments.elemental_enchanting_table.none"), panelLeft, listTop + 12, 0x8A8A8A, false);
            return;
        }

        int startIndex = this.specialEnchantScrollOffset;
        int endIndex = Math.min(possibleEnchantments.size(), startIndex + SPECIAL_LIST_VISIBLE_LINES);
        for (int i = startIndex; i < endIndex; ++i) {
            int lineIndex = i - startIndex;
            guiGraphics.drawString(this.font, this.getTrimmedSequence(possibleEnchantments.get(i), SIDE_PANEL_WIDTH), panelLeft, listTop + 12 + lineIndex * SPECIAL_LIST_LINE_HEIGHT, 0xFFFFFF, false);
        }
    }

    private List<Component> getPossibleSpecialEnchantments(int[] elementStats) {
        List<Component> components = new ArrayList<>();
        for (ResourceLocation enchantmentId : EnchUtils.getAllSpecialLootEnchantments()) {
            if (!EnchUtils.matchesElementCondition(elementStats, EnchUtils.getElementCondition(enchantmentId))) {
                continue;
            }
            Holder<Enchantment> enchantment = this.minecraft == null || this.minecraft.level == null
                    ? null
                    : this.minecraft.level.registryAccess().lookupOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT).get(ResourceKey.create(net.minecraft.core.registries.Registries.ENCHANTMENT, enchantmentId)).orElse(null);
            if (enchantment != null) {
                components.add(Enchantment.getFullname(enchantment, Math.max(1, enchantment.value().getMaxLevel())));
            }
        }
        return components;
    }

    private int[] getDisplayedElementStats() {
        return EnchUtils.getElementStatsFromEquipment(this.minecraft == null ? null : this.minecraft.player);
    }

    private FormattedCharSequence getTrimmedSequence(Component text, int maxWidth) {
        List<FormattedCharSequence> lines = this.font.split(text, maxWidth);
        return lines.isEmpty() ? FormattedCharSequence.EMPTY : lines.get(0);
    }

    private boolean isMouseOverBiasList(double mouseX, double mouseY) {
        final int sidePanelTop = 8;
        int left = this.leftPos + SIDE_PANEL_X + RIGHT_COLUMN_LEFT;
        int top = this.topPos + sidePanelTop + BIAS_LIST_TEXT_TOP;
        return mouseX >= left && mouseX < left + RIGHT_COLUMN_WIDTH && mouseY >= top && mouseY < top + BIAS_LIST_HEIGHT;
    }

    private boolean isMouseOverSpecialList(double mouseX, double mouseY) {
        final int sidePanelTop = 8;
        int left = this.leftPos + SIDE_PANEL_X;
        int top = this.topPos + sidePanelTop + SPECIAL_LIST_TEXT_TOP;
        return mouseX >= left && mouseX < left + SIDE_PANEL_WIDTH && mouseY >= top && mouseY < top + SPECIAL_LIST_HEIGHT;
    }

    private boolean isHoveringCatalystSlot(double mouseX, double mouseY) {
        return isHovering(CATALYST_SLOT_LEFT, CATALYST_SLOT_TOP, ElementalEnchantmentMenu.SLOT_SPACING, ElementalEnchantmentMenu.SLOT_SPACING, mouseX, mouseY);
    }

    private void renderOfferTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        for (int i = 0; i < 3; ++i) {
            if (!isHovering(OFFER_LEFT, OFFER_TOP + i * OFFER_HEIGHT, OFFER_WIDTH, OFFER_HEIGHT, mouseX, mouseY)) {
                continue;
            }
            EnchantmentInstance offer = this.menu.getOffer(i);
            List<Component> tooltip = new ArrayList<>();
            if (offer == null) {
                tooltip.add(Component.translatable("screen.fancyenchantments.elemental_enchanting_table.unavailable"));
            } else {
                tooltip.add(Enchantment.getFullname(offer.enchantment, offer.level));
                tooltip.add(Component.translatable("screen.fancyenchantments.elemental_enchanting_table.cost", this.menu.getLapisCost(i), this.menu.getExperienceCost(i), this.menu.getCost(i)));
            }
            guiGraphics.renderComponentTooltip(this.font, tooltip, mouseX, mouseY);
            return;
        }

        if (this.isHoveringCatalystSlot(mouseX, mouseY)) {
            ItemStack catalystStack = this.menu.slots.get(ElementalEnchantmentMenu.CATALYST_SLOT).getItem();
            List<Component> tooltip = new ArrayList<>();
            if (catalystStack.isEmpty()) {
                tooltip.add(Component.translatable("screen.fancyenchantments.elemental_enchanting_table.catalyst_slot"));
                tooltip.add(Component.translatable("screen.fancyenchantments.elemental_enchanting_table.catalyst_slot.desc"));
            } else {
                tooltip.add(catalystStack.getHoverName());
                this.appendCatalystTooltipLines(tooltip, catalystStack);
            }
            guiGraphics.renderComponentTooltip(this.font, tooltip, mouseX, mouseY);
            return;
        }

        ItemStack upgradeStack = this.menu.slots.get(ElementalEnchantmentMenu.UPGRADE_SLOT).getItem();
        if (upgradeStack.isEmpty() && isHovering(UPGRADE_SLOT_LEFT, UPGRADE_SLOT_TOP, ElementalEnchantmentMenu.SLOT_SPACING, ElementalEnchantmentMenu.SLOT_SPACING, mouseX, mouseY)) {
            guiGraphics.renderComponentTooltip(this.font, List.of(
                    Component.translatable("screen.fancyenchantments.elemental_enchanting_table.upgrade_slot"),
                    Component.translatable("screen.fancyenchantments.elemental_enchanting_table.upgrade_slot.desc")
            ), mouseX, mouseY);
            return;
        }

        if (isHovering(this.getToggleButtonLeft(), this.getToggleButtonTop(), TOGGLE_BUTTON_SIZE, TOGGLE_BUTTON_SIZE, mouseX, mouseY)) {
            guiGraphics.renderComponentTooltip(this.font, List.of(Component.translatable(this.sidePanelCollapsed
                    ? "screen.fancyenchantments.elemental_enchanting_table.expand_panel"
                    : "screen.fancyenchantments.elemental_enchanting_table.collapse_panel")), mouseX, mouseY);
        }
    }

    private void appendCatalystTooltipLines(List<Component> tooltip, ItemStack catalystStack) {
        ResourceLocation resourceLocation = catalystStack.getItemHolder().unwrapKey().map(ResourceKey::location).orElse(null);
        if (resourceLocation == null) {
            return;
        }
        Map<Catalyst, Integer> catalystData = Catalyst.catalystDataMap.get(resourceLocation.toString());
        if (catalystData == null || catalystData.isEmpty()) {
            tooltip.add(Component.translatable("screen.fancyenchantments.elemental_enchanting_table.catalyst_slot.no_data"));
            return;
        }

        tooltip.add(Component.translatable("screen.fancyenchantments.elemental_enchanting_table.catalyst_slot.bias"));
        catalystData.entrySet().stream()
                .filter(entry -> entry.getKey() != null && entry.getValue() != null)
                .sorted(Comparator.comparingInt(entry -> entry.getKey().ordinal()))
                .forEach(entry -> tooltip.add(Component.translatable(
                        "screen.fancyenchantments.elemental_enchanting_table.catalyst_slot.entry",
                        Component.translatable(this.getCatalystTranslationKey(entry.getKey())),
                        entry.getValue()
                )));
    }

    private String getCatalystTranslationKey(Catalyst catalyst) {
        return switch (catalyst) {
            case AER -> "screen.fancyenchantments.elemental_enchanting_table.catalyst.aer";
            case AQUA -> "screen.fancyenchantments.elemental_enchanting_table.catalyst.aqua";
            case IGNIS -> "screen.fancyenchantments.elemental_enchanting_table.catalyst.ignis";
            case TERRA -> "screen.fancyenchantments.elemental_enchanting_table.catalyst.terra";
            case HOLY -> "screen.fancyenchantments.elemental_enchanting_table.catalyst.holy";
            case TWISTED -> "screen.fancyenchantments.elemental_enchanting_table.catalyst.twisted";
            case TREASURE -> "screen.fancyenchantments.elemental_enchanting_table.catalyst.treasure";
            case SPECIAL -> "screen.fancyenchantments.elemental_enchanting_table.catalyst.special";
        };
    }

    private List<Component> getActiveCatalystSummaryLines() {
        List<Component> lines = new ArrayList<>();
        for (Catalyst catalyst : Catalyst.values()) {
            int bonus = this.menu.getCatalystBonus(catalyst.ordinal());
            if (bonus <= 0) {
                continue;
            }
            lines.add(Component.translatable("screen.fancyenchantments.elemental_enchanting_table.catalyst_slot.entry", Component.translatable(this.getCatalystTranslationKey(catalyst)), bonus));
        }
        return lines;
    }

    private void updateLayout() {
        this.imageWidth = this.sidePanelCollapsed ? VANILLA_WIDTH : VANILLA_WIDTH + PANEL_WIDTH;
    }

    private int getToggleButtonLeft() {
        return this.imageWidth - TOGGLE_BUTTON_SIZE - TOGGLE_BUTTON_MARGIN;
    }

    private int getToggleButtonTop() {
        return TOGGLE_BUTTON_MARGIN;
    }

    private void renderSidePanelToggleButton(GuiGraphics guiGraphics, int mouseX, int mouseY, int left, int top) {
        boolean hovered = isHovering(this.getToggleButtonLeft(), this.getToggleButtonTop(), TOGGLE_BUTTON_SIZE, TOGGLE_BUTTON_SIZE, mouseX, mouseY);
        int fill = hovered ? 0xFFB08A57 : 0xFF8F6D45;
        int border = hovered ? 0xFF3C2918 : 0xFF2B1D12;
        int highlight = hovered ? 0xFFF0DCA7 : 0xFFD8BF88;
        int shadow = hovered ? 0xFF7A5A36 : 0xFF5F4427;
        guiGraphics.fill(left, top, left + TOGGLE_BUTTON_SIZE, top + TOGGLE_BUTTON_SIZE, fill);
        guiGraphics.fill(left, top, left + TOGGLE_BUTTON_SIZE, top + 1, border);
        guiGraphics.fill(left, top + TOGGLE_BUTTON_SIZE - 1, left + TOGGLE_BUTTON_SIZE, top + TOGGLE_BUTTON_SIZE, border);
        guiGraphics.fill(left, top, left + 1, top + TOGGLE_BUTTON_SIZE, border);
        guiGraphics.fill(left + TOGGLE_BUTTON_SIZE - 1, top, left + TOGGLE_BUTTON_SIZE, top + TOGGLE_BUTTON_SIZE, border);
        guiGraphics.fill(left + 1, top + 1, left + TOGGLE_BUTTON_SIZE - 1, top + 2, highlight);
        guiGraphics.fill(left + 1, top + 1, left + 2, top + TOGGLE_BUTTON_SIZE - 1, highlight);
        guiGraphics.fill(left + 1, top + TOGGLE_BUTTON_SIZE - 2, left + TOGGLE_BUTTON_SIZE - 1, top + TOGGLE_BUTTON_SIZE - 1, shadow);
        guiGraphics.fill(left + TOGGLE_BUTTON_SIZE - 2, top + 1, left + TOGGLE_BUTTON_SIZE - 1, top + TOGGLE_BUTTON_SIZE - 1, shadow);
        guiGraphics.drawString(this.font, this.sidePanelCollapsed ? "<" : ">", left + 3, top + 1, 0xFFFFFF, false);
    }

    private void renderUpgradeSlotBackground(GuiGraphics guiGraphics, int left, int top) {
        int fill = 0xFF8B8B8B;
        guiGraphics.fill(left, top, left + ElementalEnchantmentMenu.SLOT_SPACING, top + ElementalEnchantmentMenu.SLOT_SPACING, 0xFFFFFFFF);
        guiGraphics.fill(left, top, left + ElementalEnchantmentMenu.SLOT_SPACING - 1, top + ElementalEnchantmentMenu.SLOT_SPACING - 1, 0xFF373737);
        guiGraphics.fill(left + 1, top + 1, left + ElementalEnchantmentMenu.SLOT_SPACING - 1, top + ElementalEnchantmentMenu.SLOT_SPACING - 1, fill);
        guiGraphics.fill(left + ElementalEnchantmentMenu.SLOT_SPACING - 1, top, left + ElementalEnchantmentMenu.SLOT_SPACING, top + 1, fill);
        guiGraphics.fill(left, top + ElementalEnchantmentMenu.SLOT_SPACING - 1, left + 1, top + ElementalEnchantmentMenu.SLOT_SPACING, fill);
    }

    private void renderApplyUpgradeButton(GuiGraphics guiGraphics, int mouseX, int mouseY, int left, int top) {
        boolean enabled = this.menu.canStore();
        boolean hovered = isHovering(APPLY_BUTTON_LEFT, APPLY_BUTTON_TOP, APPLY_BUTTON_WIDTH, APPLY_BUTTON_HEIGHT, mouseX, mouseY);
        int fill = enabled ? (hovered ? 0xFFB08A57 : 0xFF8F6D45) : 0xFF4A4A4A;
        int border = enabled ? (hovered ? 0xFF3C2918 : 0xFF2B1D12) : 0xFF2F2F2F;
        int highlight = enabled ? (hovered ? 0xFFF0DCA7 : 0xFFD8BF88) : 0xFF7A7A7A;
        int shadow = enabled ? (hovered ? 0xFF7A5A36 : 0xFF5F4427) : 0xFF3A3A3A;
        guiGraphics.fill(left, top, left + APPLY_BUTTON_WIDTH, top + APPLY_BUTTON_HEIGHT, fill);
        guiGraphics.fill(left, top, left + APPLY_BUTTON_WIDTH, top + 1, border);
        guiGraphics.fill(left, top + APPLY_BUTTON_HEIGHT - 1, left + APPLY_BUTTON_WIDTH, top + APPLY_BUTTON_HEIGHT, border);
        guiGraphics.fill(left, top, left + 1, top + APPLY_BUTTON_HEIGHT, border);
        guiGraphics.fill(left + APPLY_BUTTON_WIDTH - 1, top, left + APPLY_BUTTON_WIDTH, top + APPLY_BUTTON_HEIGHT, border);
        guiGraphics.fill(left + 1, top + 1, left + APPLY_BUTTON_WIDTH - 1, top + 2, highlight);
        guiGraphics.fill(left + 1, top + 1, left + 2, top + APPLY_BUTTON_HEIGHT - 1, highlight);
        guiGraphics.fill(left + 1, top + APPLY_BUTTON_HEIGHT - 2, left + APPLY_BUTTON_WIDTH - 1, top + APPLY_BUTTON_HEIGHT - 1, shadow);
        guiGraphics.fill(left + APPLY_BUTTON_WIDTH - 2, top + 1, left + APPLY_BUTTON_WIDTH - 1, top + APPLY_BUTTON_HEIGHT - 1, shadow);
        Component text = Component.literal("+");
        guiGraphics.drawString(this.font, text, left + (APPLY_BUTTON_WIDTH - this.font.width(text)) / 2, top + 1, enabled ? 0xFFFFFF : 0xC0C0C0, false);
    }
}
