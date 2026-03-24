package com.foolsix.fancyenchantments.block.table;

import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ElementalEnchantmentScreen extends AbstractContainerScreen<ElementalEnchantmentMenu> {
    private static final ResourceLocation ENCHANTING_TABLE_TEXTURE =
            new ResourceLocation("minecraft", "textures/gui/container/enchanting_table.png");
    static final int VANILLA_WIDTH = 176;
    static final int PANEL_WIDTH = 132;
    static final int SIDE_PANEL_X = VANILLA_WIDTH + 8;
    static final int SIDE_PANEL_WIDTH = 118;
    static final int TOGGLE_BUTTON_SIZE = 10;
    static final int TOGGLE_BUTTON_MARGIN = 2;
    static final int SPECIAL_LIST_TOP = 76;
    static final int SPECIAL_LIST_TEXT_TOP = SPECIAL_LIST_TOP + 12;
    static final int SPECIAL_LIST_LINE_HEIGHT = 9;
    static final int SPECIAL_LIST_VISIBLE_LINES = 6;
    static final int SPECIAL_LIST_HEIGHT = SPECIAL_LIST_VISIBLE_LINES * SPECIAL_LIST_LINE_HEIGHT;
    static final int OFFER_LEFT = 59;
    static final int OFFER_TOP = 14;
    static final int OFFER_WIDTH = 109;
    static final int OFFER_HEIGHT = 19;
    static final int EXTRA_SLOT_LEFT = ElementalEnchantmentMenu.EXTRA_SLOT_X - 1;
    static final int EXTRA_SLOT_TOP = ElementalEnchantmentMenu.EXTRA_SLOT_Y;
    static final int UPGRADE_SLOT_LEFT = ElementalEnchantmentMenu.UPGRADE_SLOT_X - 1;
    static final int UPGRADE_SLOT_TOP = ElementalEnchantmentMenu.UPGRADE_SLOT_Y;
    static final int APPLY_BUTTON_WIDTH = UPGRADE_SLOT_LEFT + ElementalEnchantmentMenu.SLOT_SPACING - EXTRA_SLOT_LEFT;
    static final int APPLY_BUTTON_HEIGHT = 8;
    static final int APPLY_BUTTON_LEFT = EXTRA_SLOT_LEFT;
    static final int APPLY_BUTTON_TOP = UPGRADE_SLOT_TOP - APPLY_BUTTON_HEIGHT - 2;
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
        int sidePanelBackgroundColor = 0xCC121212; // semi-transparent dark charcoal
        int sidePanelInnerBackgroundColor = 0xAA1E1E1E; // semi-transparent dark gray
        guiGraphics.blit(ENCHANTING_TABLE_TEXTURE, left, top, 0, 0, VANILLA_WIDTH, this.imageHeight);
        if (!this.sidePanelCollapsed) {
            guiGraphics.fill(left + VANILLA_WIDTH, top, left + this.imageWidth, top + this.imageHeight, sidePanelBackgroundColor);
            guiGraphics.fill(left + VANILLA_WIDTH + 4, top + 4, left + this.imageWidth - 4, top + this.imageHeight - 4, sidePanelInnerBackgroundColor);
        }
        this.renderUpgradeSlotBackground(guiGraphics, left + EXTRA_SLOT_LEFT, top + EXTRA_SLOT_TOP);
        this.renderUpgradeSlotBackground(guiGraphics, left + UPGRADE_SLOT_LEFT, top + UPGRADE_SLOT_TOP);
        this.renderApplyUpgradeButton(guiGraphics, mouseX, mouseY, left + APPLY_BUTTON_LEFT, top + APPLY_BUTTON_TOP);
        this.renderSidePanelToggleButton(guiGraphics, mouseX, mouseY, left + this.getToggleButtonLeft(), top + this.getToggleButtonTop());

        // Set button colors to mimic the vanilla enchanting table offer panel
        int enabledOfferColor = 0xFF8F6D45; // opaque medium brown
        int disabledOfferColor = 0xFF4A4A4A; // opaque medium gray
        int hoveredOfferColor = 0xFFB08A57; // opaque warm gold brown
        int enabledOuterBorderColor = 0xFF2B1D12; // opaque very dark brown
        int disabledOuterBorderColor = 0xFF2F2F2F; // opaque dark gray
        int hoveredOuterBorderColor = 0xFF3C2918; // opaque dark warm brown
        int enabledInnerHighlightColor = 0xFFD8BF88; // opaque pale gold
        int disabledInnerHighlightColor = 0xFF7A7A7A; // opaque light gray
        int hoveredInnerHighlightColor = 0xFFF0DCA7; // opaque light gold
        int enabledInnerShadowColor = 0xFF5F4427; // opaque dark tan
        int disabledInnerShadowColor = 0xFF3A3A3A; // opaque charcoal gray
        int hoveredInnerShadowColor = 0xFF7A5A36; // opaque rich brown
        int offerTextColor = 0xFFFFFF; // white
        int offerCostColor = 0x80FF20; // bright green

        for (int i = 0; i < 3; ++i) {
            // Calculate the top position for this button
            int optionTop = top + OFFER_TOP + i * OFFER_HEIGHT;

            // Calculate the left position for this button
            int optionLeft = left + OFFER_LEFT;

            // Limit the right boundary so the button does not cross the vanilla GUI area
            int optionRight = Math.min(optionLeft + OFFER_WIDTH, left + VANILLA_WIDTH);
            int optionWidth = optionRight - optionLeft;

            // Check if this enchantment offer exists
            boolean enabled = this.menu.canAffordOffer(i);
            int color = enabled ? enabledOfferColor : disabledOfferColor;
            int outerBorderColor = enabled ? enabledOuterBorderColor : disabledOuterBorderColor;
            int innerHighlightColor = enabled ? enabledInnerHighlightColor : disabledInnerHighlightColor;
            int innerShadowColor = enabled ? enabledInnerShadowColor : disabledInnerShadowColor;

            // Check hover state using the button's actual width
            if (isHovering(OFFER_LEFT, OFFER_TOP + i * OFFER_HEIGHT, optionWidth, OFFER_HEIGHT, mouseX, mouseY) && enabled) {
                color = hoveredOfferColor; // Change color when hovered
                outerBorderColor = hoveredOuterBorderColor; // Dark outer border when hovered
                innerHighlightColor = hoveredInnerHighlightColor; // Bright inner top edge when hovered
                innerShadowColor = hoveredInnerShadowColor; // Dark inner bottom edge when hovered
            }

            // Draw the button rectangle
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

            // Get the enchantment instance for this option
            EnchantmentInstance offer = this.menu.getOffer(i);
            if (offer != null) {
                // Get the full name of the enchantment with its level
                Component offerText = offer.enchantment.getFullname(offer.level);

                // Draw the enchantment name, clipped to fit inside the button
                int textWidth = optionWidth - 8; // leave some padding
                guiGraphics.drawString(
                        this.font,
                        this.getTrimmedSequence(offerText, textWidth),
                        optionLeft + 4,
                        optionTop + 6,
                        offerTextColor,
                        false
                );

                // Draw the experience cost for the enchantment
                guiGraphics.drawString(
                        this.font,
                        String.valueOf(this.menu.getCost(i)),
                        optionRight - 14,
                        optionTop + 6,
                        offerCostColor,
                        false
                );
            }
        }

        if (!this.sidePanelCollapsed) {
            this.renderSidePanel(guiGraphics, left + SIDE_PANEL_X, top + sidePanelTop);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int labelTextColor = 0x404040; // dark gray
        guiGraphics.drawString(this.font, this.title, 12, 5, labelTextColor, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, 8, this.inventoryLabelY, labelTextColor, false);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
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
            if (this.menu.canStoreUpgrade() && this.minecraft != null && this.minecraft.gameMode != null) {
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
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollDelta) {
        if (!this.sidePanelCollapsed && this.isMouseOverSpecialList(mouseX, mouseY)) {
            int maxOffset = Math.max(0, this.getPossibleSpecialEnchantments(this.getDisplayedElementStats()).size() - SPECIAL_LIST_VISIBLE_LINES);
            this.specialEnchantScrollOffset = Math.max(0, Math.min(maxOffset, this.specialEnchantScrollOffset - (int) Math.signum(scrollDelta)));
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scrollDelta);
    }

    private void renderSidePanel(GuiGraphics guiGraphics, int panelLeft, int panelTop) {
        final int elementStatsTop = 14;
        final int elementStatsLineHeight = 10;
        int panelTitleColor = 0xE0E0E0; // light gray
        int panelTextColor = 0xFFFFFF; // white
        int panelSecondaryTextColor = 0xC8C8C8; // soft gray
        int panelEmptyTextColor = 0x8A8A8A; // muted gray
        int panelPageTextColor = 0xAAAAAA; // medium light gray
        Component elementStatsTitle = Component.translatable("screen.fancyenchantments.elemental_enchanting_table.element_stats");
        guiGraphics.drawString(this.font, elementStatsTitle, panelLeft, panelTop, panelTitleColor, false);

        int rightColumnLeft = panelLeft + Math.min(this.font.width(elementStatsTitle) + 8, 58);
        int rightColumnWidth = panelLeft + SIDE_PANEL_WIDTH - rightColumnLeft;
        Component totalLevelText = Component.translatable(
                "screen.fancyenchantments.elemental_enchanting_table.total_level", this.menu.getTotalEnchantingLevel());
        Component upgradeBonusText = Component.translatable(
                "screen.fancyenchantments.elemental_enchanting_table.upgrade_bonus", this.menu.getUpgradeBonus());
        guiGraphics.drawString(this.font,
                this.getTrimmedSequence(totalLevelText, rightColumnWidth),
                rightColumnLeft, panelTop, panelSecondaryTextColor, false);
        guiGraphics.drawString(this.font,
                this.getTrimmedSequence(upgradeBonusText, rightColumnWidth),
                rightColumnLeft, panelTop + 10, panelSecondaryTextColor, false);

        int[] elementStats = this.getDisplayedElementStats();
        if (this.minecraft != null && this.minecraft.player != null) {
            for (EnchUtils.Element element : EnchUtils.Element.values()) {
                Component line = Component.literal(element.name().charAt(0) + element.name().substring(1).toLowerCase() + ": " + elementStats[element.ordinal()])
                        .withStyle(EnchUtils.Element.getChatFormatting(element));
                guiGraphics.drawString(this.font, line, panelLeft, panelTop + elementStatsTop + element.ordinal() * elementStatsLineHeight, panelTextColor, false);
            }
        }

        int listTop = panelTop + SPECIAL_LIST_TOP;
        guiGraphics.drawString(this.font,
                Component.translatable("screen.fancyenchantments.elemental_enchanting_table.special_loot"),
                panelLeft, listTop, panelTitleColor, false);

        List<Component> possibleEnchantments = this.getPossibleSpecialEnchantments(elementStats);
        int maxOffset = Math.max(0, possibleEnchantments.size() - SPECIAL_LIST_VISIBLE_LINES);
        if (this.specialEnchantScrollOffset > maxOffset) {
            this.specialEnchantScrollOffset = maxOffset;
        }
        if (possibleEnchantments.isEmpty()) {
            guiGraphics.drawString(this.font,
                    Component.translatable("screen.fancyenchantments.elemental_enchanting_table.none"),
                    panelLeft, listTop + 12, panelEmptyTextColor, false);
            return;
        }

        int startIndex = this.specialEnchantScrollOffset;
        int endIndex = Math.min(possibleEnchantments.size(), startIndex + SPECIAL_LIST_VISIBLE_LINES);
        for (int i = startIndex; i < endIndex; ++i) {
            int lineIndex = i - startIndex;
            guiGraphics.drawString(this.font, this.getTrimmedSequence(possibleEnchantments.get(i), SIDE_PANEL_WIDTH),
                    panelLeft, listTop + 12 + lineIndex * SPECIAL_LIST_LINE_HEIGHT, panelTextColor, false);
        }
        if (possibleEnchantments.size() > SPECIAL_LIST_VISIBLE_LINES) {
            guiGraphics.drawString(this.font,
                    Component.literal((startIndex + 1) + "-" + endIndex + "/" + possibleEnchantments.size()),
                    panelLeft, listTop + 12 + SPECIAL_LIST_VISIBLE_LINES * SPECIAL_LIST_LINE_HEIGHT, panelPageTextColor, false);
        }
    }

    private List<Component> getPossibleSpecialEnchantments(int[] elementStats) {
        List<Component> components = new ArrayList<>();
        for (var enchantment : EnchUtils.getAllSpecialLootEnchantment()) {
            if (enchantment instanceof FEBaseEnchantment fe) {
                if (EnchUtils.matchesElementCondition(elementStats, fe.getChestGenerationCondition())) {
                    components.add(enchantment.getFullname(Math.min(fe.getMaxLevel(), Math.max(1, fe.getCONFIG().maxLevelCanBeDiscovered))));
                }
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

    private boolean isMouseOverSpecialList(double mouseX, double mouseY) {
        final int sidePanelTop = 8;
        int left = this.leftPos + SIDE_PANEL_X;
        int top = this.topPos + sidePanelTop + SPECIAL_LIST_TEXT_TOP;
        return mouseX >= left && mouseX < left + SIDE_PANEL_WIDTH
                && mouseY >= top && mouseY < top + SPECIAL_LIST_HEIGHT;
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
                tooltip.add(offer.enchantment.getFullname(offer.level));
                tooltip.add(Component.translatable("screen.fancyenchantments.elemental_enchanting_table.cost",
                        this.menu.getLapisCost(i), this.menu.getExperienceCost(i), this.menu.getCost(i)).withStyle(ChatFormatting.GRAY));
            }
            guiGraphics.renderComponentTooltip(this.font, tooltip, mouseX, mouseY);
            return;
        }

        ItemStack upgradeStack = this.menu.slots.get(ElementalEnchantmentMenu.UPGRADE_SLOT).getItem();
        if (upgradeStack.isEmpty()
                && isHovering(UPGRADE_SLOT_LEFT, UPGRADE_SLOT_TOP,
                ElementalEnchantmentMenu.SLOT_SPACING, ElementalEnchantmentMenu.SLOT_SPACING, mouseX, mouseY)) {
            List<Component> tooltip = new ArrayList<>();
            tooltip.add(Component.translatable("screen.fancyenchantments.elemental_enchanting_table.upgrade_slot"));
            tooltip.add(Component.translatable("screen.fancyenchantments.elemental_enchanting_table.upgrade_slot.desc"));
            guiGraphics.renderComponentTooltip(this.font, tooltip, mouseX, mouseY);
            return;
        }

        if (isHovering(this.getToggleButtonLeft(), this.getToggleButtonTop(), TOGGLE_BUTTON_SIZE, TOGGLE_BUTTON_SIZE, mouseX, mouseY)) {
            guiGraphics.renderComponentTooltip(this.font,
                    List.of(Component.translatable(this.sidePanelCollapsed
                            ? "screen.fancyenchantments.elemental_enchanting_table.expand_panel"
                            : "screen.fancyenchantments.elemental_enchanting_table.collapse_panel")),
                    mouseX, mouseY);
        }
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
        String text = this.sidePanelCollapsed ? "<" : ">";
        guiGraphics.drawString(this.font, text, left + 3, top + 1, 0xFFFFFF, false);
    }

    private void renderUpgradeSlotBackground(GuiGraphics guiGraphics, int left, int top) {
        int fill = 0xFF8B8B8B;
        guiGraphics.fill(left, top, left + ElementalEnchantmentMenu.SLOT_SPACING, top + ElementalEnchantmentMenu.SLOT_SPACING, 0xFFFFFFFF);//white
        guiGraphics.fill(left, top, left + ElementalEnchantmentMenu.SLOT_SPACING - 1, top + ElementalEnchantmentMenu.SLOT_SPACING - 1, 0xFF373737);//grey
        guiGraphics.fill(left + 1, top + 1, left + ElementalEnchantmentMenu.SLOT_SPACING - 1, top + ElementalEnchantmentMenu.SLOT_SPACING - 1, fill);
        guiGraphics.fill(left + ElementalEnchantmentMenu.SLOT_SPACING - 1, top, left + ElementalEnchantmentMenu.SLOT_SPACING, top + 1, fill);
        guiGraphics.fill(left, top + ElementalEnchantmentMenu.SLOT_SPACING - 1, left + 1, top + ElementalEnchantmentMenu.SLOT_SPACING, fill);
    }

    private void renderApplyUpgradeButton(GuiGraphics guiGraphics, int mouseX, int mouseY, int left, int top) {
        boolean enabled = this.menu.canStoreUpgrade();
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
