package games.enchanted.eg_text_customiser.common.pack.property_tests.font.predicates;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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
    public boolean fontMatches(ResourceLocation font) {
        return pattern.locationPredicate().test(font);
    }

    @Override
    public MapCodec<? extends FontPredicate> codec() {
        return MAP_CODEC;
    }
}
