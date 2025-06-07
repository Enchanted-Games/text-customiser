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
    private static final Map<FakeStyle, Style> MATCHED_STYLES_fake_to_real = new HashMap<>();
    private static final Map<Style, FakeStyle> MATCHED_STYLES = new HashMap<>();

    public static Style applyStyleOverride(Style originalStyle) {
//        if(originalStyle.isEmpty()) return originalStyle;

        FakeStyle fakeStyle = STYLE_CACHE.get(originalStyle);
        if(fakeStyle == null) {
            Logging.info("Cached style: {}", originalStyle);
            fakeStyle = FakeStyle.fromStyle(originalStyle);
            STYLE_CACHE.put(originalStyle, fakeStyle);
        }

        if(UNMATCHED_STYLES.contains(fakeStyle)) {
            return originalStyle;
        }

        Style cachedStyle = MATCHED_STYLES_fake_to_real.get(fakeStyle);
        if(cachedStyle != null) {
            return cachedStyle;
        }

        for (Map.Entry<ResourceLocation, TextOverrideDefinition> entry : TEXT_OVERRIDE_DEFINITIONS.entrySet()) {
            boolean hasMatched = entry.getValue().styleMatches(fakeStyle);
            if(!hasMatched) {
                UNMATCHED_STYLES.add(fakeStyle);
                return originalStyle;
            }

            Style modifiedStyle = entry.getValue().applyToStyle(originalStyle);
            MATCHED_STYLES_fake_to_real.put(fakeStyle, modifiedStyle);
            return modifiedStyle;
        }

        return originalStyle;
    }

    public static FakeStyle getOverrideForStyle(Style originalStyle) {
        FakeStyle fakeStyle = STYLE_CACHE.get(originalStyle);
        if(fakeStyle == null) {
            Logging.info("Cached style: {}", originalStyle);
            fakeStyle = FakeStyle.fromStyle(originalStyle);
            STYLE_CACHE.put(originalStyle, fakeStyle);
        }

        if(UNMATCHED_STYLES.contains(fakeStyle)) {
            return fakeStyle;
        }

        FakeStyle cachedStyle = MATCHED_STYLES.get(originalStyle);
        if(cachedStyle != null) {
            return cachedStyle;
        }

        for (Map.Entry<ResourceLocation, TextOverrideDefinition> entry : TEXT_OVERRIDE_DEFINITIONS.entrySet()) {
            boolean hasMatched = entry.getValue().styleMatches(fakeStyle);
            if(!hasMatched) {
                UNMATCHED_STYLES.add(fakeStyle);
                return fakeStyle;
            }

            FakeStyle modifiedStyle = FakeStyle.fromStyle(entry.getValue().applyToStyle(originalStyle));
            MATCHED_STYLES.put(originalStyle, modifiedStyle);
            return fakeStyle;
        }

        return fakeStyle;
    }

    public static void registerOverride(ResourceLocation location, TextOverrideDefinition definition) {
        TEXT_OVERRIDE_DEFINITIONS.put(location, definition);
    }

    public static void clearCaches() {
        TEXT_OVERRIDE_DEFINITIONS.clear();
        STYLE_CACHE.clear();
        UNMATCHED_STYLES.clear();
        MATCHED_STYLES.clear();
        MATCHED_STYLES_fake_to_real.clear();
    }
}
