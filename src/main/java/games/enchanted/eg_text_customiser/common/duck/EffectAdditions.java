package games.enchanted.eg_text_customiser.common.duck;

import games.enchanted.eg_text_customiser.common.fake_style.DecorationType;
import net.minecraft.network.chat.Style;

public interface EffectAdditions {
    void eg_text_customiser$applyEffectOverride(Style style, DecorationType decorationType, boolean isDropShadow);
}
