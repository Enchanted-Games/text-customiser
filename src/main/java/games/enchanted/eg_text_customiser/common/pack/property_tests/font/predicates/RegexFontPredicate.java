package games.enchanted.eg_text_customiser.common.pack.property_tests.font.predicates;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.resources.Identifier;
import net.minecraft.util.IdentifierPattern;

public class RegexFontPredicate implements FontPredicate {
    public static final MapCodec<RegexFontPredicate> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            IdentifierPattern.CODEC.fieldOf("regex").forGetter(predicate -> predicate.pattern)
        ).apply(
            instance,
            RegexFontPredicate::new
        )
    );

    private final IdentifierPattern pattern;

    public RegexFontPredicate(IdentifierPattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean fontMatches(FontDescription font) {
        if(!(font instanceof FontDescription.Resource(Identifier id))) {
            return false;
        }
        return pattern.locationPredicate().test(id);
    }

    @Override
    public MapCodec<? extends FontPredicate> codec() {
        return MAP_CODEC;
    }
}
