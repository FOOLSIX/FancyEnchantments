package com.foolsix.fancyenchantments.network;

import com.foolsix.fancyenchantments.FancyEnchantments;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class Networking {
    private static final String NETWORK_VERSION = "1.0";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(FancyEnchantments.MODID, "network"),
            () -> NETWORK_VERSION, NETWORK_VERSION::equals, NETWORK_VERSION::equals);

    public static void initNetwork() {
        int index = 0;
        CHANNEL.registerMessage(index++, TimeToLivePacket.class, TimeToLivePacket::encode, TimeToLivePacket::decode, TimeToLivePacket::handle);
    }
}
