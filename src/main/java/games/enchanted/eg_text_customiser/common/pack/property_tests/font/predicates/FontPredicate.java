package games.enchanted.eg_text_customiser.common.pack.property_tests.font.predicates;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.resources.ResourceLocation;

public interface FontPredicate {
    boolean fontMatches(FontDescription font);
    MapCodec<? extends FontPredicate> codec();
}