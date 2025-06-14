package games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import games.enchanted.eg_text_customiser.common.fake_style.SpecialTextColour;
import games.enchanted.eg_text_customiser.common.serialization.ColourCodecs;
import games.enchanted.eg_text_customiser.common.util.ColourUtil;

public class RangeColourPredicate implements ColourPredicate {
    public static final MapCodec<RangeColourPredicate> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            ColourCodecs.HEX_OR_RGB_LIST_CODEC.fieldOf("min").forGetter(predicate -> ColourUtil.RGB_to_RGBint(predicate.minComparison[0], predicate.minComparison[1], predicate.minComparison[2])),
            ColourCodecs.HEX_OR_RGB_LIST_CODEC.fieldOf("max").forGetter(predicate -> ColourUtil.RGB_to_RGBint(predicate.maxComparison[0], predicate.maxComparison[1], predicate.maxComparison[2]))
        ).apply(
            instance,
            RangeColourPredicate::new
        )
    );

    private final int[] minComparison;
    private final int[] maxComparison;

    public RangeColourPredicate(Integer minComparison, Integer maxComparison) {
        this.minComparison = ColourUtil.RGBint_to_RGB(minComparison);
        this.maxComparison = ColourUtil.RGBint_to_RGB(maxComparison);
    }

    @Override
    public boolean colourMatches(SpecialTextColour colour) {
        int[] testParts = ColourUtil.RGBint_to_RGB(colour.safeGetAsRGB());
        for (int i = 0; i < 3; i++) {
            boolean inRange = testParts[i] >= minComparison[i] && testParts[i] <= maxComparison[i];
            if(!inRange) return false;
        }
        return true;
    }

    @Override
    public MapCodec<? extends ColourPredicate> codec() {
        return MAP_CODEC;
    }
}
