package games.enchanted.eg_text_customiser.common.text_override;

import games.enchanted.eg_text_customiser.common.Logging;
import games.enchanted.eg_text_customiser.common.text_override.fake_style.FakeStyle;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class TextOverrideManager {
    private static final Map<ResourceLocation, TextOverrideDefinition> TEXT_OVERRIDE_DEFINITIONS = new HashMap<>();

    private static final Map<FakeStyle, ResourceLocation> STYLE_TO_TEXT_OVERRIDE_LOCATION = new HashMap<>();
    private static final Map<Style, FakeStyle> STYLE_CACHE = new HashMap<>();

    public static boolean styleHasOverride(Style style) {
        FakeStyle fakeStyle = STYLE_CACHE.get(style);
        if(fakeStyle == null) {
            Logging.info("Cached style: {}", style);
            fakeStyle = FakeStyle.fromStyle(style);
            STYLE_CACHE.put(style, fakeStyle);
        }

        ResourceLocation cachedDefinitionLocation = STYLE_TO_TEXT_OVERRIDE_LOCATION.get(fakeStyle);
        if(cachedDefinitionLocation != null) {
            return TEXT_OVERRIDE_DEFINITIONS.get(cachedDefinitionLocation).styleMatches(fakeStyle);
        }

        for (Map.Entry<ResourceLocation, TextOverrideDefinition> entry : TEXT_OVERRIDE_DEFINITIONS.entrySet()) {
            boolean hasMatched = entry.getValue().styleMatches(fakeStyle);
            // TODO: cache styles that have no override definition
            if(!hasMatched) return false;

            STYLE_TO_TEXT_OVERRIDE_LOCATION.put(fakeStyle, entry.getKey());
            return true;
        }

        return false;
    }

    public static void clearStyleCache() {
        STYLE_CACHE.clear();
    }

    public static void registerOverride(ResourceLocation location, TextOverrideDefinition definition) {
        TEXT_OVERRIDE_DEFINITIONS.put(location, definition);
    }

    public static void clearOverrides() {
        TEXT_OVERRIDE_DEFINITIONS.clear();
    }
}
