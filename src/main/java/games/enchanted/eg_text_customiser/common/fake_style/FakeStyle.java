package games.enchanted.eg_text_customiser.common.fake_style;

import games.enchanted.eg_text_customiser.common.pack.colour_override.ColourOverrideDefinition;
import games.enchanted.eg_text_customiser.common.util.ColourUtil;
import net.minecraft.resources.ResourceLocation;
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
    @Nullable ResourceLocation font,
    @Nullable DecorationType decorationType,
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
        @Nullable ResourceLocation font,
        @Nullable DecorationType decorationType
    ) {
        this(colour, shadowColour, bold, italic, underlined, strikethrough, obfuscated, font, decorationType, ColourOverrideDefinition.PropertiesPart.DEFAULT);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FakeStyle(
            SpecialTextColour colour1, Integer shadowColour1, Boolean bold1, Boolean italic1, Boolean underlined1,
            Boolean strikethrough1, Boolean obfuscated1, ResourceLocation font1, DecorationType type,
            ColourOverrideDefinition.PropertiesPart properties1
        ))) return false;
        return Objects.equals(bold, bold1) && Objects.equals(italic, italic1) && Objects.equals(underlined, underlined1) && Objects.equals(obfuscated, obfuscated1) && Objects.equals(shadowColour, shadowColour1) && Objects.equals(strikethrough, strikethrough1) && Objects.equals(font, font1) && Objects.equals(colour, colour1) && Objects.equals(decorationType, type) && Objects.equals(properties, properties1);
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
            (font == null ? "" : ", font=\"" + font + "\"") +
            (decorationType == null ? "" : ", decoration_type=\"" + decorationType + "\"") +
        ']';
    }
}
