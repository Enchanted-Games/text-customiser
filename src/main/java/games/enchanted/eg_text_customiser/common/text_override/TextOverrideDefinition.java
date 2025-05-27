package games.enchanted.eg_text_customiser.common.text_override;

import games.enchanted.eg_text_customiser.common.text_override.fake_style.FakeStyle;
import games.enchanted.eg_text_customiser.common.text_override.fake_style.SpecialTextColour;
import games.enchanted.eg_text_customiser.common.text_override.tests.SimpleEqualityTest;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class TextOverrideDefinition {
    List<Function<FakeStyle, Boolean>> tests;
    SimpleEqualityTest<Boolean> boldTester;
    SimpleEqualityTest<Boolean> italicTester;
    SimpleEqualityTest<Boolean> underlinedTester;
    SimpleEqualityTest<Boolean> strikethroughTester;
    SimpleEqualityTest<Boolean> obfuscatedTester;

    TextOverrideDefinition(@Nullable SpecialTextColour colour, @Nullable Integer shadowColour, @Nullable Boolean bold, @Nullable Boolean italic, @Nullable Boolean underlined, @Nullable Boolean strikethrough, @Nullable Boolean obfuscated, @Nullable ResourceLocation font) {
        boldTester = new SimpleEqualityTest<>(bold);
        italicTester = new SimpleEqualityTest<>(italic);
        underlinedTester = new SimpleEqualityTest<>(underlined);
        strikethroughTester = new SimpleEqualityTest<>(strikethrough);
        obfuscatedTester = new SimpleEqualityTest<>(obfuscated);
        tests = List.of(
            (style) -> boldTester.matches(style.bold()),
            (style) -> italicTester.matches(style.italic()),
            (style) -> underlinedTester.matches(style.underlined()),
            (style) -> strikethroughTester.matches(style.strikethrough()),
            (style) -> obfuscatedTester.matches(style.obfuscated())
        );
    }

    public boolean styleMatches(FakeStyle style) {
        for (int i = 0; i < tests.size(); i++) {
            boolean lastText = i >= tests.size() - 1;
            Function<FakeStyle, Boolean> test = tests.get(i);
            boolean testResult = test.apply(style);
            if(testResult && !lastText) continue;
            return testResult;
        }
        return false;
    }
}
