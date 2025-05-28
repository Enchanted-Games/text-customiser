package games.enchanted.eg_text_customiser.common.text_override;

import games.enchanted.eg_text_customiser.common.Logging;
import games.enchanted.eg_text_customiser.common.text_override.fake_style.FakeStyle;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TextOverrideManager {
    private static final Map<ResourceLocation, TextOverrideDefinition> TEXT_OVERRIDE_DEFINITIONS = new HashMap<>();

    private static final Map<Style, FakeStyle> STYLE_CACHE = new HashMap<>();
    private static final Set<FakeStyle> UNMATCHED_STYLES = new HashSet<>();
    private static final Map<FakeStyle, TextOverrideDefinition> MATCHED_STYLES = new HashMap<>();

    public static boolean styleHasOverride(Style style) {
        FakeStyle fakeStyle = STYLE_CACHE.get(style);
        if(fakeStyle == null) {
            Logging.info("Cached stayle: {}", style);
            fakeStyle = FakeStyle.fromStyle(style);
            STYLE_CACHE.put(style, fakeStyle);
        }

        if(UNMATCHED_STYLES.contains(fakeStyle)) {
            return false;
        }

        TextOverrideDefinition cachedDefinition = MATCHED_STYLES.get(fakeStyle);
        if(cachedDefinition != null) {
            return true;
        }

        for (Map.Entry<ResourceLocation, TextOverrideDefinition> entry : TEXT_OVERRIDE_DEFINITIONS.entrySet()) {
            boolean hasMatched = entry.getValue().styleMatches(fakeStyle);
            if(!hasMatched) {
                UNMATCHED_STYLES.add(fakeStyle);
                return false;
            }

            MATCHED_STYLES.put(fakeStyle, entry.getValue());
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
