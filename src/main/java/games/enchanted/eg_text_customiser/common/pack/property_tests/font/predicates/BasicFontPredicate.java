package games.enchanted.eg_text_customiser.common.pack.property_tests.font.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import games.enchanted.eg_text_customiser.common.serialization.ModCodecs;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.resources.Identifier;

public class BasicFontPredicate implements FontPredicate {
    public static final Codec<? extends FontPredicate> CODEC = ModCodecs.RESOURCE_FONT_DESCRIPTION.comapFlatMap(
        input -> DataResult.success(new BasicFontPredicate(input)),
        input -> input.comparisonFont
    );
    public static final MapCodec<BasicFontPredicate> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            ModCodecs.RESOURCE_FONT_DESCRIPTION.fieldOf("value").forGetter(predicate -> predicate.comparisonFont)
        ).apply(
            instance,
            BasicFontPredicate::new
        )
    );

    private final FontDescription comparisonFont;

    public BasicFontPredicate(FontDescription comparisonFont) {
        this.comparisonFont = comparisonFont;
    }

    @Override
    public boolean fontMatches(FontDescription font) {
        return font.equals(comparisonFont);
    }

    @Override
    public MapCodec<? extends FontPredicate> codec() {
        return MAP_CODEC;
    }
}
