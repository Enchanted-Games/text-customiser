package games.enchanted.eg_text_customiser.common.pack.property_tests.character.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import games.enchanted.eg_text_customiser.common.codepoint.Codepoints;
import org.jetbrains.annotations.Nullable;

public class NotCharacterPredicate implements CharacterPredicate {
    public static final Codec<? extends CharacterPredicate> CODEC = Codepoints.CODEC.comapFlatMap(
        input -> DataResult.success(new NotCharacterPredicate(input)),
        input -> input.codepoints
    );
    public static final MapCodec<NotCharacterPredicate> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            Codepoints.CODEC.fieldOf("chars").forGetter(predicate -> predicate.codepoints)
        ).apply(
            instance,
            NotCharacterPredicate::new
        )
    );

    private final Codepoints codepoints;

    public NotCharacterPredicate(Codepoints codepoints) {
        this.codepoints = codepoints;
    }

    @Override
    public boolean charMatches(@Nullable Integer testCodepoint) {
        if(testCodepoint == null) return false;
        int[] codepoints = this.codepoints.points();
        for (int codepoint : codepoints) {
            if (codepoint == testCodepoint) return false;
        }
        return true;
    }

    @Override
    public MapCodec<? extends CharacterPredicate> codec() {
        return MAP_CODEC;
    }
}
