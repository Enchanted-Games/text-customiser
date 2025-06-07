package games.enchanted.eg_text_customiser.common.pack;

import games.enchanted.eg_text_customiser.common.fake_style.FakeStyle;
import games.enchanted.eg_text_customiser.common.pack.colour_override.ColourOverrideDefinition;
import games.enchanted.eg_text_customiser.common.pack.style_override.StyleOverrideDefinition;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.NotImplementedException;

import java.util.HashMap;
import java.util.Map;

public class TextOverrideManager {
    private static final Map<ResourceLocation, ColourOverrideDefinition> COLOUR_OVERRIDE_DEFINITIONS = new HashMap<>();

    private static final Map<FakeStyle, ColourOverrideDefinition> MATCHING_STYLES = new HashMap<>();

    public static Style applyColourOverride(Style originalStyle) {
        FakeStyle fakeStyle = FakeStyle.fromStyle(originalStyle);

        if(MATCHING_STYLES.containsKey(fakeStyle)) {
            return MATCHING_STYLES.get(fakeStyle).applyToStyleIfMatching(originalStyle);
        }

        for (Map.Entry<ResourceLocation, ColourOverrideDefinition> entry : COLOUR_OVERRIDE_DEFINITIONS.entrySet()) {
            boolean match = entry.getValue().styleMatches(fakeStyle);
            if(!match) continue;
            MATCHING_STYLES.put(fakeStyle, entry.getValue());
            return entry.getValue().applyToStyleIfMatching(originalStyle);
        }

        return originalStyle;
    }

    public static void registerOverride(ResourceLocation location, StyleOverrideDefinition definition) {
        throw new NotImplementedException("StyleOverrideDefinition not implemented");
    }
    public static void registerOverride(ResourceLocation location, ColourOverrideDefinition definition) {
        COLOUR_OVERRIDE_DEFINITIONS.put(location, definition);
    }

    public static void clearCaches() {
        COLOUR_OVERRIDE_DEFINITIONS.clear();
        MATCHING_STYLES.clear();
    }
}
