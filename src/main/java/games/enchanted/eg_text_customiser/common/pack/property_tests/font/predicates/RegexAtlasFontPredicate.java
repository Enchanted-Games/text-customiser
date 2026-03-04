package games.enchanted.eg_text_customiser.common.pack.property_tests.font.predicates;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.resources.Identifier;
import net.minecraft.util.IdentifierPattern;

public class RegexAtlasFontPredicate implements FontPredicate {
    public static final MapCodec<RegexAtlasFontPredicate> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            IdentifierPattern.CODEC.fieldOf("atlas").forGetter(predicate -> predicate.atlas),
            IdentifierPattern.CODEC.fieldOf("sprite").forGetter(predicate -> predicate.sprite)
        ).apply(
            instance,
            RegexAtlasFontPredicate::new
        )
    );

    private final IdentifierPattern atlas;
    private final IdentifierPattern sprite;

    public RegexAtlasFontPredicate(IdentifierPattern atlas, IdentifierPattern sprite) {
        this.atlas = atlas;
        this.sprite = sprite;
    }

    @Override
    public boolean fontMatches(FontDescription font) {
        if(!(font instanceof FontDescription.AtlasSprite(Identifier atlasId, Identifier spriteId))) {
            return false;
        }
        return this.atlas.locationPredicate().test(atlasId) && this.sprite.locationPredicate().test(spriteId);
    }

    @Override
    public MapCodec<? extends FontPredicate> codec() {
        return MAP_CODEC;
    }
}
