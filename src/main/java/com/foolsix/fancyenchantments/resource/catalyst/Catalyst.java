package com.foolsix.fancyenchantments.resource.catalyst;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum Catalyst {
    AER,
    AQUA,
    IGNIS,
    TERRA,
    HOLY,
    TWISTED,
    TREASURE,
    SPECIAL;

    public static final Map<String, Map<Catalyst, Integer>> catalystDataMap = new HashMap<>();

    public static @Nullable Catalyst getCatalyst(String catalystName) {
        if (catalystName == null) {
            return null;
        }

        return switch (catalystName.toLowerCase(Locale.ROOT)) {
            case "aer" -> AER;
            case "aqua" -> AQUA;
            case "ignis" -> IGNIS;
            case "terra" -> TERRA;
            case "holy" -> HOLY;
            case "twisted" -> TWISTED;
            case "treasure" -> TREASURE;
            case "special" -> SPECIAL;
            default -> null;
        };
    }
}
