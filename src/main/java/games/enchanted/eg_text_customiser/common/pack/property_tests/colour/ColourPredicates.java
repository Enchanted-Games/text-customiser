package games.enchanted.eg_text_customiser.common.pack.property_tests.colour;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates.BasicColourPredicate;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates.ColourPredicate;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates.RangeColourPredicate;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates.SignDyeColourPredicate;
import games.enchanted.eg_text_customiser.common.util.ResourceLocationUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

public class ColourPredicates {
    private static final ExtraCodecs.LateBoundIdMapper<ResourceLocation, MapCodec<? extends ColourPredicate>> COLOUR_PREDICATES_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();
    public static final Codec<ColourPredicate> CODEC = Codec.withAlternative(
        COLOUR_PREDICATES_MAPPER.codec(ResourceLocation.CODEC).dispatch(ColourPredicate::codec, mapCodec -> mapCodec),
        BasicColourPredicate.CODEC
    );

    public static void registerColourPredicates() {
        COLOUR_PREDICATES_MAPPER.put(ResourceLocationUtil.ofMod("simple"), BasicColourPredicate.MAP_CODEC);
        COLOUR_PREDICATES_MAPPER.put(ResourceLocationUtil.ofMod("range"), RangeColourPredicate.MAP_CODEC);
        COLOUR_PREDICATES_MAPPER.put(ResourceLocationUtil.ofMod("sign_dye"), SignDyeColourPredicate.MAP_CODEC);
    }
}
