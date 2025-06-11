package games.enchanted.eg_text_customiser.common.pack.colour_override;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import games.enchanted.eg_text_customiser.common.Logging;
import games.enchanted.eg_text_customiser.common.duck.StyleAdditions;
import games.enchanted.eg_text_customiser.common.fake_style.FakeStyle;
import games.enchanted.eg_text_customiser.common.fake_style.SpecialTextColour;
import games.enchanted.eg_text_customiser.common.pack.property_tests.ColourPredicateTest;
import games.enchanted.eg_text_customiser.common.pack.property_tests.SimpleEqualityTest;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.ColourPredicates;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates.BasicColourPredicate;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates.ColourPredicate;
import games.enchanted.eg_text_customiser.common.serialization.ColourCodecs;
import games.enchanted.eg_text_customiser.common.util.ColourUtil;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.profiling.Profiler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class ColourOverrideDefinition {
    public static final Codec<List<ColourPredicate>> COLOUR_PREDICATE_LIST_CODEC = ExtraCodecs.compactListCodec(ColourPredicates.CODEC);

    public static final Codec<ColourOverrideDefinition> CODEC = RecordCodecBuilder.create((RecordCodecBuilder.Instance<ColourOverrideDefinition> instance) ->
        instance.group(
            PropertiesPart.CODEC.optionalFieldOf("properties", PropertiesPart.DEFAULT).forGetter(override -> override.properties),
            WhenPart.CODEC.fieldOf("when").forGetter((override) -> override.when),
            ReplaceWithPart.CODEC.fieldOf("replace_with").forGetter((override) -> override.replacement)
        ).apply(
            instance,
            ColourOverrideDefinition::new
        )
    );

    final PropertiesPart properties;
    final WhenPart when;
    final ReplaceWithPart replacement;
    List<Function<FakeStyle, Boolean>> tests;

    @Nullable final List<ColourPredicateTest> colourTests;
    @Nullable final List<ColourPredicateTest> shadowColourTests;
    final SimpleEqualityTest<Boolean> boldTester;
    final SimpleEqualityTest<Boolean> italicTester;
    final SimpleEqualityTest<Boolean> underlinedTester;
    final SimpleEqualityTest<Boolean> strikethroughTester;
    final SimpleEqualityTest<Boolean> obfuscatedTester;
    final SimpleEqualityTest<ResourceLocation> fontTester;

    public ColourOverrideDefinition(PropertiesPart propertiesPart, WhenPart whenPart, ReplaceWithPart replaceWithPart) {
        this.properties = propertiesPart;
        this.when = whenPart;
        this.replacement = replaceWithPart;

        this.colourTests = whenPart.colour;
        this.shadowColourTests = whenPart.shadowColour;
        this.boldTester = new SimpleEqualityTest<>(whenPart.bold);
        this.italicTester = new SimpleEqualityTest<>(whenPart.italic);
        this.underlinedTester = new SimpleEqualityTest<>(whenPart.underlined);
        this.strikethroughTester = new SimpleEqualityTest<>(whenPart.strikethrough);
        this.obfuscatedTester = new SimpleEqualityTest<>(whenPart.obfuscated);
        this.fontTester = new SimpleEqualityTest<>(whenPart.font);

        tests = List.of(
            (style) -> {
                if (this.colourTests == null || this.colourTests.isEmpty()) {
                    return true;
                }
                return this.colourTests.stream().anyMatch(test -> test.matches(style.colour()));
            },
            (style) -> {
                if (this.shadowColourTests == null || this.shadowColourTests.isEmpty()) {
                    return true;
                }
                return this.shadowColourTests.stream().anyMatch(test -> test.matches(
                    style.shadowColour() == null ? null : new SpecialTextColour(style.shadowColour())
                ));
            },
            (style) -> this.boldTester.matches(style.bold()),
            (style) -> this.italicTester.matches(style.italic()),
            (style) -> this.underlinedTester.matches(style.underlined()),
            (style) -> this.strikethroughTester.matches(style.strikethrough()),
            (style) -> this.obfuscatedTester.matches(style.obfuscated()),
            (style) -> this.fontTester.matches(style.font())
        );
    }

    public boolean styleMatches(FakeStyle style) {
        Profiler.get().push("eg_text_customiser:check_style_matches");
        boolean result = tests.stream().allMatch(test -> test.apply(style));
        Profiler.get().pop();
        return result;
    }

    public FakeStyle applyToStyle(FakeStyle style) {
        Profiler.get().push("eg_text_customiser:apply_to_style");
        FakeStyle newStyle = new FakeStyle(
            replacement.colour == null ? style.colour() : new SpecialTextColour(replacement.colour),
            replacement.shadowColour == null ? style.shadowColour() : replacement.shadowColour,
            style.bold(),
            style.italic(),
            style.underlined(),
            style.strikethrough(),
            style.obfuscated(),
            style.font(),
            properties
        );
        Profiler.get().pop();
        return newStyle;
    }

    public static void printExample() {
        DataResult<JsonElement> result = ColourOverrideDefinition.CODEC.encodeStart(JsonOps.INSTANCE, new ColourOverrideDefinition(
            PropertiesPart.DEFAULT,
            new WhenPart(List.of(new ColourPredicateTest(new BasicColourPredicate(new SpecialTextColour("red")))), null, null, null, null, null, null, null),
            new ReplaceWithPart(0xffffff, 0x777777)
        ));
        if(result.error().isPresent()) {
            throw new RuntimeException(result.error().get().message());
        }

        Logging.info("Example file: {}", result.getOrThrow().toString());
    }

    public record PropertiesPart(boolean autoGenerateShadow, float autoShadowMultiplier, boolean forceEnableShadow) {
        public static final boolean AUTO_GENERATE_SHADOW_DEFAULT = false;
        public static final float AUTO_SHADOW_MULTIPLIER_DEFAULT = 0.25f;
        public static final boolean FORCE_ENABLE_SHADOW_DEFAULT = false;

        public static final PropertiesPart DEFAULT = new PropertiesPart(AUTO_GENERATE_SHADOW_DEFAULT, AUTO_SHADOW_MULTIPLIER_DEFAULT, FORCE_ENABLE_SHADOW_DEFAULT);

        private static final Codec<PropertiesPart> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                Codec.BOOL.optionalFieldOf("auto_generate_shadow", AUTO_GENERATE_SHADOW_DEFAULT).forGetter(part -> part.autoGenerateShadow),
                ExtraCodecs.floatRange(0, 1).optionalFieldOf("auto_shadow_multiplier", AUTO_SHADOW_MULTIPLIER_DEFAULT).forGetter(part -> part.autoShadowMultiplier),
                Codec.BOOL.optionalFieldOf("force_enable_shadow", FORCE_ENABLE_SHADOW_DEFAULT).forGetter(part -> part.forceEnableShadow)
            ).apply(
                instance, PropertiesPart::new
            )
        );
    }

    public record WhenPart(@Nullable List<ColourPredicateTest> colour, @Nullable List<ColourPredicateTest> shadowColour, @Nullable Boolean bold, @Nullable Boolean italic, @Nullable Boolean underlined, @Nullable Boolean strikethrough, @Nullable Boolean obfuscated, @Nullable ResourceLocation font) {
        private static final Codec<WhenPart> CODEC = RecordCodecBuilder.create((RecordCodecBuilder.Instance<WhenPart> instance) ->
            instance.group(
                COLOUR_PREDICATE_LIST_CODEC.optionalFieldOf("color").forGetter((part) -> Optional.ofNullable(ColourPredicateTest.testsToPredicates(part.colour))),
                COLOUR_PREDICATE_LIST_CODEC.optionalFieldOf("shadow_color").forGetter(part -> Optional.ofNullable(ColourPredicateTest.testsToPredicates(part.shadowColour))),
                Codec.BOOL.optionalFieldOf("bold").forGetter(part -> Optional.ofNullable(part.bold)),
                Codec.BOOL.optionalFieldOf("italic").forGetter(part -> Optional.ofNullable(part.italic)),
                Codec.BOOL.optionalFieldOf("underlined").forGetter(part -> Optional.ofNullable(part.underlined)),
                Codec.BOOL.optionalFieldOf("strikethrough").forGetter(part -> Optional.ofNullable(part.strikethrough)),
                Codec.BOOL.optionalFieldOf("obfuscated").forGetter(part -> Optional.ofNullable(part.obfuscated)),
                ResourceLocation.CODEC.optionalFieldOf("font").forGetter(part -> Optional.ofNullable(part.font))
            ).apply(
                instance, (Optional<List<ColourPredicate>> colour, Optional<List<ColourPredicate>> shadowColour, Optional<Boolean> bold, Optional<Boolean> italic, Optional<Boolean> underlined, Optional<Boolean> strikethrough, Optional<Boolean> obfuscated, Optional<ResourceLocation> font) -> new WhenPart(
                    ColourPredicateTest.predicatesToTests(colour.orElse(null)),
                    ColourPredicateTest.predicatesToTests(shadowColour.orElse(null)),
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

    public record ReplaceWithPart(@Nullable Integer colour, @Nullable Integer shadowColour) {
        public static final Codec<ReplaceWithPart> CODEC = RecordCodecBuilder.create((RecordCodecBuilder.Instance<ReplaceWithPart> instance) ->
            instance.group(
                ColourCodecs.RGB_HEX_CODEC.optionalFieldOf("color").forGetter((part) -> Optional.ofNullable(part.colour)),
                ColourCodecs.RGB_HEX_CODEC.optionalFieldOf("shadow_color").forGetter(part -> Optional.ofNullable(part.shadowColour))
            )
            .apply(
                instance, (Optional<Integer> colour, Optional<Integer> shadowColour) -> new ReplaceWithPart(
                    colour.orElse(null),
                    shadowColour.orElse(null)
                )
            )
        );
    }
}
