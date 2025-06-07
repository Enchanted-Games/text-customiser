package games.enchanted.eg_text_customiser.common.text_override.fake_style;

import games.enchanted.eg_text_customiser.common.duck.StyleAddedFields;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.SignText;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record FakeStyle(
    @Nullable SpecialTextColour colour,
    @Nullable Integer shadowColour,
    @Nullable Boolean bold,
    @Nullable Boolean italic,
    @Nullable Boolean underlined,
    @Nullable Boolean strikethrough,
    @Nullable Boolean obfuscated,
    @Nullable ResourceLocation font
) {
    public static FakeStyle fromStyle(Style style) {
        SpecialTextColour specialTextColour;
        @Nullable Integer comparisonColour = ((StyleAddedFields) style).eg_text_customiser$getComparisonColour();
        if(comparisonColour == null) {
            specialTextColour = SpecialTextColour.fromTextColor(style.getColor());
        } else {
            specialTextColour = new SpecialTextColour(comparisonColour);
        }
        return new FakeStyle(
            specialTextColour,
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

    public TextColor getAsTextColor(TextColor fallback) {
        return (colour == null || colour.getColourValueOrName().left().orElse(0) == -1) ? fallback : colour.toTextColor();
    }

    public @NotNull Boolean bold(Boolean fallback) {
        return bold == null ? fallback : bold;
    }

    public @NotNull Boolean italic(Boolean fallback) {
        return italic == null ? fallback : italic;
    }

    public @NotNull Boolean underlined(Boolean fallback) {
        return underlined == null ? fallback : underlined;
    }

    public @NotNull Boolean strikethrough(Boolean fallback) {
        return strikethrough == null ? fallback : strikethrough;
    }

    public @NotNull Boolean obfuscated(Boolean fallback) {
        return obfuscated == null ? fallback : obfuscated;
    }

    public @NotNull ResourceLocation font(ResourceLocation fallback) {
        return font == null ? fallback : font;
    }
}
