package games.enchanted.eg_text_customiser.common.text_override.tests.colour;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import games.enchanted.eg_text_customiser.common.text_override.tests.colour.predicates.BasicColourPredicate;
import games.enchanted.eg_text_customiser.common.util.ResourceLocationUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

public class ColourPredicates {
    private static final ExtraCodecs.LateBoundIdMapper<ResourceLocation, MapCodec<? extends ColourPredicate>> COLOUR_PREDICATES_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();
    public static final Codec<ColourPredicate> CODEC = COLOUR_PREDICATES_MAPPER.codec(ResourceLocation.CODEC).dispatch(ColourPredicate::codec, mapCodec -> mapCodec);

    public static void registerColourPredicates() {
        COLOUR_PREDICATES_MAPPER.put(ResourceLocationUtil.ofMod("simple"), BasicColourPredicate.MAP_CODEC);
    }
}
