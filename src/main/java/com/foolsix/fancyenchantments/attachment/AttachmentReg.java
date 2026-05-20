package com.foolsix.fancyenchantments.attachment;

import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

public final class AttachmentReg {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> TIME_TO_LIVE =
            ATTACHMENTS.register(
                    "time_to_live",
                    () -> AttachmentType.builder(() -> -1)
                            .serialize(Codec.INT, ttl -> ttl != -1)
                            .sync(ByteBufCodecs.INT)
                            .build()
            );

    private AttachmentReg() {
    }

    public static void register(IEventBus eventBus) {
        ATTACHMENTS.register(eventBus);
    }
}
