package games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates;

import com.mojang.serialization.MapCodec;
import games.enchanted.eg_text_customiser.common.fake_style.SpecialTextColour;
import org.jetbrains.annotations.Nullable;

public interface ColourPredicate {
    /**
     * Check if a given colour matches against this predicate. If null is passed, the test should always fail
     *
     * @param colour the colour to test against
     * @return if the test succeeded
     */
    boolean colourMatches(@Nullable SpecialTextColour colour);
    MapCodec<? extends ColourPredicate> codec();
}
