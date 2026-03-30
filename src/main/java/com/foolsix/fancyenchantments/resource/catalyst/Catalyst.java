package com.foolsix.fancyenchantments.resource.catalyst;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public enum Catalyst {
    AER, AQUA, IGNIS, TERRA, HOLY, TWISTED, TREASURE, SPECIAL;
    public static final Map<String, Map<Catalyst, Integer>> catalystDataMap = new HashMap<>();
    @Nullable
    public static Catalyst getCatalyst(String catalystName) {
        Catalyst ret = null;
        switch (catalystName.toLowerCase()) {
            case "aer" -> ret = AER;
            case "aqua" -> ret = AQUA;
            case "ignis" -> ret = IGNIS;
            case "terra" -> ret = TERRA;
            case "holy" -> ret = HOLY;
            case "twisted" -> ret = TWISTED;
            case "treasure" -> ret = TREASURE;
            case "special" -> ret = SPECIAL;
        }
        return ret;
    }
}
