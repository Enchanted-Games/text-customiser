package games.enchanted.eg_text_customiser.common.pack.property_tests.font.predicates;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BasicAtlasFontPredicate implements FontPredicate {
    public static final MapCodec<BasicAtlasFontPredicate> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            ResourceLocation.CODEC.optionalFieldOf("atlas").forGetter(predicate -> Optional.ofNullable(predicate.atlas)),
            ResourceLocation.CODEC.optionalFieldOf("sprite").forGetter(predicate -> Optional.ofNullable(predicate.sprite))
        ).apply(
            instance,
            (atlasLocation, spriteLocation) -> new BasicAtlasFontPredicate(
                atlasLocation.orElse(null),
                spriteLocation.orElse(null)
            )
        )
    );

    private final @Nullable ResourceLocation atlas;
    private final @Nullable ResourceLocation sprite;

    public BasicAtlasFontPredicate(@Nullable ResourceLocation atlas, @Nullable ResourceLocation sprite) {
        this.atlas = atlas;
        this.sprite = sprite;
    }

    @Override
    public boolean fontMatches(FontDescription font) {
        if(!(font instanceof FontDescription.AtlasSprite spriteFont)) {
            return false;
        }
        return (this.atlas == null || this.atlas.equals(spriteFont.atlasId())) && (this.sprite == null || this.sprite.equals(spriteFont.spriteId()));
    }

    @Override
    public MapCodec<? extends FontPredicate> codec() {
        return MAP_CODEC;
    }
}
