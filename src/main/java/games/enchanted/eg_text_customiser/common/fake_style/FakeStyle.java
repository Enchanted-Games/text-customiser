package games.enchanted.eg_text_customiser.common.fake_style;

import games.enchanted.eg_text_customiser.common.pack.colour_override.ColourOverrideDefinition;
import games.enchanted.eg_text_customiser.common.util.ColourUtil;
import net.minecraft.network.chat.FontDescription;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record FakeStyle(
    @Nullable SpecialTextColour colour,
    @Nullable Integer shadowColour,
    @Nullable Boolean bold,
    @Nullable Boolean italic,
    @Nullable Boolean underlined,
    @Nullable Boolean strikethrough,
    @Nullable Boolean obfuscated,
    @Nullable FontDescription font,
    @Nullable DecorationType decorationType,
    @Nullable Integer codepoint,
    ColourOverrideDefinition.PropertiesPart properties
) {
    public FakeStyle(
        @Nullable SpecialTextColour colour,
        @Nullable Integer shadowColour,
        @Nullable Boolean bold,
        @Nullable Boolean italic,
        @Nullable Boolean underlined,
        @Nullable Boolean strikethrough,
        @Nullable Boolean obfuscated,
        @Nullable FontDescription font,
        @Nullable DecorationType decorationType,
        @Nullable Integer codepoint
    ) {
        this(colour, shadowColour, bold, italic, underlined, strikethrough, obfuscated, font, decorationType, codepoint, ColourOverrideDefinition.PropertiesPart.DEFAULT);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FakeStyle(
            SpecialTextColour colour1, Integer shadowColour1, Boolean bold1, Boolean italic1, Boolean underlined1,
            Boolean strikethrough1, Boolean obfuscated1, FontDescription font1, DecorationType type, Integer codepoint1,
            ColourOverrideDefinition.PropertiesPart properties1
        ))) return false;
        return Objects.equals(bold, bold1) &&
            Objects.equals(italic, italic1) &&
            Objects.equals(underlined, underlined1) &&
            Objects.equals(obfuscated, obfuscated1) &&
            Objects.equals(shadowColour, shadowColour1) &&
            Objects.equals(strikethrough, strikethrough1) &&
            Objects.equals(font, font1) &&
            Objects.equals(colour, colour1) &&
            Objects.equals(decorationType, type) &&
            Objects.equals(codepoint, codepoint1) &&
            Objects.equals(properties, properties1);
    }

    @Override
    public @NotNull String toString() {
        return "FakeStyle{" +
            "colour=" + colour +
            ", shadowColour=" + shadowColour +
            ", bold=" + bold +
            ", italic=" + italic +
            ", underlined=" + underlined +
            ", strikethrough=" + strikethrough +
            ", obfuscated=" + obfuscated +
            ", font=" + font +
            ", decorationType=" + decorationType +
            ", codepoint=" + codepoint +
            ", properties=" + properties +
        '}';
    }

    public @NotNull String formattedString() {
        return "[" +
            (colour == null ? "" : "color=\"" + colour.formattedString() + "\"") +
            (shadowColour == null ? "" : ", shadow_color=\"" + ColourUtil.formatIntAsHexString(shadowColour) + "\"") +
            (bold == null ? "" : ", bold=" + bold) +
            (italic == null ? "" : ", italic=" + italic) +
            (underlined == null ? "" : ", underlined=" + underlined) +
            (strikethrough == null ? "" : ", strikethrough=" + strikethrough) +
            (obfuscated == null ? "" : ", obfuscated=" + obfuscated) +
            (font == null ? "" : ", font=\"" + formatFontDescription(font) + "\"") +
            (decorationType == null ? "" : ", decoration_type=\"" + decorationType + "\"") +
            (codepoint == null ? "" : ", codepoint=\"" + codepoint + "\"") +
        ']';
    }

    private static String formatFontDescription(FontDescription font) {
        return "TODO"; //TODO
    }
}
