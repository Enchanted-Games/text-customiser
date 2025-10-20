package games.enchanted.eg_text_customiser.common.pack.property_tests.character;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import games.enchanted.eg_text_customiser.common.pack.property_tests.character.predicates.BasicCharacterPredicate;
import games.enchanted.eg_text_customiser.common.pack.property_tests.character.predicates.CharacterPredicate;
import games.enchanted.eg_text_customiser.common.serialization.ModCodecs;
import games.enchanted.eg_text_customiser.common.util.ResourceLocationUtil;
import net.minecraft.resources.ResourceLocation;

public class CharacterPredicates {
    private static final ModCodecs.IdToElmMapper<ResourceLocation, MapCodec<? extends CharacterPredicate>> CHARACTER_PREDICATES_MAPPER = new ModCodecs.IdToElmMapper<>();
    public static final Codec<CharacterPredicate> CODEC = Codec.withAlternative(
        CHARACTER_PREDICATES_MAPPER.codec(ResourceLocation.CODEC).dispatch(CharacterPredicate::codec, mapCodec -> mapCodec),
        BasicCharacterPredicate.CODEC
    );

    public static void registerCharacterPredicates() {
        CHARACTER_PREDICATES_MAPPER.put(ResourceLocationUtil.ofMod("simple"), BasicCharacterPredicate.MAP_CODEC);
    }
}
