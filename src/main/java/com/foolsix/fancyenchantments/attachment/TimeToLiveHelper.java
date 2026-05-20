package com.foolsix.fancyenchantments.attachment;

import net.minecraft.world.entity.player.Player;

public final class TimeToLiveHelper {
    private TimeToLiveHelper() {
    }

    public static int getTtl(Player player) {
        return player.getData(AttachmentReg.TIME_TO_LIVE);
    }

    public static void setTtl(Player player, int ttl) {
        player.setData(AttachmentReg.TIME_TO_LIVE, Math.max(ttl, -1));
    }

    public static void subTtl(Player player, int amount) {
        setTtl(player, getTtl(player) - amount);
    }
}
