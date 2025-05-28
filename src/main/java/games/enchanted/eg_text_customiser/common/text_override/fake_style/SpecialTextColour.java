package games.enchanted.eg_text_customiser.common.text_override.fake_style;


import com.mojang.datafixers.util.Either;
import games.enchanted.eg_text_customiser.common.mixin.accessor.TextColorAccess;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.level.block.entity.SignText;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SpecialTextColour {
    public static final String DEFAULT_COLOUR_NAME = "eg_text_customiser:default";

    private final Either<Integer, String> colourValueOrName;
    private final boolean isSignText;
    private final boolean isGlowingSignText;
    private final boolean isGlowingOutline;

    public SpecialTextColour(int rgb) {
        this.colourValueOrName = Either.left(rgb);
        this.isSignText = false;
        this.isGlowingSignText = false;
        this.isGlowingOutline = false;
    }

    SpecialTextColour(String colourName) {
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
            return new SpecialTextColour(-1);
        }
        String textNamedColour = ((TextColorAccess) (Object) textColor).eg_text_customiser$getName();
        if(textNamedColour != null) {
            return new SpecialTextColour(textNamedColour);
        }
        return new SpecialTextColour(textColor.getValue());
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
        String colourName = either.right().get();
        if(colourName.equals(DEFAULT_COLOUR_NAME)) {
            return new SpecialTextColour(-1);
        }
        return new SpecialTextColour(colourName);
    }

    public Either<Integer, String> getColourValueOrName() {
        return colourValueOrName;
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
