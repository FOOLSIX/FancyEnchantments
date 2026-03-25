package resource.catalyst;

import java.util.Collections;
import java.util.Map;

public class CatalystData {
    private final String type;
    private final String item;
    private final Map<String, Integer> catalysis;

    public CatalystData(String type, String item, Map<String, Integer> catalysis) {
        this.type = type;
        this.item = item;
        this.catalysis = catalysis;
    }

    public String getType() {
        return type;
    }

    public String getItem() {
        return item;
    }

    public Map<String, Integer> getCatalysis() {
        if (catalysis == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(catalysis);
    }
}
