package games.enchanted.eg_text_customiser.common.pack.property_tests.font.predicates;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ResourceLocationPattern;

public class RegexFontPredicate implements FontPredicate {
    public static final MapCodec<RegexFontPredicate> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            ResourceLocationPattern.CODEC.fieldOf("regex").forGetter(predicate -> predicate.pattern)
        ).apply(
            instance,
            RegexFontPredicate::new
        )
    );

    private final ResourceLocationPattern pattern;

    public RegexFontPredicate(ResourceLocationPattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean fontMatches(FontDescription font) {
        if(!(font instanceof FontDescription.Resource(ResourceLocation id))) {
            return false;
        }
        return pattern.locationPredicate().test(id);
    }

    @Override
    public MapCodec<? extends FontPredicate> codec() {
        return MAP_CODEC;
    }
}
