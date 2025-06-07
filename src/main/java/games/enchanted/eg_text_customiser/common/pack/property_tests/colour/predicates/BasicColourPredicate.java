package games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import games.enchanted.eg_text_customiser.common.serialization.ColourCodecs;
import games.enchanted.eg_text_customiser.common.fake_style.SpecialTextColour;

public class BasicColourPredicate implements ColourPredicate {
    public static final Codec<? extends ColourPredicate> NAMED_ONLY_CODEC = ColourCodecs.NAMED_COLOUR_CODEC.comapFlatMap(
        input -> DataResult.success(new BasicColourPredicate(new SpecialTextColour(input))),
        input -> input.comparisonColour.getColourValueOrName().right().orElseThrow(
            () -> new IllegalArgumentException("Invalid value '" + input.comparisonColour.getColourValueOrName() + "', must be a named colour.")
        )
    );
    public static final Codec<? extends ColourPredicate> CODEC = ColourCodecs.HEX_OR_NAMED_CODEC.comapFlatMap(
        input -> DataResult.success(new BasicColourPredicate(SpecialTextColour.fromEither(input))),
        input -> input.comparisonColour.getColourValueOrName()
    );
    public static final MapCodec<BasicColourPredicate> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            ColourCodecs.HEX_OR_NAMED_CODEC.fieldOf("value").forGetter(predicate -> predicate.comparisonColour.getColourValueOrName())
        ).apply(
            instance,
            (colourValueOrName) -> new BasicColourPredicate(SpecialTextColour.fromEither(colourValueOrName))
        )
    );

    private final SpecialTextColour comparisonColour;

    public BasicColourPredicate(SpecialTextColour comparisonColour) {
        this.comparisonColour = comparisonColour;
    }

    @Override
    public boolean colourMatches(SpecialTextColour colour) {
        return colour.equals(comparisonColour);
    }

    @Override
    public MapCodec<? extends ColourPredicate> codec() {
        return MAP_CODEC;
    }
}
