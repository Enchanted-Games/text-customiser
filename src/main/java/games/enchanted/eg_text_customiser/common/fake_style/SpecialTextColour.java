package games.enchanted.eg_text_customiser.common.fake_style;


import com.mojang.datafixers.util.Either;
import games.enchanted.eg_text_customiser.common.mixin.accessor.TextColorAccess;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates.BasicColourPredicate;
import games.enchanted.eg_text_customiser.common.util.ColourUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SpecialTextColour {
    private static final int WHITE = 0xffffff;

    private final Either<Integer, String> colourValueOrName;
    private final boolean isSignText;
    private final boolean isGlowingSignText;
    private final boolean isGlowingOutline;
    @Nullable private final DyeColor dyeColor;

    public SpecialTextColour(int rgb) {
        if(rgb == -1) rgb = WHITE;
        this.colourValueOrName = Either.left(rgb);
        this.isSignText = false;
        this.isGlowingSignText = false;
        this.isGlowingOutline = false;
        this.dyeColor = null;
    }

    public SpecialTextColour(String colourName) {
        this.colourValueOrName = Either.right(colourName);
        this.isSignText = false;
        this.isGlowingSignText = false;
        this.isGlowingOutline = false;
        this.dyeColor = null;
    }

    public SpecialTextColour(int rgb, @NotNull DyeColor dyeColor, boolean isSignText, boolean isGlowingSignText, boolean isGlowingOutline) {
        this.colourValueOrName = Either.left(rgb);
        this.isSignText = isSignText;
        this.isGlowingSignText = isGlowingSignText;
        this.isGlowingOutline = isGlowingOutline;
        this.dyeColor = dyeColor;
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

    public static SpecialTextColour fromSignTextData(SignTextData signTextData, boolean isGlowingOutline) {
        if(isGlowingOutline && signTextData.outlineColour() == null) {
            throw new IllegalStateException("SignTextData has no outline colour but tried to create a SpecialTextColour for outline colour");
        }
        return new SpecialTextColour(
            isGlowingOutline ? signTextData.outlineColour() : signTextData.darkColour(),
            signTextData.dyeColor(),
            true,
            signTextData.isGlowingSignText(),
            isGlowingOutline
        );
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
        return safeGetAsRGB(this.colourValueOrName);
    }

    private static int safeGetAsRGB(Either<Integer, String> colourValueOrName) {
        if(colourValueOrName.left().isPresent()) {
            return colourValueOrName.left().get();
        } else if(colourValueOrName.right().isEmpty()) {
            throw new IllegalStateException("Either has no right or left value");
        }
        ChatFormatting formatting = ChatFormatting.getByName(colourValueOrName.right().get());
        if(formatting != null && formatting.isColor() && formatting.getColor() != null) {
            return formatting.getColor();
        } else {
            throw new IllegalStateException("Expected a colour chat formatting, got '" + formatting + "' instead.");
        }
    }

    public @Nullable DyeColor getDyeColor() {
        return dyeColor;
    }

    public boolean isNamedColour() {
        return colourValueOrName.right().isPresent();
    }

    private boolean compareColour(Either<Integer, String> comparison, boolean strictMatchNamed) {
        if(!strictMatchNamed) {
            return Objects.equals(this.safeGetAsRGB(), safeGetAsRGB(comparison));
        }
        if(colourValueOrName.left().isPresent() && comparison.left().isPresent()) {
            // integer comparison
            return Objects.equals(colourValueOrName.left(), comparison.left());
        } else if(colourValueOrName.right().isPresent() && comparison.right().isPresent()) {
            // name comparison
            return Objects.equals(colourValueOrName.right(), comparison.right());
        }
        return false;
    }

    public boolean compareTo(SpecialTextColour comparison, boolean strictMatchNamed) {
        if(this.isSignText && comparison.isSignText) {
            return Objects.equals(this.dyeColor, comparison.dyeColor) && isGlowingSignText == comparison.isGlowingSignText && isGlowingOutline == comparison.isGlowingOutline;
        }
        return isSignText == comparison.isSignText && isGlowingSignText == comparison.isGlowingSignText && isGlowingOutline == comparison.isGlowingOutline && compareColour(comparison.colourValueOrName, strictMatchNamed);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SpecialTextColour that = (SpecialTextColour) o;
        return compareTo(that, true);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colourValueOrName, isSignText, isGlowingSignText, isGlowingOutline);
    }

    @Override
    public @NotNull String toString() {
        return "SpecialTextColour{" +
            "colourValueOrName=" + colourValueOrName +
            ", isSignText=" + isSignText +
            ", isGlowingSignText=" + isGlowingSignText +
            ", isGlowingOutline=" + isGlowingOutline +
            ", dyeColor=" + dyeColor +
        '}';
    }


    public String formattedString() {
        if(isSignText) {
            return "{" +
                "dye=" + (dyeColor == null ? "<null>" : dyeColor.getName()) +
                ", glowing=" + isGlowingSignText +
                ", is_glowing_outline=" + isGlowingOutline +
            "}";
        }

        if(colourValueOrName.left().isPresent()) {
            return ColourUtil.formatIntAsHexString(colourValueOrName.left().get());
        } else if(colourValueOrName.right().isPresent()) {
            return colourValueOrName.right().get();
        }

        return toString();
    }
}
