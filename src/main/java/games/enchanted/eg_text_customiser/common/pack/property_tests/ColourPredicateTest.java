package games.enchanted.eg_text_customiser.common.pack.property_tests;

import games.enchanted.eg_text_customiser.common.fake_style.SpecialTextColour;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates.ColourPredicate;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ColourPredicateTest implements PropertyTest<SpecialTextColour> {
    @Nullable private final ColourPredicate predicate;

    public ColourPredicateTest(@Nullable ColourPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean matches(@Nullable SpecialTextColour value) {
        if(this.predicate == null) return true;
        return this.predicate.colourMatches(value);
    }

    public static List<ColourPredicateTest> predicatesToTests(@Nullable List<ColourPredicate> predicates) {
        if(predicates == null) {
            return List.of();
        }
        return predicates.stream().map(ColourPredicateTest::new).toList();
    }

    public static List<ColourPredicate> testsToPredicates(@Nullable List<ColourPredicateTest> tests) {
        if(tests == null) {
            return List.of();
        }
        return tests.stream().map(test -> test.predicate).toList();
    }
}
