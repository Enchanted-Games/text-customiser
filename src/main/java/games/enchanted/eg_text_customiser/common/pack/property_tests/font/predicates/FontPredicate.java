package games.enchanted.eg_text_customiser.common.pack.property_tests.font.predicates;

import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceLocation;

public interface FontPredicate {
    boolean fontMatches(ResourceLocation colour);
    MapCodec<? extends FontPredicate> codec();
}