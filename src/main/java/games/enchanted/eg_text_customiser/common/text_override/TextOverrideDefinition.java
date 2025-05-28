package games.enchanted.eg_text_customiser.common.text_override;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import games.enchanted.eg_text_customiser.common.Logging;
import games.enchanted.eg_text_customiser.common.text_override.fake_style.FakeStyle;
import games.enchanted.eg_text_customiser.common.text_override.tests.SimpleEqualityTest;
import games.enchanted.eg_text_customiser.common.text_override.tests.colour.ColourPredicate;
import games.enchanted.eg_text_customiser.common.text_override.tests.colour.ColourPredicates;
import games.enchanted.eg_text_customiser.common.text_override.tests.colour.predicates.BasicColourPredicate;
import games.enchanted.eg_text_customiser.common.util.ResourceLocationUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TextOverrideDefinition {
    public static final Codec<TextOverrideDefinition> CODEC = RecordCodecBuilder.create((RecordCodecBuilder.Instance<TextOverrideDefinition> instance) ->
        instance.group(
            WhenPart.CODEC.fieldOf("when").forGetter((override) -> override.whenPart),
            ReplaceWithPart.CODEC.fieldOf("replace_with").forGetter((override) -> override.replacement)
        ).apply(
            instance,
            TextOverrideDefinition::new
        )
    );

    final WhenPart whenPart;
    List<Function<FakeStyle, Boolean>> tests;
    final SimpleEqualityTest<Boolean> boldTester;
    final SimpleEqualityTest<Boolean> italicTester;
    final SimpleEqualityTest<Boolean> underlinedTester;
    final SimpleEqualityTest<Boolean> strikethroughTester;
    final SimpleEqualityTest<Boolean> obfuscatedTester;

    final ReplaceWithPart replacement;

    public TextOverrideDefinition(WhenPart whenPart, ReplaceWithPart replaceWithPart) {
        this.whenPart = whenPart;
        this.boldTester = new SimpleEqualityTest<>(whenPart.bold);
        this.italicTester = new SimpleEqualityTest<>(whenPart.italic);
        this.underlinedTester = new SimpleEqualityTest<>(whenPart.underlined);
        this.strikethroughTester = new SimpleEqualityTest<>(whenPart.strikethrough);
        this.obfuscatedTester = new SimpleEqualityTest<>(whenPart.obfuscated);
        tests = List.of(
            (style) -> this.boldTester.matches(style.bold()),
            (style) -> this.italicTester.matches(style.italic()),
            (style) -> this.underlinedTester.matches(style.underlined()),
            (style) -> this.strikethroughTester.matches(style.strikethrough()),
            (style) -> this.obfuscatedTester.matches(style.obfuscated())
        );

        this.replacement = replaceWithPart;
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

    public record WhenPart(@Nullable ColourPredicate colour, @Nullable Integer shadowColour, @Nullable Boolean bold, @Nullable Boolean italic, @Nullable Boolean underlined, @Nullable Boolean strikethrough, @Nullable Boolean obfuscated, @Nullable ResourceLocation font) {
        private static final Codec<WhenPart> CODEC = RecordCodecBuilder.create((RecordCodecBuilder.Instance<WhenPart> instance) ->
            instance.group(
                ColourPredicates.CODEC.optionalFieldOf("color").forGetter((part) -> Optional.ofNullable(part.colour)),
                ExtraCodecs.RGB_COLOR_CODEC.optionalFieldOf("shadow_color").forGetter(part -> Optional.ofNullable(part.shadowColour)),
                Codec.BOOL.optionalFieldOf("bold").forGetter(part -> Optional.ofNullable(part.bold)),
                Codec.BOOL.optionalFieldOf("italic").forGetter(part -> Optional.ofNullable(part.italic)),
                Codec.BOOL.optionalFieldOf("underlined").forGetter(part -> Optional.ofNullable(part.underlined)),
                Codec.BOOL.optionalFieldOf("strikethrough").forGetter(part -> Optional.ofNullable(part.strikethrough)),
                Codec.BOOL.optionalFieldOf("obfuscated").forGetter(part -> Optional.ofNullable(part.obfuscated)),
                ResourceLocation.CODEC.optionalFieldOf("font").forGetter(part -> Optional.ofNullable(part.font))
            ).apply(
                instance, (Optional<ColourPredicate> colour, Optional<Integer> shadowColour, Optional<Boolean> bold, Optional<Boolean> italic, Optional<Boolean> underlined, Optional<Boolean> strikethrough, Optional<Boolean> obfuscated, Optional<ResourceLocation> font) -> new WhenPart(
                    colour.orElse(null),
                    shadowColour.orElse(null),
                    bold.orElse(null),
                    italic.orElse(null),
                    underlined.orElse(null),
                    strikethrough.orElse(null),
                    obfuscated.orElse(null),
                    font.orElse(null)
                )
            )
        );
    }

    public record ReplaceWithPart(@Nullable Integer colour, @Nullable Integer shadowColour, @Nullable Boolean bold, @Nullable Boolean italic, @Nullable Boolean underlined, @Nullable Boolean strikethrough, @Nullable Boolean obfuscated, @Nullable ResourceLocation font) {
        public static final Codec<ReplaceWithPart> CODEC = RecordCodecBuilder.create((RecordCodecBuilder.Instance<ReplaceWithPart> instance) ->
            instance.group(
                ExtraCodecs.RGB_COLOR_CODEC.optionalFieldOf("color").forGetter((part) -> Optional.ofNullable(part.colour)),
                ExtraCodecs.RGB_COLOR_CODEC.optionalFieldOf("shadow_color").forGetter(part -> Optional.ofNullable(part.shadowColour)),
                Codec.BOOL.optionalFieldOf("bold").forGetter(part -> Optional.ofNullable(part.bold)),
                Codec.BOOL.optionalFieldOf("italic").forGetter(part -> Optional.ofNullable(part.italic)),
                Codec.BOOL.optionalFieldOf("underlined").forGetter(part -> Optional.ofNullable(part.underlined)),
                Codec.BOOL.optionalFieldOf("strikethrough").forGetter(part -> Optional.ofNullable(part.strikethrough)),
                Codec.BOOL.optionalFieldOf("obfuscated").forGetter(part -> Optional.ofNullable(part.obfuscated)),
                ResourceLocation.CODEC.optionalFieldOf("font").forGetter(part -> Optional.ofNullable(part.font))
            )
            .apply(
                instance, (Optional<Integer> colour, Optional<Integer> shadowColour, Optional<Boolean> bold, Optional<Boolean> italic, Optional<Boolean> underlined, Optional<Boolean> strikethrough, Optional<Boolean> obfuscated, Optional<ResourceLocation> font) -> new ReplaceWithPart(
                    colour.orElse(null),
                    shadowColour.orElse(null),
                    bold.orElse(null),
                    italic.orElse(null),
                    underlined.orElse(null),
                    strikethrough.orElse(null),
                    obfuscated.orElse(null),
                    font.orElse(null)
                )
            )
        );
    }

    public static void printExample() {
        DataResult<JsonElement> result = TextOverrideDefinition.CODEC.encodeStart(JsonOps.INSTANCE, new TextOverrideDefinition(
            new TextOverrideDefinition.WhenPart(new BasicColourPredicate(-1), -1, false, false, false, false, false, ResourceLocationUtil.ofMod("placeholder")),
            new TextOverrideDefinition.ReplaceWithPart(-1, -1, false, false, false, false, false, ResourceLocationUtil.ofMod("placeholder"))
        ));
        if(result.error().isPresent()) {
            throw new RuntimeException(result.error().get().message());
        }

        Logging.info("Example file: {}", result.getOrThrow().toString());
    }
}
