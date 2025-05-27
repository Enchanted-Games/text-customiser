package games.enchanted.eg_text_customiser.common.text_override;

import games.enchanted.eg_text_customiser.common.text_override.fake_style.FakeStyle;
import games.enchanted.eg_text_customiser.common.text_override.tests.SimpleEqualityTest;
import org.jetbrains.annotations.Nullable;

public class TextOverrideDefinition {
    SimpleEqualityTest<Boolean> boldTester;

    TextOverrideDefinition(@Nullable Boolean bold) {
        boldTester = new SimpleEqualityTest<>(bold);
    }

    public boolean styleMatches(FakeStyle style) {
        return boldTester.matches(style.getBold());
    }
}
