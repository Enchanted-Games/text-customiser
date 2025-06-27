package games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import games.enchanted.eg_text_customiser.common.serialization.ColourCodecs;
import games.enchanted.eg_text_customiser.common.fake_style.SpecialTextColour;

public class BasicColourPredicate implements ColourPredicate {
    public static final boolean MATCH_NAMED_DEFAULT_DEFAULT = true;

    public static final Codec<? extends ColourPredicate> CODEC = ColourCodecs.HEX_OR_NAMED_CODEC.comapFlatMap(
        input -> DataResult.success(new BasicColourPredicate(SpecialTextColour.fromEither(input))),
        input -> input.comparisonColour.getColourValueOrName()
    );
    public static final MapCodec<BasicColourPredicate> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            ColourCodecs.HEX_OR_NAMED_CODEC.fieldOf("value").forGetter(predicate -> predicate.comparisonColour.getColourValueOrName()),
            Codec.BOOL.optionalFieldOf("match_named_colors", MATCH_NAMED_DEFAULT_DEFAULT).forGetter(predicate -> predicate.matchNamed)
        ).apply(
            instance,
            (colourValueOrName, strictMatchNamed) -> new BasicColourPredicate(SpecialTextColour.fromEither(colourValueOrName), strictMatchNamed)
        )
    );

    private final boolean matchNamed;
    private final SpecialTextColour comparisonColour;


    public BasicColourPredicate(SpecialTextColour comparisonColour) {
        this(comparisonColour, MATCH_NAMED_DEFAULT_DEFAULT);
    }

    public BasicColourPredicate(SpecialTextColour comparisonColour, boolean matchNamed) {
        this.matchNamed = matchNamed;
        this.comparisonColour = comparisonColour;
    }

    @Override
    public boolean colourMatches(SpecialTextColour colour) {
        if(colour == null) return false;
        return this.comparisonColour.compareTo(colour, matchNamed);
    }

    @Override
    public MapCodec<? extends ColourPredicate> codec() {
        return MAP_CODEC;
    }
}
