package games.enchanted.eg_text_customiser.common.pack.property_tests;

import org.jetbrains.annotations.Nullable;

public interface PropertyTest<T> {
    boolean matches(@Nullable T value);
}
