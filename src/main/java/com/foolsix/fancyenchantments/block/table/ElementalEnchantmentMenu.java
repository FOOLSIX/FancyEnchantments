package com.foolsix.fancyenchantments.block.table;

import com.foolsix.fancyenchantments.block.ModBlockReg;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.*;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import resource.catalyst.Catalyst;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
public class ElementalEnchantmentMenu extends AbstractContainerMenu {
    static final int OFFER_COUNT = 3;
    private static final int BOOKSHELF_LEVEL_PER_BLOCK = 2;
    private static final int MAX_BOOKSHELF_LEVEL = 30;
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
    static final TagKey<Item> UPGRADE_MATERIALS = ItemTags.create(new ResourceLocation("fancyenchantments", "upgrade_materials"));
    private static List<Enchantment> enchantmentCandidates;

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

    public ElementalEnchantmentMenu(int windowId, Inventory inventory, BlockPos pos) {
        this(windowId, inventory, ContainerLevelAccess.create(inventory.player.level(), pos));
    }

    public ElementalEnchantmentMenu(int windowId, Inventory inventory, ContainerLevelAccess access) {
        super(ModBlockReg.ELEMENTAL_ENCHANTMENT_MENU.get(), windowId);
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
                ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey(stack.getItem());
                if (resourceLocation == null) {
                    return false;
                }
                return Catalyst.catalystDataMap.containsKey(resourceLocation.toString());
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

    private void refreshOffers() {
        if (this.playerInventory.player.level().isClientSide) {
            return;
        }

        ItemStack stack = this.enchantSlots.getItem(INPUT_SLOT);
        this.access.execute((level, pos) -> {
            int bookshelves = this.getBookshelfPower(level, pos);
            int upgradeBonus = this.getTableBlockEntity(level, pos)
                    .map(ElementalEnchantmentTableBlockEntity::getStoredUpgradeBonus)
                    .orElse(0);
            Map<Integer, Integer> storedCatalystData = this.getTableBlockEntity(level, pos)
                    .map(ElementalEnchantmentTableBlockEntity::getStoredCatalystData)
                    .orElse(Map.of());
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
            Set<Enchantment> rolledSpecialLoot = this.getAvailableSpecialLoot(elementStats);
            List<EnchantmentInstance> fallbackOffers = this.getEligibleOffers(stack, 0, rolledSpecialLoot, false, true);

            for (int slot = 0; slot < OFFER_COUNT; ++slot) {
                int cost = this.calculateCost(slot, bookshelves, stack, upgradeBonus);
                EnchantmentInstance offer = this.pickOffer(stack, cost, rolledSpecialLoot, fallbackOffers);
                if (offer == null) {
                    this.data.set(OFFER_COST_DATA_START + slot, 0);
                    this.data.set(OFFER_ENCHANTMENT_DATA_START + slot, -1);
                    this.data.set(OFFER_LEVEL_DATA_START + slot, 0);
                } else {
                    this.data.set(OFFER_COST_DATA_START + slot, cost);
                    this.data.set(OFFER_ENCHANTMENT_DATA_START + slot, getEnchantmentCandidates().indexOf(offer.enchantment));
                    this.data.set(OFFER_LEVEL_DATA_START + slot, offer.level);
                }
            }
        });
        this.broadcastChanges();
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

        if (offer == null || itemStack.isEmpty()) {
            return false;
        }
        if (!this.canAffordOffer(buttonId)) {
            return false;
        }

        this.access.execute((level, pos) -> {
            ItemStack resultStack = itemStack;
            if (itemStack.is(Items.BOOK)) {
                resultStack = new ItemStack(Items.ENCHANTED_BOOK);
                if (itemStack.hasCustomHoverName()) {
                    resultStack.setHoverName(itemStack.getHoverName());
                }
                EnchantedBookItem.addEnchantment(resultStack, offer);
                this.enchantSlots.setItem(INPUT_SLOT, resultStack);
            } else {
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);
                enchantments.put(offer.enchantment, offer.level);
                EnchantmentHelper.setEnchantments(enchantments, itemStack);
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

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, ModBlockReg.ELEMENTAL_ENCHANTING_TABLE.get());
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((level, pos) -> this.clearContainer(player, this.enchantSlots));
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
            if (!this.moveItemStackTo(slotStack, LAPIS_SLOT, UPGRADE_SLOT, true)) {
                return ItemStack.EMPTY;
            }
        } else if (this.slots.get(CATALYST_SLOT).mayPlace(slotStack)) {
            if (!this.moveItemStackTo(slotStack, CATALYST_SLOT, UPGRADE_SLOT, true)) {
                return ItemStack.EMPTY;
            }
        } else if (slotStack.is(UPGRADE_MATERIALS)) {
            if (!this.moveItemStackTo(slotStack, UPGRADE_SLOT, PLAYER_INV_START, true)) {
                return ItemStack.EMPTY;
            }
        } else if (this.slots.get(INPUT_SLOT).mayPlace(slotStack)) {
            if (!this.moveItemStackTo(slotStack, INPUT_SLOT, LAPIS_SLOT, true)) {
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
                && this.getUpgradeBonus() < ElementalEnchantmentTableBlockEntity.MAX_STORED_UPGRADE_BONUS)
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

    @Nullable
    public EnchantmentInstance getOffer(int slot) {
        int enchantmentIndex = this.data.get(OFFER_ENCHANTMENT_DATA_START + slot);
        int level = this.data.get(OFFER_LEVEL_DATA_START + slot);
        List<Enchantment> enchantments = getEnchantmentCandidates();
        if (enchantmentIndex < 0 || enchantmentIndex >= enchantments.size() || level <= 0) {
            return null;
        }
        return new EnchantmentInstance(enchantments.get(enchantmentIndex), level);
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
        return Math.min(ElementalEnchantmentTableBlockEntity.MAX_STORED_UPGRADE_BONUS, upgradeStack.getCount() * 2);
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
                int remainingCapacity = ElementalEnchantmentTableBlockEntity.MAX_STORED_UPGRADE_BONUS - storedBonus;
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
                ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey(catalystStack.getItem());
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
        if (level.getBlockEntity(pos) instanceof ElementalEnchantmentTableBlockEntity table) {
            return Optional.of(table);
        }
        return Optional.empty();
    }

    @Nullable
    private EnchantmentInstance pickOffer(ItemStack stack, int cost, Set<Enchantment> rolledSpecialLoot, List<EnchantmentInstance> fallbackOffers) {
        List<EnchantmentInstance> candidates = this.getEligibleOffers(stack, cost, rolledSpecialLoot, true, false);
        if (candidates.isEmpty()) {
            candidates = fallbackOffers;
        }
        if (candidates.isEmpty()) {
            return null;
        }

        int totalWeight = 0;
        for (EnchantmentInstance candidate : candidates) {
            totalWeight += getWeight(candidate.enchantment);
        }
        return this.pickWeightedOffer(candidates, totalWeight);
    }

    private List<EnchantmentInstance> getEligibleOffers(ItemStack stack, int cost, Set<Enchantment> rolledSpecialLoot, boolean includeSpecialLoot,boolean ignoreCost) {
        List<EnchantmentInstance> candidates = new ArrayList<>();

        for (Enchantment enchantment : getEnchantmentCandidates()) {
            if (includeSpecialLoot && enchantment instanceof FEBaseEnchantment fe && fe.isSpecialLoot() && !rolledSpecialLoot.contains(enchantment)) {
                continue;
            }
            if (stack.is(Items.BOOK)) {
                if (!enchantment.isAllowedOnBooks()) {
                    continue;
                }
            } else if (!enchantment.canEnchant(stack)) {
                continue;
            }

            for (int level = enchantment.getMaxLevel(); level >= 1; --level) {
                if (ignoreCost || cost >= enchantment.getMinCost(level) && cost <= enchantment.getMaxCost(level)) {
                    candidates.add(new EnchantmentInstance(enchantment, level));
                    break;
                }
            }
        }
        return candidates;
    }

    private EnchantmentInstance pickWeightedOffer(List<EnchantmentInstance> candidates, int totalWeight) {
        int chosen = this.random.nextInt(totalWeight);
        for (EnchantmentInstance candidate : candidates) {
            chosen -= getWeight(candidate.enchantment);
            if (chosen < 0) {
                return candidate;
            }
        }
        return candidates.get(candidates.size() - 1);
    }

    private Set<Enchantment> getAvailableSpecialLoot(int[] elementStats) {
        Set<Enchantment> available = new HashSet<>();
        for (Enchantment enchantment : getEnchantmentCandidates()) {
            if (!(enchantment instanceof FEBaseEnchantment fe) || !fe.isSpecialLoot()) {
                continue;
            }
            if (this.random.nextDouble() < fe.getChestGenerationProbability() || !EnchUtils.matchesElementCondition(elementStats, fe.getChestGenerationCondition())) {
                continue;
            }
            available.add(enchantment);
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

    private int getWeight(Enchantment enchantment) {
        int weight = switch (enchantment.getRarity()) {
            case COMMON -> 20;
            case UNCOMMON -> 10;
            case RARE -> 4;
            case VERY_RARE -> 1;
        };
        return weight + this.getCatalystBonusWeight(enchantment);
    }

    private static List<Enchantment> getEnchantmentCandidates() {
        if (enchantmentCandidates == null) {
            enchantmentCandidates = EnchantmentReg.ENCHANTMENTS.getEntries().stream()
                    .map(RegistryObject::get)
                    .filter(e -> e instanceof FEBaseEnchantment fe && fe.isInElementalTable())
                    .toList();
        }
        return enchantmentCandidates;
    }

    private boolean canStoreCatalyst() {
        if (this.hasAnyCatalystData()) {
            return false;
        }
        ItemStack catalystStack = this.enchantSlots.getItem(CATALYST_SLOT);
        if (catalystStack.isEmpty()) {
            return false;
        }
        ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey(catalystStack.getItem());
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

    private boolean canCatalystApply(int catalystOrdinal, Enchantment enchantment) {
        if (catalystOrdinal < 0 || catalystOrdinal >= Catalyst.values().length) {
            return false;
        }
        boolean result = false;
        switch (Catalyst.values()[catalystOrdinal]) {
            case AER -> result = enchantment instanceof AerEnchantment;
            case AQUA -> result = enchantment instanceof AquaEnchantment;
            case IGNIS -> result = enchantment instanceof IgnisEnchantment;
            case TERRA -> result = enchantment instanceof TerraEnchantment;
            case HOLY -> result = enchantment instanceof HolyEnchantment;
            case TWISTED -> result = enchantment instanceof TwistedEnchantment;
            case TREASURE -> result = enchantment instanceof FEBaseEnchantment fe && !fe.isSpecialLoot() && fe.isTreasureOnly();
            case SPECIAL -> result = enchantment instanceof FEBaseEnchantment fe && fe.isSpecialLoot();
        }
        return result;
    }

    private int getCatalystBonusWeight(Enchantment enchantment) {
        return this.access.evaluate((level, pos) -> this.getTableBlockEntity(level, pos)
                .map(ElementalEnchantmentTableBlockEntity::getStoredCatalystData)
                .map(storedCatalystData -> {
                    int bonus = 0;
                    for (Map.Entry<Integer, Integer> entry : storedCatalystData.entrySet()) {
                        if (entry.getValue() != null && this.canCatalystApply(entry.getKey(), enchantment)) {
                            bonus += entry.getValue();
                        }
                    }
                    return bonus;
                })
                .orElse(0), 0);
    }
}
