package games.enchanted.eg_text_customiser.common.fake_style;


import com.mojang.datafixers.util.Either;
import games.enchanted.eg_text_customiser.common.mixin.accessor.TextColorAccess;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.level.block.entity.SignText;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SpecialTextColour {
    private static final int WHITE = 0xffffff;

    private final Either<Integer, String> colourValueOrName;
    private final boolean isSignText;
    private final boolean isGlowingSignText;
    private final boolean isGlowingOutline;

    public SpecialTextColour(int rgb) {
        if(rgb == -1) rgb = WHITE;
        this.colourValueOrName = Either.left(rgb);
        this.isSignText = false;
        this.isGlowingSignText = false;
        this.isGlowingOutline = false;
    }

    public SpecialTextColour(String colourName) {
        this.colourValueOrName = Either.right(colourName);
        this.isSignText = false;
        this.isGlowingSignText = false;
        this.isGlowingOutline = false;
    }

    public SpecialTextColour(int rgb, boolean isSignText, boolean isGlowingSignText, boolean isGlowingOutline) {
        this.colourValueOrName = Either.left(rgb);
        this.isSignText = isSignText;
        this.isGlowingSignText = isGlowingSignText;
        this.isGlowingOutline = isGlowingOutline;
    }

    public static SpecialTextColour fromTextColor(@Nullable TextColor textColor) {
        if(textColor == null) {
            return new SpecialTextColour(WHITE);
        }
        String textNamedColour = ((TextColorAccess) (Object) textColor).eg_text_customiser$getName();
        if(textNamedColour != null) {
            return new SpecialTextColour(textNamedColour);
        }
        return new SpecialTextColour(textColor.getValue());
    }

    public TextColor toTextColor() {
        if(colourValueOrName.left().isPresent()) {
            return TextColor.fromRgb(colourValueOrName.left().get());
        } else if(colourValueOrName.right().isEmpty()) {
            throw new IllegalStateException("Either passed with no right or left value");
        }
        return TextColor.fromLegacyFormat(ChatFormatting.getByName(colourValueOrName.right().get()));
    }

    public static SpecialTextColour fromSignText(SignText signText, boolean isGlowingOutline) {
        return new SpecialTextColour(signText.getColor().getTextColor(), true, signText.hasGlowingText(), isGlowingOutline);
    }

    public static SpecialTextColour fromEither(Either<Integer, String> either) {
        if(either.left().isPresent()) {
            return new SpecialTextColour(either.left().get());
        } else if(either.right().isEmpty()) {
            throw new IllegalStateException("Either passed with no right or left value");
        }
        return new SpecialTextColour(either.right().get());
    }

    public Either<Integer, String> getColourValueOrName() {
        return colourValueOrName;
    }

    public int safeGetAsRGB() {
        if(colourValueOrName.left().isPresent()) {
            return colourValueOrName.left().get();
        } else if(colourValueOrName.right().isEmpty()) {
            throw new IllegalStateException("Either has no right or left value");
        }
        ChatFormatting formatting = ChatFormatting.getByName(colourValueOrName.right().get());
        Integer intRGB;
        if(formatting != null && formatting.isColor() && formatting.getColor() != null) {
            return formatting.getColor();
        } else {
            throw new IllegalStateException("Expected a colour chat formatting, got '" + formatting + "' instead.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SpecialTextColour that = (SpecialTextColour) o;
        return isSignText == that.isSignText && isGlowingSignText == that.isGlowingSignText && isGlowingOutline == that.isGlowingOutline && Objects.equals(colourValueOrName, that.colourValueOrName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colourValueOrName, isSignText, isGlowingSignText, isGlowingOutline);
    }
}
