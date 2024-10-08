package com.foolsix.fancyenchantments.enchantment.util;

import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.ENCHANTMENTS;

public class EnchUtils {
    public static final Component CURSE_SUFFIX = Component.translatable("(Curse)").withStyle(ChatFormatting.RED);
    public static final String MOD_NAME_PREFIX = "Fancy Enchantment:";

    public static final int ELEMENT_COUNT = Element.values().length;
    public static final int[] EMPTY_CONDITION = new int[ELEMENT_COUNT];
    private static List<Enchantment> FE_DISCOVERABLE_ENCHANTMENT = null;
    private static List<Enchantment> SPECIAL_LOOT_ENCHANTMENT = null;

    public enum Element {
        AER, AQUA, IGNIS, TERRA, HOLY, TWISTED;

        @Nullable
        public static Element getElement(Enchantment enchantment) {
            if (enchantment instanceof AerEnchantment) {
                return AER;
            }
            if (enchantment instanceof AquaEnchantment) {
                return AQUA;
            }
            if (enchantment instanceof IgnisEnchantment) {
                return IGNIS;
            }
            if (enchantment instanceof TerraEnchantment) {
                return TERRA;
            }
            if (enchantment instanceof HolyEnchantment) {
                return HOLY;
            }
            if (enchantment instanceof TwistedEnchantment) {
                return TWISTED;
            }
            return null;
        }

        public static @NotNull ChatFormatting getChatFormatting(Element element) {
            ChatFormatting chatFormatting = ChatFormatting.RESET;
            switch (element) {
                case AER -> chatFormatting = ChatFormatting.YELLOW;
                case AQUA -> chatFormatting = ChatFormatting.AQUA;
                case IGNIS -> chatFormatting = ChatFormatting.GOLD;
                case TERRA -> chatFormatting = ChatFormatting.GREEN;
                case HOLY -> chatFormatting = ChatFormatting.WHITE;
                case TWISTED -> chatFormatting = ChatFormatting.DARK_PURPLE;
            }
            return chatFormatting;
        }

        public static int getColor(Element element) {
            int color = 0x0;
            switch (element) {
                case AER -> color = 16777045;
                case AQUA -> color = 5636095;
                case IGNIS -> color = 16755200;
                case TERRA -> color = 5635925;
                case HOLY -> color = 16777215;
                case TWISTED -> color = 11141290;
            }
            return color;
        }
    }

    public static Component getMixedColorFullName(Component name, Element e1, Element e2) {
        if (name instanceof MutableComponent mutableName) {
            mutableName.setStyle(Style.EMPTY.withColor(gradualColor(Element.getColor(e1), Element.getColor(e2), 180)));
        }
        return name;
    }

    public static final Predicate<Entity> VISIBLE_HOSTILE =
            entity -> !entity.isSpectator() && isHostileToPlayer(entity);

    public static List<BlockPos> getRandomValidPos(Entity entity, Level world, int tryTimes) {
        BlockPos originPos = entity.blockPosition();
        RandomSource random = RandomSource.create();
        List<BlockPos> blockPosList = new ArrayList<>();
        while (tryTimes-- > 0) {
            BlockPos pos = new BlockPos(originPos.getX() + random.nextInt(3) - 1, originPos.getY() + random.nextInt(3) - 1, originPos.getZ() + random.nextInt(2));
            if (world.isEmptyBlock(pos) && !world.isEmptyBlock(pos.below())) {
                blockPosList.add(pos);
            }
        }
        return blockPosList;
    }

    public static void generateSimpleParticleAroundEntity(Entity entity, SimpleParticleType type) {
        if (entity.getLevel() instanceof ServerLevel level) {
            level.sendParticles(type, entity.getX(), entity.getY(), entity.getZ(),
                    30, 0.2D, 0.7D, 0.2D, 0);
        }
    }

    public static void generateSimpleParticleAroundEntity(Entity entity, SimpleParticleType type, int pParticleCount, double pXOffset, double pYOffset, double pZOffset, double pSpeed) {
        if (entity.getLevel() instanceof ServerLevel level) {
            level.sendParticles(type, entity.getX(), entity.getY(), entity.getZ(),
                    pParticleCount, pXOffset, pYOffset, pZOffset, pSpeed);
        }
    }


    public static boolean isHostileToPlayer(Entity entity) {
        return entity instanceof Enemy && !(entity instanceof NeutralMob neutralMob && !neutralMob.isAngry());
    }

    public static boolean isHostileToLivingEntity(Entity entity, LivingEntity living) {
        return entity instanceof Enemy && !(entity instanceof NeutralMob neutralMob && !neutralMob.isAngryAt(living));
    }

