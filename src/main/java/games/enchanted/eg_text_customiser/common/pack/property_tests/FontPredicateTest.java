package games.enchanted.eg_text_customiser.common.pack.property_tests;


import games.enchanted.eg_text_customiser.common.pack.property_tests.font.predicates.FontPredicate;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FontPredicateTest implements PropertyTest<ResourceLocation> {
    @Nullable
    private final FontPredicate predicate;

    public FontPredicateTest(@Nullable FontPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean matches(@Nullable ResourceLocation value) {
        if(this.predicate == null) return true;
        return this.predicate.fontMatches(value);
    }

    public static List<FontPredicateTest> predicatesToTests(@Nullable List<FontPredicate> predicates) {
        if(predicates == null) {
            return List.of();
        }
        return predicates.stream().map(FontPredicateTest::new).toList();
    }

    public static List<FontPredicate> testsToPredicates(@Nullable List<FontPredicateTest> tests) {
        if(tests == null) {
            return List.of();
        }
        return tests.stream().map(test -> test.predicate).toList();
    }
}
