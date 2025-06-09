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
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.ColourPredicates;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates.BasicColourPredicate;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates.ColourPredicate;
import games.enchanted.eg_text_customiser.common.serialization.ColourCodecs;
import net.minecraft.network.chat.Style;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ColourOverrideDefinition {
    public static final Codec<List<ColourPredicate>> SIMPLE_OR_SIGN_COLOUR_CODEC = ExtraCodecs.compactListCodec(ColourPredicates.CODEC);

    public static final Codec<ColourOverrideDefinition> CODEC = RecordCodecBuilder.create((RecordCodecBuilder.Instance<ColourOverrideDefinition> instance) ->
        instance.group(
            SIMPLE_OR_SIGN_COLOUR_CODEC.fieldOf("replace_color").forGetter((override) -> override.colourPredicates),
            ColourOverrideDefinition.ReplaceWithPart.CODEC.fieldOf("with").forGetter((override) -> override.replacement)
        ).apply(
            instance,
            ColourOverrideDefinition::new
        )
    );

    final List<ColourPredicate> colourPredicates;
    final ColourOverrideDefinition.ReplaceWithPart replacement;

    public ColourOverrideDefinition(List<ColourPredicate> colourPredicates, ColourOverrideDefinition.ReplaceWithPart replaceWithPart) {
        this.colourPredicates = colourPredicates;

        this.replacement = replaceWithPart;
    }

    public boolean styleMatches(FakeStyle style) {
        for (ColourPredicate predicate : colourPredicates) {
            if(predicate.colourMatches(style.colour())) {
                return true;
            }
        }
        return false;
    }

    public Style applyToStyle(Style style) {
        Style modifiedStyle = style;
        if(replacement.colour != null) {
            modifiedStyle = modifiedStyle.withColor(replacement.colour);
        }
        if(replacement.shadowColour != null) {
            modifiedStyle = modifiedStyle.withShadowColor(replacement.shadowColour + 0xff000000);
        }
        ((StyleAdditions) modifiedStyle).eg_text_customiser$setHasBeenOverridden(true);
        return modifiedStyle;
    }

    public FakeStyle applyToStyle(FakeStyle style) {
        return new FakeStyle(
            replacement.colour == null ? style.colour() : new SpecialTextColour(replacement.colour),
            replacement.shadowColour == null ? style.shadowColour() : replacement.shadowColour,
            style.bold(),
            style.italic(),
            style.underlined(),
            style.strikethrough(),
            style.obfuscated(),
            style.font(),
            true
        );
    }

    public static void printExample() {
        DataResult<JsonElement> result = ColourOverrideDefinition.CODEC.encodeStart(JsonOps.INSTANCE, new ColourOverrideDefinition(
            List.of(new BasicColourPredicate(new SpecialTextColour("red"))),
            new ReplaceWithPart(0xffffff, 0x777777)
        ));
        if(result.error().isPresent()) {
            throw new RuntimeException(result.error().get().message());
        }

        Logging.info("Example file: {}", result.getOrThrow().toString());
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
