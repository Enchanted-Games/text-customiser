package games.enchanted.eg_text_customiser.common.pack.property_tests;

import games.enchanted.eg_text_customiser.common.pack.property_tests.character.predicates.CharacterPredicate;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CharacterPredicateTest implements PropertyTest<Integer> {
    @Nullable
    private final CharacterPredicate predicate;

    public CharacterPredicateTest(@Nullable CharacterPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean matches(@Nullable Integer value) {
        if(this.predicate == null) return true;
        return predicate.charMatches(value);
    }

    public static List<CharacterPredicateTest> predicatesToTests(@Nullable List<CharacterPredicate> predicates) {
        if(predicates == null) {
            return List.of();
        }
        return predicates.stream().map(CharacterPredicateTest::new).toList();
    }

    public static List<CharacterPredicate> testsToPredicates(@Nullable List<CharacterPredicateTest> tests) {
        if(tests == null) {
            return List.of();
        }
        return tests.stream().map(test -> test.predicate).toList();
    }
}
