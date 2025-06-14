package games.enchanted.eg_text_customiser.common.pack.property_tests.font.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public class BasicFontPredicate implements FontPredicate {
    public static final Codec<? extends FontPredicate> CODEC = ResourceLocation.CODEC.comapFlatMap(
        input -> DataResult.success(new BasicFontPredicate(input)),
        input -> input.comparisonFont
    );
    public static final MapCodec<BasicFontPredicate> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            ResourceLocation.CODEC.fieldOf("value").forGetter(predicate -> predicate.comparisonFont)
        ).apply(
            instance,
            BasicFontPredicate::new
        )
    );

    private final ResourceLocation comparisonFont;

    public BasicFontPredicate(ResourceLocation comparisonFont) {
        this.comparisonFont = comparisonFont;
    }

    @Override
    public boolean fontMatches(ResourceLocation font) {
        return font.equals(comparisonFont);
    }

    @Override
    public MapCodec<? extends FontPredicate> codec() {
        return MAP_CODEC;
    }
}
