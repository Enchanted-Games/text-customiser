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
    @Shadow public abstract int shadowColor();
    @Shadow abstract boolean hasShadow();

    @Mutable @Shadow @Final int color;
    @Mutable @Shadow @Final private int shadowColor;
    //?} else {
    /*@Mutable @Shadow @Final protected float a;
    @Mutable @Shadow @Final protected float r;
    @Mutable @Shadow @Final protected float g;
    @Mutable @Shadow @Final protected float b;
    *///?}

    //? if minecraft: >= 1.21.5 {
    @Override
    public void eg_text_customiser$applyEffectOverride(Style style, DecorationType decorationType, boolean isDropShadow) {
        TextOverrideManager.replaceColour(color, shadowColor(), style, hasShadow(), DecorationType.NONE, colourRGBA -> this.color = colourRGBA, shadowRGBA -> this.shadowColor = shadowRGBA);
    }
    //?} else {
    /*@Override
    public void eg_text_customiser$applyEffectOverride(Style style, DecorationType decorationType, boolean isDropShadow) {
        final int mainColour = ColourUtil.calcMainColour(isDropShadow, this.a, this.r, this.g, this.b);
        final int shadowColour = ColourUtil.calcShadowColour(isDropShadow, this.a, this.r, this.g, this.b);
        TextOverrideManager.replaceColour(
            mainColour,
            shadowColour,
            style,
            true,
            DecorationType.NONE,
            colourARGB -> {
                if(!isDropShadow) {
                    int[] argb = ColourUtil.ARGBint_to_ARGB(colourARGB);
                    this.a = argb[0] / 255f;
                    this.r = argb[1] / 255f;
                    this.g = argb[2] / 255f;
                    this.b = argb[3] / 255f;
                }
            },
            shadowARGB -> {
                if(isDropShadow) {
                    int[] argb = ColourUtil.ARGBint_to_ARGB(shadowARGB);
                    this.a = argb[0] / 255f;
                    this.r = argb[1] / 255f;
                    this.g = argb[2] / 255f;
                    this.b = argb[3] / 255f;
                }
            }
        );
    }
    *///?}
}