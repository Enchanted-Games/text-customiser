package games.enchanted.eg_text_customiser.common.pack.property_tests.colour.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import games.enchanted.eg_text_customiser.common.fake_style.SignTextData;
import games.enchanted.eg_text_customiser.common.fake_style.SpecialTextColour;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SignDyeColourPredicate implements ColourPredicate {
    public static final MapCodec<SignDyeColourPredicate> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            DyeColor.CODEC.optionalFieldOf("dye").forGetter(predicate -> Optional.ofNullable(predicate.dyeColor)),
            Codec.BOOL.optionalFieldOf("glowing", false).forGetter(predicate -> predicate.glowing),
            Codec.BOOL.optionalFieldOf("is_glowing_outline", false).forGetter(predicate -> predicate.isGlowingOutline)
        ).apply(
            instance,
            (dyeColor, glowing, isGlowingOutline) -> new SignDyeColourPredicate(dyeColor.orElse(null), glowing, isGlowingOutline)
        )
    );

    private final @Nullable DyeColor dyeColor;
    private final boolean glowing;
    private final boolean isGlowingOutline;

    SignDyeColourPredicate(@Nullable DyeColor dyeColor, boolean glowing, boolean isGlowingOutline) {
        this.dyeColor = dyeColor;
        this.glowing = glowing;
        this.isGlowingOutline = isGlowingOutline;
    }

    @Override
    public boolean colourMatches(SpecialTextColour colour) {
        if(colour == null) return false;
        // These colours in SignTextData are not used for comparison, just placeholders
        return colour.equals(SpecialTextColour.fromSignTextData(new SignTextData(
            dyeColor == null ? colour.getDyeColor() : dyeColor,
            glowing,
            0,
            0
            ), isGlowingOutline)
        );
    }

    @Override
    public MapCodec<? extends ColourPredicate> codec() {
        return MAP_CODEC;
    }
}
