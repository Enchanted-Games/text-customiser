package games.enchanted.eg_text_customiser.common.pack.colour_override;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import games.enchanted.eg_text_customiser.common.Logging;
import games.enchanted.eg_text_customiser.common.fake_style.FakeStyle;
import games.enchanted.eg_text_customiser.common.fake_style.SpecialTextColour;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.ColourPredicates;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates.BasicColourPredicate;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates.ColourPredicate;
import games.enchanted.eg_text_customiser.common.pack.style_override.StyleOverrideDefinition;
import net.minecraft.network.chat.Style;

public class ColourOverrideDefinition {
    public static final Codec<ColourPredicate> SIMPLE_OR_SIGN_COLOUR_CODEC = ColourPredicates.CODEC;

    public static final Codec<ColourOverrideDefinition> CODEC = RecordCodecBuilder.create((RecordCodecBuilder.Instance<ColourOverrideDefinition> instance) ->
        instance.group(
            SIMPLE_OR_SIGN_COLOUR_CODEC.fieldOf("replace_color").forGetter((override) -> override.colourPredicate),
            StyleOverrideDefinition.ReplaceWithPart.CODEC.fieldOf("with").forGetter((override) -> override.replacement)
        ).apply(
            instance,
            ColourOverrideDefinition::new
        )
    );

    final ColourPredicate colourPredicate;
    final StyleOverrideDefinition.ReplaceWithPart replacement;

    public ColourOverrideDefinition(ColourPredicate colourPredicate, StyleOverrideDefinition.ReplaceWithPart replaceWithPart) {
        this.colourPredicate = colourPredicate;

        this.replacement = replaceWithPart;
    }

    public boolean styleMatches(FakeStyle style) {
        return colourPredicate.colourMatches(style.colour());
    }

    public Style applyToStyleIfMatching(Style style) {
        return Style.EMPTY.withColor(0xff00ff);
    }
    public FakeStyle applyToStyleIfMatching(FakeStyle style) {
        return new FakeStyle(new SpecialTextColour(0xff00ff), style.shadowColour(), style.bold(), style.italic(), style.underlined(), style.strikethrough(), style.obfuscated(), style.font());
    }

    public static void printExample() {
        DataResult<JsonElement> result = ColourOverrideDefinition.CODEC.encodeStart(JsonOps.INSTANCE, new ColourOverrideDefinition(
            new BasicColourPredicate(new SpecialTextColour("red")),
            new StyleOverrideDefinition.ReplaceWithPart(0xffffff, 0x777777, null, null, null, null, null, null)
        ));
        if(result.error().isPresent()) {
            throw new RuntimeException(result.error().get().message());
        }

        Logging.info("Example file: {}", result.getOrThrow().toString());
    }
}
