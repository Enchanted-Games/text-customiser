package games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import games.enchanted.eg_text_customiser.common.fake_style.SpecialTextColour;
import net.minecraft.world.item.DyeColor;

public class SignDyeColourPredicate implements ColourPredicate {
    public static final MapCodec<SignDyeColourPredicate> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            DyeColor.CODEC.fieldOf("dye").forGetter(predicate -> predicate.dyeColor),
            Codec.BOOL.optionalFieldOf("glowing", false).forGetter(predicate -> predicate.glowing),
            Codec.BOOL.optionalFieldOf("is_glowing_outline", false).forGetter(predicate -> predicate.isGlowingOutline)
        ).apply(
            instance,
            SignDyeColourPredicate::new
        )
    );

    private final DyeColor dyeColor;
    private final boolean glowing;
    private final boolean isGlowingOutline;

    SignDyeColourPredicate(DyeColor dyeColor, boolean glowing, boolean isGlowingOutline) {
        this.dyeColor = dyeColor;
        this.glowing = glowing;
        this.isGlowingOutline = isGlowingOutline;
    }

    @Override
    public boolean colourMatches(SpecialTextColour colour) {
        return colour.equals(new SpecialTextColour(dyeColor.getTextColor(), true, glowing, isGlowingOutline));
    }

    @Override
    public MapCodec<? extends ColourPredicate> codec() {
        return MAP_CODEC;
    }
}
