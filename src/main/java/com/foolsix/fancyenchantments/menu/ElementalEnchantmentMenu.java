package com.foolsix.fancyenchantments.menu;

import com.foolsix.fancyenchantments.block.ModBlockReg;
import com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElementalEnchantmentMenu extends AbstractContainerMenu {
    private static final int OFFER_COUNT = 3;
    private static List<Enchantment> discoverableEnchantments;

    private final Container enchantSlots = new SimpleContainer(2) {
        @Override
        public void setChanged() {
            super.setChanged();
            ElementalEnchantmentMenu.this.slotsChanged(this);
        }
    };
    private final ContainerData data = new SimpleContainerData(10);
    private final ContainerLevelAccess access;
    private final Inventory playerInventory;
    private final RandomSource random = RandomSource.create();

    public ElementalEnchantmentMenu(int windowId, Inventory inventory, BlockPos pos) {
        this(windowId, inventory, ContainerLevelAccess.create(inventory.player.level(), pos));
    }

    public ElementalEnchantmentMenu(int windowId, Inventory inventory, ContainerLevelAccess access) {
        super(ModBlockReg.ELEMENTAL_ENCHANTMENT_MENU.get(), windowId);
        this.access = access;
        this.playerInventory = inventory;

        this.addSlot(new Slot(this.enchantSlots, 0, 15, 47) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Items.BOOK) || stack.isEnchantable();
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.enchantSlots, 1, 35, 47) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Items.LAPIS_LAZULI);
            }
        });

        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(inventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }

        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(inventory, column, 8 + column * 18, 142));
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

        ItemStack stack = this.enchantSlots.getItem(0);
        this.access.execute((level, pos) -> {
            int bookshelves = countBookshelves(level, pos);
            this.data.set(9, bookshelves);

            if (stack.isEmpty() || (!stack.is(Items.BOOK) && !stack.isEnchantable())) {
                this.clearOffers(false);
                return;
            }

            this.random.setSeed(this.playerInventory.player.getEnchantmentSeed() + stack.hashCode());

            for (int slot = 0; slot < OFFER_COUNT; ++slot) {
                int cost = this.calculateCost(slot, bookshelves, stack);
                EnchantmentInstance offer = this.pickOffer(stack, cost);
                if (offer == null) {
                    this.data.set(slot, 0);
                    this.data.set(3 + slot, -1);
                    this.data.set(6 + slot, 0);
                } else {
                    this.data.set(slot, cost);
                    this.data.set(3 + slot, getDiscoverableEnchantments().indexOf(offer.enchantment));
                    this.data.set(6 + slot, offer.level);
                }
            }
        });
        this.broadcastChanges();
    }

    @Override
    public boolean clickMenuButton(Player player, int buttonId) {
        if (buttonId < 0 || buttonId >= OFFER_COUNT) {
            return false;
        }

        ItemStack itemStack = this.enchantSlots.getItem(0);
        ItemStack lapisStack = this.enchantSlots.getItem(1);
        int lapisCost = getLapisCost(buttonId);
        int levelCost = getExperienceCost(buttonId);
        EnchantmentInstance offer = this.getOffer(buttonId);

        if (offer == null || itemStack.isEmpty()) {
            return false;
        }
        if (!player.getAbilities().instabuild && (lapisStack.getCount() < lapisCost || player.experienceLevel < levelCost)) {
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
                this.enchantSlots.setItem(0, resultStack);
            } else {
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);
                enchantments.put(offer.enchantment, offer.level);
                EnchantmentHelper.setEnchantments(enchantments, itemStack);
            }

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
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack slotStack = slot.getItem();
        ItemStack copy = slotStack.copy();
        if (index < 2) {
            if (!this.moveItemStackTo(slotStack, 2, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else if (slotStack.is(Items.LAPIS_LAZULI)) {
            if (!this.moveItemStackTo(slotStack, 1, 2, true)) {
                return ItemStack.EMPTY;
            }
        } else if (this.slots.get(0).mayPlace(slotStack)) {
            if (!this.moveItemStackTo(slotStack, 0, 1, true)) {
                return ItemStack.EMPTY;
            }
        } else if (index < 29) {
            if (!this.moveItemStackTo(slotStack, 29, this.slots.size(), false)) {
                return ItemStack.EMPTY;
            }
        } else if (!this.moveItemStackTo(slotStack, 2, 29, false)) {
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
        return this.data.get(slot);
    }

    public int getBookshelfCount() {
        return this.data.get(9);
    }

    public int getLapisCost(int slot) {
        return slot + 1;
    }

    public int getExperienceCost(int slot) {
        return slot + 1;
    }

    @Nullable
    public EnchantmentInstance getOffer(int slot) {
        int enchantmentIndex = this.data.get(3 + slot);
        int level = this.data.get(6 + slot);
        List<Enchantment> enchantments = getDiscoverableEnchantments();
        if (enchantmentIndex < 0 || enchantmentIndex >= enchantments.size() || level <= 0) {
            return null;
        }
        return new EnchantmentInstance(enchantments.get(enchantmentIndex), level);
    }

    private void clearOffers(boolean clearBookshelves) {
        for (int slot = 0; slot < OFFER_COUNT; ++slot) {
            this.data.set(slot, 0);
            this.data.set(3 + slot, -1);
            this.data.set(6 + slot, 0);
        }
        if (clearBookshelves) {
            this.data.set(9, 0);
        }
        this.broadcastChanges();
    }

    private int calculateCost(int slot, int bookshelves, ItemStack stack) {
        int enchantability = Math.max(1, stack.getEnchantmentValue());
        int base = this.random.nextInt(8) + 1 + bookshelves + enchantability / 4;
        return switch (slot) {
            case 0 -> Mth.clamp(base + 3, 8, 30);
            case 1 -> Mth.clamp((base * 2) / 3 + 1, 4, 28);
            default -> Mth.clamp(base / 3, 1, 20);
        };
    }

    @Nullable
    private EnchantmentInstance pickOffer(ItemStack stack, int cost) {
        List<EnchantmentInstance> candidates = new ArrayList<>();
        int totalWeight = 0;
        Map<Enchantment, Integer> existingEnchantments = EnchantmentHelper.getEnchantments(stack);

        for (Enchantment enchantment : getDiscoverableEnchantments()) {
            if (enchantment.isTreasureOnly()) {
                continue;
            }
            if (stack.is(Items.BOOK)) {
                if (!enchantment.isAllowedOnBooks()) {
                    continue;
                }
            } else if (!enchantment.canEnchant(stack)) {
                continue;
            }
            if (!EnchantmentHelper.isEnchantmentCompatible(existingEnchantments.keySet(), enchantment)) {
                continue;
            }

            for (int level = enchantment.getMaxLevel(); level >= 1; --level) {
                if (cost >= enchantment.getMinCost(level) && cost <= enchantment.getMaxCost(level)) {
                    candidates.add(new EnchantmentInstance(enchantment, level));
                    totalWeight += getWeight(enchantment);
                    break;
                }
            }
        }

        if (candidates.isEmpty()) {
            return null;
        }

        int chosen = this.random.nextInt(totalWeight);
        for (EnchantmentInstance candidate : candidates) {
            chosen -= getWeight(candidate.enchantment);
            if (chosen < 0) {
                return candidate;
            }
        }
        return candidates.get(candidates.size() - 1);
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

    private int countBookshelf(Level level, BlockPos pos) {
        return level.getBlockState(pos).is(Blocks.BOOKSHELF) ? 1 : 0;
    }

    private int getWeight(Enchantment enchantment) {
        return switch (enchantment.getRarity()) {
            case COMMON -> 10;
            case UNCOMMON -> 7;
            case RARE -> 4;
            case VERY_RARE -> 2;
        };
    }

    private static List<Enchantment> getDiscoverableEnchantments() {
        if (discoverableEnchantments == null) {
            discoverableEnchantments = EnchantmentReg.ENCHANTMENTS.getEntries().stream()
                    .map(RegistryObject::get)
                    .filter(Enchantment::isDiscoverable)
                    .toList();
        }
        return discoverableEnchantments;
    }
}