    public static LivingEntity getLookAtLivingEntity(LivingEntity viewer, float partialTicks, double reachDistance) {
        Vec3 eye = viewer.getEyePosition();
        Vec3 look = viewer.getViewVector(partialTicks);
        Vec3 end = eye.add(look.x * reachDistance, look.y * reachDistance, look.z * reachDistance);
        AABB searchBox =
                viewer.getBoundingBox().expandTowards(look.scale(reachDistance)).inflate(1.0D, 1.0D, 1.0D);
        EntityHitResult result = ProjectileUtil.getEntityHitResult(viewer, eye, end, searchBox, VISIBLE_HOSTILE, reachDistance * reachDistance);
        if (result == null) {
            return null;
        } else if (result.getEntity() instanceof LivingEntity)
            return (LivingEntity) result.getEntity();

        return null;
    }


    public static int lerp(int start, int end, double t) {
        return (int) (start + (end - start) * t);
    }

    public static int gradualColor(int color1, int color2, int cycleLength) {
        double t = 0;

        if (Minecraft.getInstance().level != null) {
            t = Math.cos((Math.abs(Minecraft.getInstance().level.getGameTime() % cycleLength - cycleLength / 2)) / ((double) cycleLength / 4) * Math.PI) / 2 + 0.5;
        }

        int red1 = (color1 >> 16) & 0xFF;
        int green1 = (color1 >> 8) & 0xFF;
        int blue1 = color1 & 0xFF;

        int red2 = (color2 >> 16) & 0xFF;
        int green2 = (color2 >> 8) & 0xFF;
        int blue2 = color2 & 0xFF;

        int red = lerp(red1, red2, t);
        int green = lerp(green1, green2, t);
        int blue = lerp(blue1, blue2, t);

        return (red << 16) | (green << 8) | blue;
    }

    public static @Nullable Enchantment getRandomModEnchantment(RandomSource rand) {
        if (FE_DISCOVERABLE_ENCHANTMENT == null) {
            FE_DISCOVERABLE_ENCHANTMENT =
                    ENCHANTMENTS.getEntries().stream()
                            .map(RegistryObject::get)
                            .filter(Enchantment::isDiscoverable)
                            .toList();
        }

        //it's ok to set all enchantments undiscoverable
        if (FE_DISCOVERABLE_ENCHANTMENT.isEmpty()) return null;

        return FE_DISCOVERABLE_ENCHANTMENT.get(rand.nextInt(FE_DISCOVERABLE_ENCHANTMENT.size()));
    }

    public static @Nonnull List<Enchantment> getAllSpecialLootEnchantment() {
        if (SPECIAL_LOOT_ENCHANTMENT == null) {
            SPECIAL_LOOT_ENCHANTMENT =
                    ENCHANTMENTS.getEntries().stream()
                            .map(RegistryObject::get)
                            .filter(e -> e instanceof FEBaseEnchantment fe && fe.isSpecialLoot())
                            .toList();
        }

        return SPECIAL_LOOT_ENCHANTMENT;
    }

    public static String getLangName(String name) {
        StringBuilder sb = new StringBuilder(name);
        for (int i = 0; i < sb.length(); ++i) {
            char c = sb.charAt(i);
            if (i == 0 || sb.charAt(i - 1) == ' ') {
                sb.setCharAt(i, Character.toUpperCase(c));
            } else if (c == '_') {
                sb.setCharAt(i, ' ');
            }
        }
        return sb.toString();
    }

    public static boolean removeEnchantment(ItemStack stack, Enchantment enchantment) {
        Map<Enchantment, Integer> enchantments = stack.getAllEnchantments();
        boolean isRemoved = enchantments.remove(enchantment) != null;
        EnchantmentHelper.setEnchantments(enchantments, stack);
        return isRemoved;
    }

    public void modifyEffectLevel(LivingEntity living, MobEffect effect, int amplifier) {
        MobEffectInstance instance = living.getEffect(effect);
        if (instance != null) {
            living.forceAddEffect(new MobEffectInstance(effect, instance.getDuration(), amplifier), null);
        }
    }

    public static boolean canBlock(LivingEntity holder, @Nullable Vec3 sourcePosition) {
        // source position should never be null (checked by livingentity) but safety as its marked nullable
        if (sourcePosition == null) {
            return false;
        }
        float blockAngle = 45;
        // want the angle between the view vector and the
        Vec3 viewVector = holder.getViewVector(1.0f);
        Vec3 entityPosition = holder.position();
        Vec3 direction = new Vec3(entityPosition.x - sourcePosition.x, 0, entityPosition.z - sourcePosition.z);
        double length = viewVector.length() * direction.length();
        // prevent zero vector from messing with us
        if (length < 1.0E-4D) {
            return false;
        }
        // acos will return between 90 and 270, we want an absolute angle from 0 to 180
        double angle = Math.abs(180 - Math.acos(direction.dot(viewVector) / length) * Mth.RAD_TO_DEG);
        return blockAngle >= angle;
    }
}
