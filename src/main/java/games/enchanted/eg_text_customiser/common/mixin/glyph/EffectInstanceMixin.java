package games.enchanted.eg_text_customiser.common.mixin.glyph;

import games.enchanted.eg_text_customiser.common.duck.EffectAdditions;
import games.enchanted.eg_text_customiser.common.fake_style.DecorationType;
import games.enchanted.eg_text_customiser.common.mixin.accessor.FontAccess;
import games.enchanted.eg_text_customiser.common.pack.TextOverrideManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.*;

import net.minecraft.client.gui.font.glyphs.BakedSheetGlyph;

@Mixin(BakedSheetGlyph.EffectInstance.class)
public abstract class EffectInstanceMixin implements EffectAdditions {
    @Shadow public abstract int color();
    @Shadow public abstract int shadowColor();
    @Shadow abstract boolean hasShadow();

    @Mutable @Shadow @Final int color;
    @Shadow @Final private float shadowOffset;

    @Shadow @Final float y0;
    @Shadow @Final float x0;
    @Shadow @Final float x1;
    @Shadow @Final float y1;
    @Shadow @Final float depth;

    @Shadow @Final private BakedSheetGlyph glyph;

    @Override
    public TextRenderable eg_text_customiser$applyEffectOverride(Style style, DecorationType decorationType, boolean isDropShadow) {
        final int[] newCols = {this.color(), this.shadowColor()};
        TextOverrideManager.replaceColour(color, shadowColor(), style, hasShadow(), decorationType, null, colourRGBA -> newCols[0] = colourRGBA, shadowRGBA -> newCols[1] = shadowRGBA);
        return ((FontAccess) Minecraft.getInstance().font).eg_text_customiser$getProvider().effect().createEffect(
            this.x0,
            this.y0,
            this.x1,
            this.y1,
            this.depth,
            newCols[0],
            newCols[1],
            this.shadowOffset
        );
    }
}