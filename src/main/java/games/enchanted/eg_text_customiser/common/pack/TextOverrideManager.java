package games.enchanted.eg_text_customiser.common.pack;

import games.enchanted.eg_text_customiser.common.Logging;
import games.enchanted.eg_text_customiser.common.duck.StyleAdditions;
import games.enchanted.eg_text_customiser.common.fake_style.FakeStyle;
import games.enchanted.eg_text_customiser.common.fake_style.SignTextData;
import games.enchanted.eg_text_customiser.common.fake_style.SpecialTextColour;
import games.enchanted.eg_text_customiser.common.pack.colour_override.ColourOverrideDefinition;
import games.enchanted.eg_text_customiser.common.util.ColourUtil;
import games.enchanted.eg_text_customiser.common.util.Profiling;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TextOverrideManager {
    private static final Map<ResourceLocation, ColourOverrideDefinition> COLOUR_OVERRIDE_DEFINITIONS = new HashMap<>();

    private static final Map<FakeStyle, FakeStyle> MATCHED_STYLES = new HashMap<>();
    private static final HashSet<FakeStyle> UNMATCHED_STYLED = new HashSet<>();

    private static void logStyle(FakeStyle style, @Nullable ResourceLocation matchedTo) {
        String matchedPart = matchedTo != null ? "(and matched to '" + matchedTo + "')" : "(didn't match)";
        Logging.info("Seen {} style:\n{}", matchedPart, style.formattedString());
    }

    private static @Nullable ColourOverrideDefinition getDefinitionForStyle(@NotNull FakeStyle style) {
        Profiling.push("eg_text_customiser:check_unmatched");
        if(UNMATCHED_STYLED.contains(style)) {
            Profiling.pop();
            return null;
        }
        Profiling.pop();

        Profiling.push("eg_text_customiser:loop");
        for (Map.Entry<ResourceLocation, ColourOverrideDefinition> entry : COLOUR_OVERRIDE_DEFINITIONS.entrySet()) {
            Profiling.push("eg_text_customiser:check_match");
            if(entry.getValue().styleMatches(style)) {
                logStyle(style, entry.getKey());
                MATCHED_STYLES.put(style, entry.getValue().applyToStyle(style));
                Profiling.pop();
                return entry.getValue();
            }
            Profiling.pop();
        }
        Profiling.pop();

        logStyle(style, null);
        UNMATCHED_STYLED.add(style);
        return null;
    }

    public static synchronized FakeStyle applyFakeColourOverride(@NotNull FakeStyle originalStyle) {
        // TODO: implement better hash functions for FakeStyle and ColourOverrideDefinition
        Profiling.push("eg_text_customiser:check_matched");
        @Nullable FakeStyle matchedStyle = MATCHED_STYLES.get(originalStyle);
        if(matchedStyle != null) {
            Profiling.pop();
            return matchedStyle;
        }
        Profiling.pop();

        Profiling.push("eg_text_customiser:get_definition");
        ColourOverrideDefinition overrideDefinition = getDefinitionForStyle(originalStyle);
        Profiling.pop();
        Profiling.push("eg_text_customiser:check_null_or_apply");
        if(overrideDefinition == null) {
            Profiling.pop();
            return originalStyle;
        }
        Profiling.pop();
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

    public static void replaceColour(int color, int shadowColor, Style style, boolean hasShadow, ColourApplier colourApplier, ShadowColourApplier shadowColourApplier) {
        Profiling.push("eg_text_customiser:replace_glyph_colour");
        int noAlphaColour = ColourUtil.removeAlpha(color);

        SpecialTextColour comparisonTextColour;

        SignTextData signTextData = ((StyleAdditions) style).eg_text_customiser$getSignTextData();
        boolean currentlyRegularColour;
        boolean currentlyOutlineColour;
        if(signTextData != null) {
            currentlyRegularColour = noAlphaColour == ColourUtil.removeAlpha(signTextData.darkColour());
            currentlyOutlineColour = signTextData.outlineColour() != null && noAlphaColour == ColourUtil.removeAlpha(signTextData.outlineColour());
        } else {
            currentlyRegularColour = false;
            currentlyOutlineColour = false;
        }

        boolean isValidSignText = currentlyRegularColour || currentlyOutlineColour;

        if(!isValidSignText && style.getColor() != null) {
            comparisonTextColour = SpecialTextColour.fromTextColor(style.getColor());
        } else if(isValidSignText) {
            comparisonTextColour = SpecialTextColour.fromSignTextData(signTextData, currentlyOutlineColour);
        } else {
            comparisonTextColour = new SpecialTextColour(noAlphaColour);
        }

        int noAlphaShadowColour = ColourUtil.removeAlpha(shadowColor);
        FakeStyle fakeStyle = new FakeStyle(comparisonTextColour, shadowColor == 0 ? null : noAlphaShadowColour, style.isBold(), style.isItalic(), style.isUnderlined(), style.isStrikethrough(), style.isObfuscated(), style.getFont());
        FakeStyle newStyle = TextOverrideManager.applyFakeColourOverride(fakeStyle);

        int colorAlpha = ColourUtil.extractAlpha(color);
        if(newStyle.colour() != null) {
            int newColour = newStyle.colour().safeGetAsRGB();
            colourApplier.applyColour(ColourUtil.applyAlpha(newColour, colorAlpha));
        }

        if(!hasShadow && !newStyle.properties().forceEnableShadow()) {
            Profiling.pop();
            return;
        }

        int shadowAlpha = newStyle.properties().forceEnableShadow() ? colorAlpha : ColourUtil.extractAlpha(shadowColor);
        if(newStyle.colour() != null && newStyle.shadowColour() != null && newStyle.shadowColour() == noAlphaShadowColour && newStyle.properties().autoGenerateShadow()) {
            // shadow colour has not changed but regular colour has, change shadow based on new colour
            shadowColourApplier.applyShadowColour(ColourUtil.applyAlpha(ColourUtil.darkenRGB(newStyle.colour().safeGetAsRGB(), newStyle.properties().autoShadowMultiplier()), shadowAlpha));
        }
        else if(newStyle.shadowColour() != null ) {
            // shadow colour has changed, replace it
            shadowColourApplier.applyShadowColour(ColourUtil.applyAlpha(newStyle.shadowColour(), shadowAlpha));
        }
        Profiling.pop();
    }

    @FunctionalInterface
    public interface ColourApplier {
        void applyColour(Integer colourARGB);
    }
    @FunctionalInterface
    public interface ShadowColourApplier {
        void applyShadowColour(Integer shadowARGB);
    }
}
