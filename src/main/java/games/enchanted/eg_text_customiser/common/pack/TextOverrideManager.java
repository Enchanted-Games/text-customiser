package games.enchanted.eg_text_customiser.common.pack;

import games.enchanted.eg_text_customiser.common.fake_style.FakeStyle;
import games.enchanted.eg_text_customiser.common.pack.colour_override.ColourOverrideDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.Profiler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TextOverrideManager {
    private static final Map<ResourceLocation, ColourOverrideDefinition> COLOUR_OVERRIDE_DEFINITIONS = new HashMap<>();

    private static final Map<FakeStyle, FakeStyle> MATCHED_STYLES = new HashMap<>();
    private static final HashSet<FakeStyle> UNMATCHED_STYLED = new HashSet<>();

    private static @Nullable ColourOverrideDefinition getDefinitionForStyle(@NotNull FakeStyle style) {
        Profiler.get().push("eg_text_customiser:check_unmatched");
        if(UNMATCHED_STYLED.contains(style)) {
            Profiler.get().pop();
            return null;
        }
        Profiler.get().pop();

        Profiler.get().push("eg_text_customiser:loop");
        for (Map.Entry<ResourceLocation, ColourOverrideDefinition> entry : COLOUR_OVERRIDE_DEFINITIONS.entrySet()) {
            Profiler.get().push("eg_text_customiser:check_match");
            if(entry.getValue().styleMatches(style)) {
                MATCHED_STYLES.put(style, entry.getValue().applyToStyle(style));
                Profiler.get().pop();
                return entry.getValue();
            }
            Profiler.get().pop();
        }
        Profiler.get().pop();

        UNMATCHED_STYLED.add(style);
        return null;
    }

    public static synchronized FakeStyle applyFakeColourOverride(@NotNull FakeStyle originalStyle) {
        // TODO: implement better hash functions for FakeStyle and ColourOverrideDefinition
        Profiler.get().push("eg_text_customiser:check_matched");
        @Nullable FakeStyle matchedStyle = MATCHED_STYLES.get(originalStyle);
        if(matchedStyle != null) {
            Profiler.get().pop();
            return matchedStyle;
        }
        Profiler.get().pop();

        Profiler.get().push("eg_text_customiser:get_definition");
        ColourOverrideDefinition overrideDefinition = getDefinitionForStyle(originalStyle);
        Profiler.get().pop();
        Profiler.get().push("eg_text_customiser:check_null_or_apply");
        if(overrideDefinition == null) {
            Profiler.get().pop();
            return originalStyle;
        }
        Profiler.get().pop();
        return overrideDefinition.applyToStyle(originalStyle);
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
