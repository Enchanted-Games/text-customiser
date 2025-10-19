package games.enchanted.eg_text_customiser.common.pack.property_tests.font.predicates;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ResourceLocationPattern;

public class RegexAtlasFontPredicate implements FontPredicate {
    public static final MapCodec<RegexAtlasFontPredicate> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            ResourceLocationPattern.CODEC.fieldOf("atlas").forGetter(predicate -> predicate.atlas),
            ResourceLocationPattern.CODEC.fieldOf("sprite").forGetter(predicate -> predicate.sprite)
        ).apply(
            instance,
            RegexAtlasFontPredicate::new
        )
    );

    private final ResourceLocationPattern atlas;
    private final ResourceLocationPattern sprite;

    public RegexAtlasFontPredicate(ResourceLocationPattern atlas, ResourceLocationPattern sprite) {
        this.atlas = atlas;
        this.sprite = sprite;
    }

    @Override
    public boolean fontMatches(FontDescription font) {
        if(!(font instanceof FontDescription.AtlasSprite(ResourceLocation atlasId, ResourceLocation spriteId))) {
            return false;
        }
        return this.atlas.locationPredicate().test(atlasId) && this.sprite.locationPredicate().test(spriteId);
    }

    @Override
    public MapCodec<? extends FontPredicate> codec() {
        return MAP_CODEC;
    }
}
