package games.enchanted.eg_text_customiser.common.text_override.fake_style;


import com.mojang.datafixers.util.Either;
import games.enchanted.eg_text_customiser.common.mixin.accessor.TextColorAccess;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.level.block.entity.SignText;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SpecialTextColour {
    private final Either<String, Integer> colourNameOrValue;
    private final boolean isSignText;
    private final boolean isGlowingOutline;

    public SpecialTextColour(int rgb) {
        this.colourNameOrValue = Either.right(rgb);
        this.isSignText = false;
        this.isGlowingOutline = false;
    }

    SpecialTextColour(String colourName) {
        this.colourNameOrValue = Either.left(colourName);
        this.isSignText = false;
        this.isGlowingOutline = false;
    }

    SpecialTextColour(int rgb, boolean isSignText, boolean isGlowingOutline) {
        this.colourNameOrValue = Either.right(rgb);
        this.isSignText = isSignText;
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
        return new SpecialTextColour(signText.getColor().getTextColor(), true, isGlowingOutline);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SpecialTextColour that = (SpecialTextColour) o;
        return isSignText == that.isSignText && isGlowingOutline == that.isGlowingOutline && Objects.equals(colourNameOrValue, that.colourNameOrValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colourNameOrValue, isSignText, isGlowingOutline);
    }
}
