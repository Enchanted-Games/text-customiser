package games.enchanted.eg_text_customiser.common.pack.property_tests.colour;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates.BasicColourPredicate;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates.ColourPredicate;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates.RangeColourPredicate;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates.SignDyeColourPredicate;
import games.enchanted.eg_text_customiser.common.serialization.ModCodecs;
import games.enchanted.eg_text_customiser.common.util.IdentifierUtil;
import net.minecraft.resources.Identifier;

public class ColourPredicates {
    private static final ModCodecs.IdToElmMapper<Identifier, MapCodec<? extends ColourPredicate>> COLOUR_PREDICATES_MAPPER = new ModCodecs.IdToElmMapper<>();
    public static final Codec<ColourPredicate> CODEC = Codec.withAlternative(
        COLOUR_PREDICATES_MAPPER.codec(Identifier.CODEC).dispatch(ColourPredicate::codec, mapCodec -> mapCodec),
        BasicColourPredicate.CODEC
    );

    public static void registerColourPredicates() {
        COLOUR_PREDICATES_MAPPER.put(IdentifierUtil.ofMod("simple"), BasicColourPredicate.MAP_CODEC);
        COLOUR_PREDICATES_MAPPER.put(IdentifierUtil.ofMod("range"), RangeColourPredicate.MAP_CODEC);
        COLOUR_PREDICATES_MAPPER.put(IdentifierUtil.ofMod("sign_dye"), SignDyeColourPredicate.MAP_CODEC);
    }
}
