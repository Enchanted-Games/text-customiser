package games.enchanted.eg_text_customiser.common.mixin;

import games.enchanted.eg_text_customiser.common.duck.EffectAdditions;
import games.enchanted.eg_text_customiser.common.fake_style.DecorationType;
import games.enchanted.eg_text_customiser.common.pack.TextOverrideManager;
import games.enchanted.eg_text_customiser.common.util.ColourUtil;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.*;

import net.minecraft.client.gui.font.glyphs.BakedGlyph;

@Mixin(BakedGlyph.Effect.class)
public abstract class EffectMixin implements EffectAdditions {

    //? if minecraft: >= 1.21.5 {
    @Shadow public abstract int color();
    @Shadow public abstract int shadowColor();
    @Shadow abstract boolean hasShadow();

    @Mutable @Shadow @Final int color;
    @Shadow @Final private float shadowOffset;
    //?} else {
    /*@Mutable @Shadow @Final protected float a;
    @Mutable @Shadow @Final protected float r;
    @Mutable @Shadow @Final protected float g;
    @Mutable @Shadow @Final protected float b;
    *///?}

    @Shadow @Final protected float y0;
    @Shadow @Final protected float x0;
    @Shadow @Final protected float x1;
    @Shadow @Final protected float y1;
    @Shadow @Final protected float depth;

    //? if minecraft: >= 1.21.5 {
    @Override
    public BakedGlyph.Effect eg_text_customiser$applyEffectOverride(Style style, DecorationType decorationType, boolean isDropShadow) {
        final int[] newCols = {this.color(), this.shadowColor()};
        TextOverrideManager.replaceColour(color, shadowColor(), style, hasShadow(), decorationType, colourRGBA -> newCols[0] = colourRGBA, shadowRGBA -> newCols[1] = shadowRGBA);
        return new BakedGlyph.Effect(this.x0, this.y0, this.x1, this.y1, this.depth, newCols[0], newCols[1], this.shadowOffset);
    }
    //?} else {
    /*@Override
    public BakedGlyph.Effect eg_text_customiser$applyEffectOverride(Style style, DecorationType decorationType, boolean isDropShadow) {
        final int mainColour = ColourUtil.calcMainColour(isDropShadow, this.a, this.r, this.g, this.b);
        final int shadowColour = ColourUtil.calcShadowColour(isDropShadow, this.a, this.r, this.g, this.b);
        final float[] newCol = {this.a, this.r, this.g, this.b};
        TextOverrideManager.replaceColour(
            mainColour,
            shadowColour,
            style,
            true,
            decorationType,
            colourARGB -> {
                if(!isDropShadow) {
                    int[] argb = ColourUtil.ARGBint_to_ARGB(colourARGB);
                    newCol[0] = argb[0] / 255f;
                    newCol[1] = argb[1] / 255f;
                    newCol[2] = argb[2] / 255f;
                    newCol[3] = argb[3] / 255f;
                }
            },
            shadowARGB -> {
                if(isDropShadow) {
                    int[] argb = ColourUtil.ARGBint_to_ARGB(shadowARGB);
                    newCol[0] = argb[0] / 255f;
                    newCol[1] = argb[1] / 255f;
                    newCol[2] = argb[2] / 255f;
                    newCol[3] = argb[3] / 255f;
                }
            }
        );
        return new BakedGlyph.Effect(this.x0, this.y0, this.x1, this.y1, this.depth, newCol[1], newCol[2], newCol[3], newCol[0]);
    }
    *///?}
}