package games.enchanted.eg_text_customiser.common.pack.property_tests.character.predicates;

import com.mojang.serialization.MapCodec;
import games.enchanted.eg_text_customiser.common.fake_style.SpecialTextColour;
import org.jetbrains.annotations.Nullable;

public interface CharacterPredicate {
    /**
     * Check if a codepoint matches against this predicate. If null is passed, the test should always fail
     *
     * @param codepoint a codepoint to test against
     * @return if the test succeeded
     */
    boolean charMatches(@Nullable Integer codepoint);
    MapCodec<? extends CharacterPredicate> codec();
}
