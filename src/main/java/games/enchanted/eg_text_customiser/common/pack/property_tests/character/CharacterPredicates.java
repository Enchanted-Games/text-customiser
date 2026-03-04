package games.enchanted.eg_text_customiser.common.pack.property_tests.character;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import games.enchanted.eg_text_customiser.common.pack.property_tests.character.predicates.BasicCharacterPredicate;
import games.enchanted.eg_text_customiser.common.pack.property_tests.character.predicates.CharacterPredicate;
import games.enchanted.eg_text_customiser.common.pack.property_tests.character.predicates.NotCharacterPredicate;
import games.enchanted.eg_text_customiser.common.serialization.ModCodecs;
import games.enchanted.eg_text_customiser.common.util.IdentifierUtil;
import net.minecraft.resources.Identifier;

public class CharacterPredicates {
    private static final ModCodecs.IdToElmMapper<Identifier, MapCodec<? extends CharacterPredicate>> CHARACTER_PREDICATES_MAPPER = new ModCodecs.IdToElmMapper<>();
    public static final Codec<CharacterPredicate> CODEC = Codec.withAlternative(
        CHARACTER_PREDICATES_MAPPER.codec(Identifier.CODEC).dispatch(CharacterPredicate::codec, mapCodec -> mapCodec),
        BasicCharacterPredicate.CODEC
    );

    public static void registerCharacterPredicates() {
        CHARACTER_PREDICATES_MAPPER.put(IdentifierUtil.ofMod("simple"), BasicCharacterPredicate.MAP_CODEC);
        CHARACTER_PREDICATES_MAPPER.put(IdentifierUtil.ofMod("not"), NotCharacterPredicate.MAP_CODEC);
    }
}
