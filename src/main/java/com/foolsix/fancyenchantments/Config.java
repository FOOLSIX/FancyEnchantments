package com.foolsix.fancyenchantments;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_INCOMPATIBILITY =
            BUILDER.comment("Whether Fancy Enchantments elemental opposites are incompatible.")
                    .define("enableIncompatibility", true);

    static final ModConfigSpec SPEC = BUILDER.build();

    private Config() {
    }
}
