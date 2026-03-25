package resource.catalyst;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static resource.catalyst.Catalyst.catalystDataMap;

public class CatalystResourceLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();
    public static final CatalystResourceLoader INSTANCE = new CatalystResourceLoader();

    private CatalystResourceLoader() {
        super(GSON, "catalysts");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profiler) {
        catalystDataMap.clear();

        for (JsonElement jsonElement : jsons.values()) {
            CatalystData catalystData = GSON.fromJson(jsonElement, CatalystData.class);
            if (catalystData == null || catalystData.getItem() == null) {
                continue;
            }
            Map<Catalyst, Integer> catalystIntegerHashMap = new HashMap<>();
            for (Map.Entry<String, Integer> entry : catalystData.getCatalysis().entrySet()) {
                if (entry.getKey() == null || entry.getValue() == null) {
                    continue;
                }
                Catalyst catalyst = Catalyst.getCatalyst(entry.getKey());
                if (catalyst != null) {
                    catalystIntegerHashMap.put(catalyst, entry.getValue());
                }
            }
            if (!catalystIntegerHashMap.isEmpty()) {
                catalystDataMap.put(catalystData.getItem(), catalystIntegerHashMap);
            }
        }
    }
}
