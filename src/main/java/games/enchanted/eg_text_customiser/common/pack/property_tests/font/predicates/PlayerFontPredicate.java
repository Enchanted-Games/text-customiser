package games.enchanted.eg_text_customiser.common.pack.property_tests.font.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.component.ResolvableProfile;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.regex.Pattern;

public class PlayerFontPredicate implements FontPredicate {
    public static final MapCodec<PlayerFontPredicate> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            ExtraCodecs.PATTERN.optionalFieldOf("name").forGetter(predicate -> Optional.ofNullable(predicate.name)),
            Codec.BOOL.optionalFieldOf("hat").forGetter(predicate -> Optional.ofNullable(predicate.hat))
        ).apply(
            instance,
            (pattern, hat) -> new PlayerFontPredicate(
                pattern.orElse(null),
                hat.orElse(null)
            )
        )
    );

    private final @Nullable Pattern name;
    private final @Nullable Boolean hat;

    public PlayerFontPredicate(@Nullable Pattern name, @Nullable Boolean hat) {
        this.name = name;
        this.hat = hat;
    }

    @Override
    public boolean fontMatches(FontDescription font) {
        if(!(font instanceof FontDescription.PlayerSprite(
            ResolvableProfile profile, boolean fontHat
        ))) {
            return false;
        }
        Optional<String> profileName = profile.name();
        return
            (this.name == null || (profileName.filter(name -> this.name.pattern().matches(name)).isPresent())) &&
            (this.hat == null || this.hat == fontHat);
    }

    @Override
    public MapCodec<? extends FontPredicate> codec() {
        return null;
    }
}
