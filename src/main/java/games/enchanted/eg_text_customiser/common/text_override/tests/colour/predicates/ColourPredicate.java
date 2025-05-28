package games.enchanted.eg_text_customiser.common.text_override.tests.colour.predicates;

import com.mojang.serialization.MapCodec;
import games.enchanted.eg_text_customiser.common.text_override.fake_style.SpecialTextColour;

public interface ColourPredicate {
    boolean colourMatches(SpecialTextColour colour);
    MapCodec<? extends ColourPredicate> codec();
}
