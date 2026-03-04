package games.enchanted.eg_text_customiser.common.pack.property_tests.font;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import games.enchanted.eg_text_customiser.common.pack.property_tests.font.predicates.*;
import games.enchanted.eg_text_customiser.common.serialization.ModCodecs;
import games.enchanted.eg_text_customiser.common.util.IdentifierUtil;
import net.minecraft.resources.Identifier;

public class FontPredicates {
    private static final ModCodecs.IdToElmMapper<Identifier, MapCodec<? extends FontPredicate>> FONT_PREDICATES_MAPPER = new ModCodecs.IdToElmMapper<>();
    public static final Codec<FontPredicate> CODEC = Codec.withAlternative(
        FONT_PREDICATES_MAPPER.codec(Identifier.CODEC).dispatch(FontPredicate::codec, mapCodec -> mapCodec),
        BasicFontPredicate.CODEC
    );

    public static void registerFontPredicates() {
        FONT_PREDICATES_MAPPER.put(IdentifierUtil.ofMod("simple"), BasicFontPredicate.MAP_CODEC);
        FONT_PREDICATES_MAPPER.put(IdentifierUtil.ofMod("regex"), RegexFontPredicate.MAP_CODEC);
        FONT_PREDICATES_MAPPER.put(IdentifierUtil.ofMod("atlas_sprite"), BasicAtlasFontPredicate.MAP_CODEC);
        FONT_PREDICATES_MAPPER.put(IdentifierUtil.ofMod("atlas_sprite_regex"), RegexAtlasFontPredicate.MAP_CODEC);
        FONT_PREDICATES_MAPPER.put(IdentifierUtil.ofMod("profile"), PlayerFontPredicate.MAP_CODEC);
    }
}
