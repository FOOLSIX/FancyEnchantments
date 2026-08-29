package com.foolsix.fancyenchantments.block.table;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.block.BlockReg;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg;
import com.foolsix.fancyenchantments.resource.catalyst.Catalyst;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.*;

import static com.foolsix.fancyenchantments.tag.FETags.Items.UPGRADE_MATERIALS;

public class ElementalEnchantmentMenu extends AbstractContainerMenu implements ContainerListener {
    private static final int OFFER_COUNT = 3;
    private static final int BOOKSHELF_LEVEL_PER_BLOCK = 2;
    private static final int MAX_BOOKSHELF_LEVEL = Config.ENCHANTING_TABLE_MAX_BOOKSHELF_LEVEL.get();
    public static final int MAX_STORED_UPGRADE_BONUS = Config.ENCHANTING_TABLE_MAX_UPGRADE_BONUS.get();
    public static final int APPLY_UPGRADE_BUTTON_ID = OFFER_COUNT;
    static final int ENCHANT_SLOT_COUNT = 4;
    static final int INPUT_SLOT = 0;
    static final int LAPIS_SLOT = 1;
    static final int CATALYST_SLOT = 2;
    static final int UPGRADE_SLOT = 3;
    static final int OFFER_COST_DATA_START = 0;
    static final int OFFER_ENCHANTMENT_DATA_START = OFFER_COST_DATA_START + OFFER_COUNT;
    static final int OFFER_LEVEL_DATA_START = OFFER_ENCHANTMENT_DATA_START + OFFER_COUNT;
    static final int BOOKSHELF_DATA = OFFER_LEVEL_DATA_START + OFFER_COUNT;
    static final int UPGRADE_BONUS_DATA = BOOKSHELF_DATA + 1;
    static final int CATALYST_DATA_START = UPGRADE_BONUS_DATA + 1;
    static final int MENU_DATA_COUNT = CATALYST_DATA_START + Catalyst.values().length;
    static final int PLAYER_INV_START = 4;
    static final int PLAYER_HOTBAR_START = 31;
    static final int SLOT_SPACING = 18;
    private static final int INPUT_SLOT_X = 15;
    private static final int INPUT_SLOT_Y = 47;
    static final int LAPIS_SLOT_X = 35;
    static final int LAPIS_SLOT_Y = 47;
    static final int UPGRADE_SLOT_X = 35;
    static final int UPGRADE_SLOT_Y = 27;
    static final int CATALYST_SLOT_X = UPGRADE_SLOT_X - SLOT_SPACING - 2;
    static final int CATALYST_SLOT_Y = UPGRADE_SLOT_Y;

    private final Container enchantSlots = new SimpleContainer(ENCHANT_SLOT_COUNT) {
        @Override
        public void setChanged() {
            super.setChanged();
            ElementalEnchantmentMenu.this.slotsChanged(this);
        }
    };
    private final ContainerData data = new SimpleContainerData(MENU_DATA_COUNT);
    private final ContainerLevelAccess access;
    private final Inventory playerInventory;
    private final RandomSource random = RandomSource.create();

