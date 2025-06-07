package games.enchanted.eg_text_customiser.common.pack;

import games.enchanted.eg_text_customiser.common.pack.colour_override.ColourOverrideDefinition;
import games.enchanted.eg_text_customiser.common.pack.style_override.StyleOverrideDefinition;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.NotImplementedException;

import java.util.HashMap;
import java.util.Map;

public class TextOverrideManager {
    private static final Map<ResourceLocation, ColourOverrideDefinition> COLOUR_OVERRIDE_DEFINITIONS = new HashMap<>();



    public static void registerOverride(ResourceLocation location, StyleOverrideDefinition definition) {
        throw new NotImplementedException("StyleOverrideDefinition not implemented");
    }
    public static void registerOverride(ResourceLocation location, ColourOverrideDefinition definition) {
        COLOUR_OVERRIDE_DEFINITIONS.put(location, definition);
    }

    public static void clearCaches() {
        COLOUR_OVERRIDE_DEFINITIONS.clear();
    }
}
