package games.enchanted.eg_text_customiser.common.pack;

import games.enchanted.eg_text_customiser.common.fake_style.FakeStyle;
import games.enchanted.eg_text_customiser.common.pack.colour_override.ColourOverrideDefinition;
import games.enchanted.eg_text_customiser.common.pack.style_override.StyleOverrideDefinition;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TextOverrideManager {
    private static final Map<ResourceLocation, ColourOverrideDefinition> COLOUR_OVERRIDE_DEFINITIONS = new HashMap<>();

    private static final Map<FakeStyle, ColourOverrideDefinition> MATCHED_STYLES = new HashMap<>();
    private static final HashSet<FakeStyle> UNMATCHED_STYLED = new HashSet<>();

    private static @Nullable ColourOverrideDefinition getDefinitionForStyle(FakeStyle style) {
        if(UNMATCHED_STYLED.contains(style)) {
            return null;
        }
        for (Map.Entry<ResourceLocation, ColourOverrideDefinition> entry : COLOUR_OVERRIDE_DEFINITIONS.entrySet()) {
            boolean match = entry.getValue().styleMatches(style);
            if(match) {
                MATCHED_STYLES.put(style, entry.getValue());
                return entry.getValue();
            }
        }
        UNMATCHED_STYLED.add(style);
        return null;
    }

    public static synchronized FakeStyle applyFakeColourOverride(FakeStyle originalStyle) {
        if(MATCHED_STYLES.containsKey(originalStyle)) {
            return MATCHED_STYLES.get(originalStyle).applyToStyleIfMatching(originalStyle);
        }

        ColourOverrideDefinition overrideDefinition = getDefinitionForStyle(originalStyle);
        if(overrideDefinition == null) {
            return originalStyle;
        }
        return overrideDefinition.applyToStyleIfMatching(originalStyle);
    }

    public static synchronized Style applyColourOverride(Style originalStyle) {
        FakeStyle fakeStyle = FakeStyle.fromStyle(originalStyle);

        if(MATCHED_STYLES.containsKey(fakeStyle)) {
            return MATCHED_STYLES.get(fakeStyle).applyToStyleIfMatching(originalStyle);
        }

        ColourOverrideDefinition overrideDefinition = getDefinitionForStyle(fakeStyle);
        if(overrideDefinition == null) {
            return originalStyle;
        }
        return overrideDefinition.applyToStyleIfMatching(originalStyle);
    }

    public static void registerOverride(ResourceLocation location, StyleOverrideDefinition definition) {
        throw new NotImplementedException("StyleOverrideDefinition not implemented");
    }
    public static void registerOverride(ResourceLocation location, ColourOverrideDefinition definition) {
        COLOUR_OVERRIDE_DEFINITIONS.put(location, definition);
    }

    public static void clearCaches() {
        COLOUR_OVERRIDE_DEFINITIONS.clear();
        MATCHED_STYLES.clear();
        UNMATCHED_STYLED.clear();
    }
}
