package games.enchanted.eg_text_customiser.common.text_override.tests;

import org.jetbrains.annotations.Nullable;

public class SimpleEqualityTest<T> implements PropertyTest<T> {
    @Nullable
    private final T comparisonValue;

    public SimpleEqualityTest(@Nullable T comparisonValue) {
        this.comparisonValue = comparisonValue;
    }

    @Override
    public boolean matches(@Nullable T value) {
        if(comparisonValue == null) return true;
        return comparisonValue.equals(value);
    }
}
