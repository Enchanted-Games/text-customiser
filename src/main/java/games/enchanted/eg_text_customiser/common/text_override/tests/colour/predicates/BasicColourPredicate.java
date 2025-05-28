package games.enchanted.eg_text_customiser.common.text_override.tests.colour.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import games.enchanted.eg_text_customiser.common.serialization.ColourCodecs;
import games.enchanted.eg_text_customiser.common.text_override.fake_style.SpecialTextColour;
import games.enchanted.eg_text_customiser.common.text_override.tests.colour.ColourPredicate;

public class BasicColourPredicate implements ColourPredicate {
    public static final Codec<BasicColourPredicate> CODEC = ColourCodecs.COLOUR_CODEC.comapFlatMap(
        input -> DataResult.success(new BasicColourPredicate(input)),
        input -> input.colour
    );
    public static final MapCodec<BasicColourPredicate> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            ColourCodecs.COLOUR_CODEC.fieldOf("value").forGetter(predicate -> predicate.colour)
        ).apply(
            instance,
            BasicColourPredicate::new
        )
    );

    private final int colour;

    public BasicColourPredicate(int colour) {
        this.colour = colour;
    }

    @Override
    public boolean colourMatches(SpecialTextColour colour) {
        return false;
    }

    @Override
    public MapCodec<? extends ColourPredicate> codec() {
        return MAP_CODEC;
    }
}