    public ElementalEnchantmentMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf data) {
        this(containerId, inventory, ContainerLevelAccess.create(inventory.player.level(), data.readBlockPos()));
    }

    public ElementalEnchantmentMenu(int windowId, Inventory inventory, ContainerLevelAccess access) {
        super(BlockReg.ELEMENTAL_ENCHANTMENT_MENU.get(), windowId);
        final int playerInventoryX = 8;
        final int playerInventoryY = 84;
        final int hotbarY = 142;
        this.access = access;
        this.playerInventory = inventory;

        this.addSlot(new Slot(this.enchantSlots, INPUT_SLOT, INPUT_SLOT_X, INPUT_SLOT_Y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Items.BOOK) || stack.isEnchantable();
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.enchantSlots, LAPIS_SLOT, LAPIS_SLOT_X, LAPIS_SLOT_Y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Items.LAPIS_LAZULI);
            }
        });
        this.addSlot(new Slot(this.enchantSlots, CATALYST_SLOT, CATALYST_SLOT_X, CATALYST_SLOT_Y + 1) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                if (ElementalEnchantmentMenu.this.hasAnyCatalystData()) {
                    return false;
                }

                ResourceLocation resourceLocation = stack.getItemHolder().unwrapKey()
                        .map(ResourceKey::location)
                        .orElse(null);
                return resourceLocation != null && Catalyst.catalystDataMap.containsKey(resourceLocation.toString());
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.enchantSlots, UPGRADE_SLOT, UPGRADE_SLOT_X, UPGRADE_SLOT_Y + 1) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(UPGRADE_MATERIALS);
            }
        });

        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(inventory, column + row * 9 + 9, playerInventoryX + column * SLOT_SPACING, playerInventoryY + row * SLOT_SPACING));
            }
        }

        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(inventory, column, playerInventoryX + column * SLOT_SPACING, hotbarY));
        }

        this.addDataSlots(this.data);
        this.addSlotListener(this);
        this.refreshOffers();
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
        if (container != this.enchantSlots || this.playerInventory.player.level().isClientSide) {
            return;
        }
        this.refreshOffers();
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((level, pos) -> this.clearContainer(player, this.enchantSlots));
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, BlockReg.ELEMENTAL_ENCHANTING_TABLE.get());
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack slotStack = slot.getItem();
        ItemStack copy = slotStack.copy();
        if (index < PLAYER_INV_START) {
            if (!this.moveItemStackTo(slotStack, PLAYER_INV_START, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else if (slotStack.is(Items.LAPIS_LAZULI)) {
            if (!this.moveItemStackTo(slotStack, LAPIS_SLOT, CATALYST_SLOT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (this.slots.get(CATALYST_SLOT).mayPlace(slotStack)) {
            if (!this.moveItemStackTo(slotStack, CATALYST_SLOT, UPGRADE_SLOT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (slotStack.is(UPGRADE_MATERIALS)) {
            if (!this.moveItemStackTo(slotStack, UPGRADE_SLOT, PLAYER_INV_START, false)) {
                return ItemStack.EMPTY;
            }
        } else if (this.slots.get(INPUT_SLOT).mayPlace(slotStack)) {
            if (!this.moveItemStackTo(slotStack, INPUT_SLOT, LAPIS_SLOT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index < PLAYER_HOTBAR_START) {
            if (!this.moveItemStackTo(slotStack, PLAYER_HOTBAR_START, this.slots.size(), false)) {
                return ItemStack.EMPTY;
            }
        } else if (!this.moveItemStackTo(slotStack, PLAYER_INV_START, PLAYER_HOTBAR_START, false)) {
            return ItemStack.EMPTY;
        }

        if (slotStack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return copy;
    }

    @Override
    public boolean clickMenuButton(Player player, int buttonId) {
        if (buttonId == APPLY_UPGRADE_BUTTON_ID) {
            return this.applyStoredUpgrade();
        }
        if (buttonId < 0 || buttonId >= OFFER_COUNT) {
            return false;
        }

        ItemStack itemStack = this.enchantSlots.getItem(INPUT_SLOT);
        ItemStack lapisStack = this.enchantSlots.getItem(LAPIS_SLOT);
        int lapisCost = getLapisCost(buttonId);
        int levelCost = getExperienceCost(buttonId);
        EnchantmentInstance offer = this.getOffer(buttonId);

        if (offer == null || itemStack.isEmpty() || !this.canAffordOffer(buttonId)) {
            return false;
        }

        this.access.execute((level, pos) -> {
            ItemStack resultStack = itemStack;
            if (itemStack.is(Items.BOOK)) {
                resultStack = EnchantedBookItem.createForEnchantment(offer);
                if (itemStack.has(DataComponents.CUSTOM_NAME)) {
                    resultStack.set(DataComponents.CUSTOM_NAME, itemStack.getHoverName());
                }
                this.enchantSlots.setItem(INPUT_SLOT, resultStack);
            } else {
                itemStack.enchant(offer.enchantment, offer.level);
            }

            this.getTableBlockEntity(level, pos).ifPresent(table -> {
                table.clearStoredUpgradeBonus();
                table.clearStoredCatalystData();
            });

            if (!player.getAbilities().instabuild) {
                lapisStack.shrink(lapisCost);
                player.giveExperienceLevels(-levelCost);
            }

            player.onEnchantmentPerformed(resultStack, levelCost);
            level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.1F + 0.9F);
            this.enchantSlots.setChanged();
            this.slotsChanged(this.enchantSlots);
        });
        return true;
    }

    public int getCost(int slot) {
        return this.data.get(OFFER_COST_DATA_START + slot);
    }

    public int getBookshelfCount() {
        return this.data.get(BOOKSHELF_DATA);
    }

    public int getUpgradeBonus() {
        return this.data.get(UPGRADE_BONUS_DATA);
    }

    public int getPendingUpgradeBonus() {
        return this.getUpgradeBonus(this.enchantSlots.getItem(UPGRADE_SLOT));
    }

    public int getCatalystBonus(int catalystOrdinal) {
        if (catalystOrdinal < 0 || catalystOrdinal >= Catalyst.values().length) {
            return 0;
        }
        return this.data.get(CATALYST_DATA_START + catalystOrdinal);
    }

    public boolean canStore() {
        return (this.getPendingUpgradeBonus() > 0
                && this.getUpgradeBonus() < MAX_STORED_UPGRADE_BONUS)
                || this.canStoreCatalyst();
    }

    public int getLapisCost(int slot) {
        return slot + 1;
    }

    public int getExperienceCost(int slot) {
        return slot + 1;
    }

    public boolean canAffordOffer(int slot) {
        if (slot < 0 || slot >= OFFER_COUNT) {
            return false;
        }
        EnchantmentInstance offer = this.getOffer(slot);
        if (offer == null) {
            return false;
        }
        Player player = this.playerInventory.player;
        if (player.getAbilities().instabuild) {
            return true;
        }
        ItemStack lapisStack = this.enchantSlots.getItem(LAPIS_SLOT);
        return lapisStack.getCount() >= this.getLapisCost(slot)
                && player.experienceLevel >= this.getExperienceCost(slot)
                && player.experienceLevel >= this.getCost(slot);
    }

    public @Nullable EnchantmentInstance getOffer(int slot) {
        int enchantmentIndex = this.data.get(OFFER_ENCHANTMENT_DATA_START + slot);
        int level = this.data.get(OFFER_LEVEL_DATA_START + slot);
        List<Holder<Enchantment>> enchantments = getEnchantmentCandidates(this.playerInventory.player.registryAccess());
        if (enchantmentIndex < 0 || enchantmentIndex >= enchantments.size() || level <= 0) {
            return null;
        }
        return new EnchantmentInstance(enchantments.get(enchantmentIndex), level);
    }

    private void refreshOffers() {
        if (this.playerInventory.player.level().isClientSide) {
            return;
        }

        ItemStack stack = this.enchantSlots.getItem(INPUT_SLOT);
        this.access.execute((level, pos) -> {
            int bookshelves = this.getBookshelfPower(level, pos);
            Optional<ElementalEnchantmentTableBlockEntity> table = this.getTableBlockEntity(level, pos);
            int upgradeBonus = table.map(ElementalEnchantmentTableBlockEntity::getStoredUpgradeBonus).orElse(0);
            Map<Integer, Integer> storedCatalystData = table.map(ElementalEnchantmentTableBlockEntity::getStoredCatalystData).orElse(Map.of());
            Map<Holder<Enchantment>, Integer> catalystBonusWeights = this.buildCatalystBonusWeights(storedCatalystData);
            this.data.set(BOOKSHELF_DATA, bookshelves);
            this.data.set(UPGRADE_BONUS_DATA, upgradeBonus);
            for (Catalyst catalyst : Catalyst.values()) {
                this.data.set(CATALYST_DATA_START + catalyst.ordinal(), storedCatalystData.getOrDefault(catalyst.ordinal(), 0));
            }

            if (stack.isEmpty() || (!stack.is(Items.BOOK) && !stack.isEnchantable())) {
                this.clearOffers(false);
                return;
            }

            this.random.setSeed(this.playerInventory.player.getEnchantmentSeed());
            int[] elementStats = EnchUtils.getElementStatsFromEquipment(this.playerInventory.player);
            Set<ResourceKey<Enchantment>> rolledSpecialLoot = this.getAvailableSpecialLoot(elementStats);
            List<EnchantmentInstance> fallbackOffers = this.getEligibleOffers(stack, 0, rolledSpecialLoot, false, true);

            for (int slot = 0; slot < OFFER_COUNT; ++slot) {
                int cost = this.calculateCost(slot, bookshelves, stack, upgradeBonus);
                EnchantmentInstance offer = this.pickOffer(stack, cost, rolledSpecialLoot, fallbackOffers, catalystBonusWeights);
                if (offer == null) {
                    this.data.set(OFFER_COST_DATA_START + slot, 0);
                    this.data.set(OFFER_ENCHANTMENT_DATA_START + slot, -1);
                    this.data.set(OFFER_LEVEL_DATA_START + slot, 0);
                } else {
                    ResourceKey<Enchantment> key = offer.enchantment.unwrapKey().orElse(null);
                    this.data.set(OFFER_COST_DATA_START + slot, cost);
                    this.data.set(OFFER_ENCHANTMENT_DATA_START + slot, key == null ? -1 : getEnchantmentCandidateIndices(this.playerInventory.player.registryAccess()).getOrDefault(key, -1));
                    this.data.set(OFFER_LEVEL_DATA_START + slot, offer.level);
                }
            }
        });
        this.broadcastChanges();
    }

    private void clearOffers(boolean clearBookshelves) {
        for (int slot = 0; slot < OFFER_COUNT; ++slot) {
            this.data.set(OFFER_COST_DATA_START + slot, 0);
            this.data.set(OFFER_ENCHANTMENT_DATA_START + slot, -1);
            this.data.set(OFFER_LEVEL_DATA_START + slot, 0);
        }
        if (clearBookshelves) {
            this.data.set(BOOKSHELF_DATA, 0);
        }
        this.broadcastChanges();
    }

    private int calculateCost(int slot, int bookshelves, ItemStack stack, int upgradeBonus) {
        int enchantability = Math.max(1, stack.getEnchantmentValue());
        int base = this.random.nextInt(8) + 1 + bookshelves + enchantability / 4 + upgradeBonus;
        return switch (slot) {
            case 0 -> Mth.clamp(base / 3 + upgradeBonus / 3, 1, 20 + upgradeBonus);
            case 1 -> Mth.clamp((base * 2) / 3 + 1 + upgradeBonus / 2, 4, 28 + upgradeBonus);
            default -> Mth.clamp(base + 3, 8, 30 + upgradeBonus);
        };
    }

    private int getUpgradeBonus(ItemStack upgradeStack) {
        if (!upgradeStack.is(UPGRADE_MATERIALS)) {
            return 0;
        }
        return Math.min(MAX_STORED_UPGRADE_BONUS, upgradeStack.getCount() * 2);
    }

    private boolean applyStoredUpgrade() {
        if (this.playerInventory.player.level().isClientSide) {
            return false;
        }

        ItemStack upgradeStack = this.enchantSlots.getItem(UPGRADE_SLOT);
        ItemStack catalystStack = this.enchantSlots.getItem(CATALYST_SLOT);

        return this.access.evaluate((level, pos) -> {
            Optional<ElementalEnchantmentTableBlockEntity> optionalTable = this.getTableBlockEntity(level, pos);
            if (optionalTable.isEmpty()) {
                return false;
            }

            ElementalEnchantmentTableBlockEntity table = optionalTable.get();
            boolean applied = false;

            if (upgradeStack.is(UPGRADE_MATERIALS)) {
                int storedBonus = table.getStoredUpgradeBonus();
                int remainingCapacity = MAX_STORED_UPGRADE_BONUS - storedBonus;
                if (remainingCapacity > 0) {
                    int appliedBonus = Math.min(remainingCapacity, this.getUpgradeBonus(upgradeStack));
                    if (appliedBonus > 0) {
                        int itemsToConsume = Math.min(upgradeStack.getCount(), Mth.ceil(appliedBonus / 2.0F));
                        if (itemsToConsume > 0) {
                            table.setStoredUpgradeBonus(storedBonus + itemsToConsume * 2);
                            upgradeStack.shrink(itemsToConsume);
                            applied = true;
                        }
                    }
                }
            }

            if (!this.hasStoredCatalystData() && this.slots.get(CATALYST_SLOT).mayPlace(catalystStack) && !catalystStack.isEmpty()) {
                ResourceLocation resourceLocation = catalystStack.getItemHolder().unwrapKey().map(ResourceKey::location).orElse(null);
                if (resourceLocation != null) {
                    Map<Catalyst, Integer> catalystMap = Catalyst.catalystDataMap.get(resourceLocation.toString());
                    if (catalystMap != null && !catalystMap.isEmpty()) {
                        Map<Integer, Integer> storedCatalystData = new HashMap<>();
                        for (Map.Entry<Catalyst, Integer> entry : catalystMap.entrySet()) {
                            if (entry.getKey() != null && entry.getValue() != null) {
                                storedCatalystData.put(entry.getKey().ordinal(), entry.getValue());
                            }
                        }
                        if (!storedCatalystData.isEmpty()) {
                            table.setStoredCatalystData(storedCatalystData);
                            catalystStack.shrink(1);
                            applied = true;
                        }
                    }
                }
            }

            if (!applied) {
                return false;
            }

            this.enchantSlots.setChanged();
            this.refreshOffers();
            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.BLOCKS, 0.8F, 1.1F);
            return true;
        }, false);
    }

    private Optional<ElementalEnchantmentTableBlockEntity> getTableBlockEntity(Level level, BlockPos pos) {
        return level.getBlockEntity(pos) instanceof ElementalEnchantmentTableBlockEntity table ? Optional.of(table) : Optional.empty();
    }

    private @Nullable EnchantmentInstance pickOffer(
            ItemStack stack,
            int cost,
            Set<ResourceKey<Enchantment>> rolledSpecialLoot,
            List<EnchantmentInstance> fallbackOffers,
            Map<Holder<Enchantment>, Integer> catalystBonusWeights
    ) {
        List<EnchantmentInstance> candidates = this.getEligibleOffers(stack, cost, rolledSpecialLoot, true, false);
        if (candidates.isEmpty()) {
            candidates = fallbackOffers;
        }
        if (candidates.isEmpty()) {
            return null;
        }
        return this.pickWeightedOffer(candidates, catalystBonusWeights);
    }

    private List<EnchantmentInstance> getEligibleOffers(
            ItemStack stack,
            int cost,
            Set<ResourceKey<Enchantment>> rolledSpecialLoot,
            boolean includeSpecialLoot,
            boolean ignoreCost
    ) {
        List<EnchantmentInstance> candidates = new ArrayList<>();

        for (Holder<Enchantment> enchantment : getEnchantmentCandidates(this.playerInventory.player.registryAccess())) {
            ResourceKey<Enchantment> key = enchantment.unwrapKey().orElse(null);
            if (key == null) {
                continue;
            }

            boolean isSpecialLoot = EnchUtils.getAllSpecialLootEnchantments().contains(key.location());
            if (includeSpecialLoot && isSpecialLoot && !rolledSpecialLoot.contains(key)) {
                continue;
            }
            if (!includeSpecialLoot && isSpecialLoot) {
                continue;
            }

            if (stack.is(Items.BOOK)) {
                if (!enchantment.is(EnchantmentTags.TRADEABLE) && !enchantment.is(EnchantmentTags.TREASURE)) {
                    continue;
                }
            } else if (!stack.supportsEnchantment(enchantment)) {
                continue;
            }

            for (int level = enchantment.value().getMaxLevel(); level >= 1; --level) {
                if (ignoreCost || cost >= enchantment.value().getMinCost(level) && cost <= enchantment.value().getMaxCost(level)) {
                    candidates.add(new EnchantmentInstance(enchantment, level));
                    break;
                }
            }
        }
        return candidates;
    }

    private EnchantmentInstance pickWeightedOffer(List<EnchantmentInstance> candidates, Map<Holder<Enchantment>, Integer> catalystBonusWeights) {
        int totalWeight = 0;
        for (EnchantmentInstance candidate : candidates) {
            int weight = getWeight(candidate.enchantment, catalystBonusWeights);
            if (weight > 0) {
                totalWeight += weight;
            }
        }

        if (totalWeight <= 0) {
            return candidates.get(candidates.size() - 1);
        }

        int chosen = this.random.nextInt(totalWeight);
        for (EnchantmentInstance candidate : candidates) {
            int weight = getWeight(candidate.enchantment, catalystBonusWeights);
            if (weight <= 0) {
                continue;
            }
            chosen -= weight;
            if (chosen < 0) {
                return candidate;
            }
        }
        return candidates.get(candidates.size() - 1);
    }

    private Set<ResourceKey<Enchantment>> getAvailableSpecialLoot(int[] elementStats) {
        Set<ResourceKey<Enchantment>> available = new HashSet<>();
        for (ResourceLocation enchantment : EnchUtils.getAllSpecialLootEnchantments()) {
            if (this.random.nextDouble() <= EnchUtils.getChanceForElementCondition(enchantment)
                    && EnchUtils.matchesElementCondition(elementStats, EnchUtils.getElementCondition(enchantment))) {
                available.add(ResourceKey.create(Registries.ENCHANTMENT, enchantment));
            }
        }
        return available;
    }

    private int countBookshelves(Level level, BlockPos pos) {
        int bookshelves = 0;
        for (int x = -1; x <= 1; ++x) {
            for (int z = -1; z <= 1; ++z) {
                if (x == 0 && z == 0) {
                    continue;
                }
                if (!level.isEmptyBlock(pos.offset(x, 0, z)) || !level.isEmptyBlock(pos.offset(x, 1, z))) {
                    continue;
                }
                bookshelves += this.countBookshelf(level, pos.offset(x * 2, 0, z * 2));
                bookshelves += this.countBookshelf(level, pos.offset(x * 2, 1, z * 2));
                if (x != 0 && z != 0) {
                    bookshelves += this.countBookshelf(level, pos.offset(x * 2, 0, z));
                    bookshelves += this.countBookshelf(level, pos.offset(x * 2, 1, z));
                    bookshelves += this.countBookshelf(level, pos.offset(x, 0, z * 2));
                    bookshelves += this.countBookshelf(level, pos.offset(x, 1, z * 2));
                }
            }
        }
        return bookshelves;
    }

    private int getBookshelfPower(Level level, BlockPos pos) {
        return Math.min(MAX_BOOKSHELF_LEVEL, this.countBookshelves(level, pos) * BOOKSHELF_LEVEL_PER_BLOCK);
    }

    private int countBookshelf(Level level, BlockPos pos) {
        return level.getBlockState(pos).is(Blocks.BOOKSHELF) ? 1 : 0;
    }

    private int getWeight(Holder<Enchantment> enchantment, Map<Holder<Enchantment>, Integer> catalystBonusWeights) {
        int weight = enchantment.value().getWeight();
        if (enchantment.is(EnchantmentTags.TREASURE) && !EnchUtils.getAllSpecialLootEnchantments().contains(enchantment.unwrapKey().orElseThrow().location())) {
            weight = 0;
        }
        return weight + catalystBonusWeights.getOrDefault(enchantment, 0);
    }

    private static List<Holder<Enchantment>> getEnchantmentCandidates(
            HolderLookup.Provider provider
    ) {
        HolderLookup.RegistryLookup<Enchantment> enchantments =
                provider.lookupOrThrow(Registries.ENCHANTMENT);

        List<Holder<Enchantment>> holders = new ArrayList<>();

        for (ResourceKey<Enchantment> key : EnchantmentReg.ALL) {
            Holder.Reference<Enchantment> holder =
                    enchantments.getOrThrow(key);

            if (holder.is(EnchantmentTags.IN_ENCHANTING_TABLE)) {
                holders.add(holder);
            }
        }

        return List.copyOf(holders);
    }

    private static Map<ResourceKey<Enchantment>, Integer> getEnchantmentCandidateIndices(
            HolderLookup.Provider provider
    ) {
        Map<ResourceKey<Enchantment>, Integer> indices = new HashMap<>();

        List<Holder<Enchantment>> candidates =
                getEnchantmentCandidates(provider);

        for (int index = 0; index < candidates.size(); ++index) {
            ResourceKey<Enchantment> key =
                    candidates.get(index).unwrapKey().orElse(null);

            if (key != null) {
                indices.put(key, index);
            }
        }

        return indices;
    }

    private boolean canStoreCatalyst() {
        if (this.hasAnyCatalystData()) {
            return false;
        }
        ItemStack catalystStack = this.enchantSlots.getItem(CATALYST_SLOT);
        if (catalystStack.isEmpty()) {
            return false;
        }
        ResourceLocation resourceLocation = catalystStack.getItemHolder().unwrapKey().map(ResourceKey::location).orElse(null);
        if (resourceLocation == null) {
            return false;
        }
        Map<Catalyst, Integer> catalystMap = Catalyst.catalystDataMap.get(resourceLocation.toString());
        if (catalystMap == null || catalystMap.isEmpty()) {
            return false;
        }
        for (Map.Entry<Catalyst, Integer> entry : catalystMap.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                return true;
            }
        }
        return false;
    }

    private boolean hasAnyCatalystData() {
        return this.hasSyncedCatalystData() || this.hasStoredCatalystData();
    }

    private boolean hasSyncedCatalystData() {
        for (Catalyst catalyst : Catalyst.values()) {
            if (this.data.get(CATALYST_DATA_START + catalyst.ordinal()) > 0) {
                return true;
            }
        }
        return false;
    }

    private boolean hasStoredCatalystData() {
        return this.access.evaluate((level, pos) -> this.getTableBlockEntity(level, pos)
                .map(ElementalEnchantmentTableBlockEntity::getStoredCatalystData)
                .map(storedCatalystData -> !storedCatalystData.isEmpty())
                .orElse(false), false);
    }

    private boolean canCatalystApply(int catalystOrdinal, Holder<Enchantment> enchantment) {
        if (catalystOrdinal < 0 || catalystOrdinal >= Catalyst.values().length) {
            return false;
        }

        Element element = EnchUtils.elementOf(enchantment);
        return switch (Catalyst.values()[catalystOrdinal]) {
            case AER -> element == Element.AER;
            case AQUA -> element == Element.AQUA;
            case IGNIS -> element == Element.IGNIS;
            case TERRA -> element == Element.TERRA;
            case HOLY -> element == Element.HOLY;
            case TWISTED -> element == Element.TWISTED;
            case TREASURE -> enchantment.is(EnchantmentTags.TREASURE)
                    && !EnchUtils.getAllSpecialLootEnchantments().contains(enchantment.unwrapKey().orElseThrow().location());
            case SPECIAL -> EnchUtils.getAllSpecialLootEnchantments().contains(enchantment.unwrapKey().orElseThrow().location());
        };
    }

    private Map<Holder<Enchantment>, Integer> buildCatalystBonusWeights(Map<Integer, Integer> storedCatalystData) {
        if (storedCatalystData.isEmpty()) {
            return Map.of();
        }

        Map<Holder<Enchantment>, Integer> bonusWeights = new HashMap<>();
        for (Holder<Enchantment> enchantment : getEnchantmentCandidates(this.playerInventory.player.registryAccess())) {
            int bonus = 0;
            for (Map.Entry<Integer, Integer> entry : storedCatalystData.entrySet()) {
                Integer weight = entry.getValue();
                if (weight != null && this.canCatalystApply(entry.getKey(), enchantment)) {
                    bonus += weight;
                }
            }
            if (bonus > 0) {
                bonusWeights.put(enchantment, bonus);
            }
        }
        return bonusWeights;
    }

    @Override
    public void slotChanged(AbstractContainerMenu menu, int slotIndex, ItemStack stack) {
    }

    @Override
    public void dataChanged(AbstractContainerMenu menu, int dataSlotIndex, int value) {
    }
}
