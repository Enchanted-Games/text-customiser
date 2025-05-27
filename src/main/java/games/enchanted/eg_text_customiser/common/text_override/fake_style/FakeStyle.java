package games.enchanted.eg_text_customiser.common.text_override.fake_style;

import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.SignText;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class FakeStyle {
    @Nullable private final SpecialTextColour colour;
    @Nullable private final Integer shadowColour;
    @Nullable private final Boolean bold;
    @Nullable private final Boolean italic;
    @Nullable private final Boolean underlined;
    @Nullable private final Boolean strikethrough;
    @Nullable private final Boolean obfuscated;
    @Nullable private final ResourceLocation font;

    public FakeStyle(@Nullable SpecialTextColour colour, @Nullable Integer shadowColour, @Nullable Boolean bold, @Nullable Boolean italic, @Nullable Boolean underlined, @Nullable Boolean strikethrough, @Nullable Boolean obfuscated, @Nullable ResourceLocation font) {
        this.colour = colour;
        this.shadowColour = shadowColour;
        this.bold = bold;
        this.italic = italic;
        this.underlined = underlined;
        this.strikethrough = strikethrough;
        this.obfuscated = obfuscated;
        this.font = font;
    }

    public static FakeStyle fromStyle(Style style) {
        return new FakeStyle(
            SpecialTextColour.fromTextColor(style.getColor()),
            style.getShadowColor(),
            style.isBold(),
            style.isItalic(),
            style.isUnderlined(),
            style.isStrikethrough(),
            style.isObfuscated(),
            style.getFont()
        );
    }

    public static FakeStyle fromSignText(SignText signText, boolean isGlowingOutline) {
        return new FakeStyle(SpecialTextColour.fromSignText(signText, isGlowingOutline), null, null, null, null, null, null, null);
    }

    public @Nullable SpecialTextColour getColour() {
        return colour;
    }

    public @Nullable Integer getShadowColour() {
        return shadowColour;
    }

    public @Nullable Boolean getBold() {
        return bold;
    }

    public @Nullable Boolean getItalic() {
        return italic;
    }

    public @Nullable Boolean getUnderlined() {
        return underlined;
    }

    public @Nullable Boolean getStrikethrough() {
        return strikethrough;
    }

    public @Nullable Boolean getObfuscated() {
        return obfuscated;
    }

    public @Nullable ResourceLocation getFont() {
        return font;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FakeStyle fakeStyle = (FakeStyle) o;
        return Objects.equals(colour, fakeStyle.colour) && Objects.equals(shadowColour, fakeStyle.shadowColour) && Objects.equals(bold, fakeStyle.bold) && Objects.equals(italic, fakeStyle.italic) && Objects.equals(underlined, fakeStyle.underlined) && Objects.equals(strikethrough, fakeStyle.strikethrough) && Objects.equals(obfuscated, fakeStyle.obfuscated) && Objects.equals(font, fakeStyle.font);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colour, shadowColour, bold, italic, underlined, strikethrough, obfuscated, font);
    }
}
