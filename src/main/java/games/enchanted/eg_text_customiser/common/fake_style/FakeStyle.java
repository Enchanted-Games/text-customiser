package games.enchanted.eg_text_customiser.common.fake_style;

import games.enchanted.eg_text_customiser.common.pack.colour_override.ColourOverrideDefinition;
import net.minecraft.resources.ResourceLocation;
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
        @Nullable ResourceLocation font
    ) {
        this(colour, shadowColour, bold, italic, underlined, strikethrough, obfuscated, font, ColourOverrideDefinition.PropertiesPart.DEFAULT);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FakeStyle fakeStyle)) return false;
        return Objects.equals(bold, fakeStyle.bold) && Objects.equals(italic, fakeStyle.italic) && Objects.equals(underlined, fakeStyle.underlined) && Objects.equals(obfuscated, fakeStyle.obfuscated) && Objects.equals(shadowColour, fakeStyle.shadowColour) && Objects.equals(strikethrough, fakeStyle.strikethrough) && Objects.equals(font, fakeStyle.font) && Objects.equals(colour, fakeStyle.colour) && Objects.equals(properties, fakeStyle.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colour, shadowColour, bold, italic, underlined, strikethrough, obfuscated, font, properties);
    }
}
