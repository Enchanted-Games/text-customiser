package games.enchanted.eg_text_customiser.common.duck;

import games.enchanted.eg_text_customiser.common.fake_style.DecorationType;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.gui.font.glyphs.BakedSheetGlyph;
import net.minecraft.network.chat.Style;

public interface EffectAdditions {
    TextRenderable eg_text_customiser$applyEffectOverride(Style style, DecorationType decorationType, boolean isDropShadow);
}
