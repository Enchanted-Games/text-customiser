package games.enchanted.eg_text_customiser.common.pack.property_tests.font;


import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import games.enchanted.eg_text_customiser.common.pack.property_tests.font.predicates.BasicFontPredicate;
import games.enchanted.eg_text_customiser.common.pack.property_tests.font.predicates.FontPredicate;
import games.enchanted.eg_text_customiser.common.pack.property_tests.font.predicates.RegexFontPredicate;
import games.enchanted.eg_text_customiser.common.serialization.ModCodecs;
import games.enchanted.eg_text_customiser.common.util.ResourceLocationUtil;
import net.minecraft.resources.ResourceLocation;

public class FontPredicates {
    private static final ModCodecs.IdToElmMapper<ResourceLocation, MapCodec<? extends FontPredicate>> FONT_PREDICATES_MAPPER = new ModCodecs.IdToElmMapper<>();
    public static final Codec<FontPredicate> CODEC = Codec.withAlternative(
        FONT_PREDICATES_MAPPER.codec(ResourceLocation.CODEC).dispatch(FontPredicate::codec, mapCodec -> mapCodec),
        BasicFontPredicate.CODEC
    );

    public static void registerFontPredicates() {
        FONT_PREDICATES_MAPPER.put(ResourceLocationUtil.ofMod("simple"), BasicFontPredicate.MAP_CODEC);
        FONT_PREDICATES_MAPPER.put(ResourceLocationUtil.ofMod("regex"), RegexFontPredicate.MAP_CODEC);
    }
}
