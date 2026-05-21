package com.foolsix.fancyenchantments.resource.catalyst;

import java.util.Collections;
import java.util.Map;

public record CatalystData(String type, String item, Map<String, Integer> catalysis) {
    public Map<String, Integer> catalysisOrEmpty() {
        return this.catalysis == null ? Collections.emptyMap() : Collections.unmodifiableMap(this.catalysis);
    }
}
