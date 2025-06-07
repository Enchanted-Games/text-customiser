package games.enchanted.eg_text_customiser.common.pack.property_tests;

import games.enchanted.eg_text_customiser.common.fake_style.SpecialTextColour;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates.ColourPredicate;
import org.jetbrains.annotations.Nullable;

public class ColourTest implements PropertyTest<SpecialTextColour> {
    @Nullable private final ColourPredicate predicate;

    public ColourTest(@Nullable ColourPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean matches(@Nullable SpecialTextColour value) {
        if(this.predicate == null) return true;
        return this.predicate.colourMatches(value);
    }
}
