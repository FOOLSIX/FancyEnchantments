package com.foolsix.fancyenchantments.enchantment.util;

import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public final class EnchUtils {
    public static final Component CURSE_SUFFIX = Component.translatable("(Curse)").withStyle(ChatFormatting.RED);
    public static final String MOD_NAME_PREFIX = "Fancy Enchantment:";
    public static final int ELEMENT_COUNT = Element.values().length;
    public static final int[] EMPTY_CONDITION = new int[ELEMENT_COUNT];

    public static final Predicate<Entity> VISIBLE_HOSTILE =
            entity -> !entity.isSpectator() && isHostileToPlayer(entity);

    public static Optional<ResourceKey<Enchantment>> key(Holder<Enchantment> enchantment) {
        return enchantment.unwrapKey();
    }

    public static int getEnchantmentLevel(ResourceKey<Enchantment> enchantment, LivingEntity livingEntity) {
        HolderLookup.RegistryLookup<Enchantment> enchantments = livingEntity.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        var enchant = enchantments.getOrThrow(enchantment);
        return EnchantmentHelper.getEnchantmentLevel(enchant, livingEntity);
    }

    public static int getEnchantmentLevel(ResourceKey<Enchantment> enchantment, ItemStack stack, @Nullable HolderLookup.Provider registries) {
        if (registries != null) {
            HolderLookup.RegistryLookup<Enchantment> enchantments = registries.lookupOrThrow(Registries.ENCHANTMENT);
            return stack.getEnchantmentLevel(enchantments.getOrThrow(enchantment));
        }

        for (var entry : enchantmentsOn(stack).entrySet()) {
            if (matchesKey(entry.getKey(), enchantment)) {
                return entry.getIntValue();
            }
        }
        return 0;
    }

    public static @Nullable EquipmentSlot getEquipmentSlot(ItemStack stack) {
        EquipmentSlot slot = stack.getEquipmentSlot();
        if (slot == null) {
            Equipable equipable = Equipable.get(stack);

            if (equipable != null) {
                slot = equipable.getEquipmentSlot();
            } else {
                return null;
            }
        }
        return slot;
    }

    public static @Nullable Element elementOf(Holder<Enchantment> key) {
        for (Element element : Element.values()) {
            if (key.is(element.tag())) {
                return element;
            }
        }
        return null;
    }


    public static MutableComponent applyElementStyle(Element element, MutableComponent component) {
        return component.withStyle(element.chatFormatting());
    }

    public static boolean matchesKey(Holder<Enchantment> enchantment, ResourceKey<Enchantment> key) {
        return enchantment.unwrapKey().filter(key::equals).isPresent();
    }

    public static void pushLiving(LivingEntity living, double x, double y, double z) {
        living.push(x, y, z);
        if (living instanceof ServerPlayer player) {
            player.connection.send(
                    new ClientboundSetEntityMotionPacket(player)
            );
        }
    }

    public static Component getMixedColorFullName(Component name, Element first, Element second, long gameTime) {
        MutableComponent copy = name.copy();
        copy.setStyle(Style.EMPTY.withColor(gradualColor(first.color(), second.color(), 180, gameTime)));
        return copy;
    }

    public static List<BlockPos> getRandomValidPos(Entity entity, Level level, int tries) {
        BlockPos origin = entity.blockPosition();
        RandomSource random = RandomSource.create();
        List<BlockPos> positions = new ArrayList<>();
        while (tries-- > 0) {
            BlockPos pos = new BlockPos(origin.getX() + random.nextInt(3) - 1, origin.getY() + random.nextInt(3) - 1, origin.getZ() + random.nextInt(2));
            if (level.isEmptyBlock(pos) && !level.isEmptyBlock(pos.below())) {
                positions.add(pos);
            }
        }
        return positions;
    }

    public static void generateSimpleParticleAroundEntity(Entity entity, SimpleParticleType type) {
        generateSimpleParticleAroundEntity(entity, type, 20, 0.2D, 0.7D, 0.2D, 0);
    }

    public static void generateSimpleParticleAroundEntity(Entity entity, SimpleParticleType type, int count, double xOffset, double yOffset, double zOffset, double speed) {
        if (entity.level() instanceof ServerLevel level) {
            level.sendParticles(type, entity.getX(), entity.getY(), entity.getZ(), count, xOffset, yOffset, zOffset, speed);
        }
    }

    public static boolean isHostileToPlayer(Entity entity) {
        return entity instanceof Enemy && !(entity instanceof NeutralMob neutralMob && !neutralMob.isAngry());
    }

    public static boolean isHostileToLivingEntity(Entity entity, LivingEntity living) {
        return entity instanceof Enemy && !(entity instanceof NeutralMob neutralMob && !neutralMob.isAngryAt(living));
    }

    public static @Nullable LivingEntity getLookAtLivingEntity(LivingEntity viewer, float partialTicks, double reachDistance) {
        Vec3 eye = viewer.getEyePosition();
        Vec3 look = viewer.getViewVector(partialTicks);
        Vec3 end = eye.add(look.x * reachDistance, look.y * reachDistance, look.z * reachDistance);
        AABB searchBox = viewer.getBoundingBox().expandTowards(look.scale(reachDistance)).inflate(1.0D, 1.0D, 1.0D);
        EntityHitResult result = ProjectileUtil.getEntityHitResult(viewer, eye, end, searchBox, VISIBLE_HOSTILE, reachDistance * reachDistance);
        return result != null && result.getEntity() instanceof LivingEntity living ? living : null;
    }

    public static int gradualColor(int color1, int color2, int cycleLength, long gameTime) {
        double t = Math.cos((Math.abs(gameTime % cycleLength - cycleLength / 2.0D)) / (cycleLength / 4.0D) * Math.PI) / 2.0D + 0.5D;
        int red = lerp((color1 >> 16) & 0xFF, (color2 >> 16) & 0xFF, t);
        int green = lerp((color1 >> 8) & 0xFF, (color2 >> 8) & 0xFF, t);
        int blue = lerp(color1 & 0xFF, color2 & 0xFF, t);
        return (red << 16) | (green << 8) | blue;
    }

    public static int[] getElementStatsFromEquipment(@Nullable LivingEntity living) {
        int[] stats = new int[ELEMENT_COUNT];
        if (living == null) {
            return stats;
        }

        addItemElementStats(stats, living.getMainHandItem());
        addItemElementStats(stats, living.getOffhandItem());
        for (ItemStack armorStack : living.getArmorSlots()) {
            addItemElementStats(stats, armorStack);
        }
        return stats;
    }

    public static boolean matchesElementCondition(int[] elementStats, int[] condition) {
        if (elementStats.length != condition.length) {
            return false;
        }
        for (int index = 0; index < condition.length; ++index) {
            if (condition[index] > elementStats[index]) {
                return false;
            }
        }
        return true;
    }

    public static Collection<FEBaseEnchantment> getAllSpecialLootEnchantments() {
        return FEEnchantments.all()
                .stream()
                .filter(FEBaseEnchantment::isSpecialLoot)
                .toList();
    }

    public static Optional<FEBaseEnchantment> getRandomModEnchantment(RandomSource random) {
        List<FEBaseEnchantment> enchantments = new ArrayList<>(FEEnchantments.all());
        if (enchantments.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(enchantments.get(random.nextInt(enchantments.size())));
    }

    public static ItemEnchantments enchantmentsOn(ItemStack stack) {
        return stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
    }

    public static ItemEnchantments storedEnchantmentsOn(ItemStack stack) {
        return stack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);
    }

    public static String getLangName(String name) {
        StringBuilder builder = new StringBuilder(name);
        for (int index = 0; index < builder.length(); ++index) {
            char current = builder.charAt(index);
            if (index == 0 || builder.charAt(index - 1) == ' ') {
                builder.setCharAt(index, Character.toUpperCase(current));
            } else if (current == '_') {
                builder.setCharAt(index, ' ');
            }
        }
        return builder.toString();
    }

    public static void modifyEffectLevel(LivingEntity living, Holder<MobEffect> effect, int amplifier) {
        MobEffectInstance instance = living.getEffect(effect);
        if (instance != null) {
            living.forceAddEffect(new MobEffectInstance(effect, instance.getDuration(), amplifier), null);
        }
    }

    public static boolean canBlock(LivingEntity holder, @Nullable Vec3 sourcePosition) {
        if (sourcePosition == null) {
            return false;
        }
        Vec3 viewVector = holder.getViewVector(1.0F);
        Vec3 entityPosition = holder.position();
        Vec3 direction = new Vec3(entityPosition.x - sourcePosition.x, 0, entityPosition.z - sourcePosition.z);
        double length = viewVector.length() * direction.length();
        if (length < 1.0E-4D) {
            return false;
        }
        double angle = Math.abs(180.0D - Math.acos(direction.dot(viewVector) / length) * Mth.RAD_TO_DEG);
        return 45.0D >= angle;
    }

    private static void addItemElementStats(int[] stats, ItemStack stack) {
        addEnchantmentsElementStats(stats, enchantmentsOn(stack));
        addEnchantmentsElementStats(stats, storedEnchantmentsOn(stack));
    }

    private static void addEnchantmentsElementStats(int[] stats, ItemEnchantments enchantments) {
        for (var entry : enchantments.entrySet()) {
            Element element = elementOf(entry.getKey());
            if (element != null) {
                stats[element.ordinal()] += entry.getIntValue();
            }
        }
    }


    private static int lerp(int start, int end, double amount) {
        return (int) (start + (end - start) * amount);
    }
}
