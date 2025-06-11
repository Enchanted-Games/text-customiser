package games.enchanted.eg_text_customiser.common.mixin;

import games.enchanted.eg_text_customiser.common.duck.StyleAdditions;
import games.enchanted.eg_text_customiser.common.fake_style.FakeStyle;
import games.enchanted.eg_text_customiser.common.fake_style.SpecialTextColour;
import games.enchanted.eg_text_customiser.common.pack.TextOverrideManager;
import games.enchanted.eg_text_customiser.common.util.ColourUtil;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.network.chat.Style;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BakedGlyph.GlyphInstance.class)
public abstract class GlyphInstanceMixin {
    @Mutable @Shadow @Final private int shadowColor;
    @Mutable @Shadow @Final private int color;

    @Shadow abstract boolean hasShadow();

    @Inject(
        at = @At("TAIL"),
        method = "<init>"
    )
    private void eg_text_customiser$replaceGlyphTextColours(float x, float y, int color, int shadowColor, BakedGlyph bakedGlyph, Style style, float boldOffset, float shadowOffset, CallbackInfo ci) {
        if(((StyleAdditions) style).eg_text_customiser$isSign()) return;

        int noAlphaColour = ColourUtil.removeAlpha(color);
        SpecialTextColour comparisonTextColour;
        if(style.getColor() != null) {
            comparisonTextColour = SpecialTextColour.fromTextColor(style.getColor());
        } else {
            comparisonTextColour = new SpecialTextColour(noAlphaColour);
        }
        int noAlphaShadowColour = ColourUtil.removeAlpha(shadowColor);
        FakeStyle fakeStyle = new FakeStyle(comparisonTextColour, noAlphaShadowColour, style.isBold(), style.isItalic(), style.isUnderlined(), style.isStrikethrough(), style.isObfuscated(), style.getFont());
        FakeStyle newStyle = TextOverrideManager.applyFakeColourOverride(fakeStyle);

        int colorAlpha = ColourUtil.extractAlpha(color);
        if(newStyle.colour() != null) {
            int newColour = newStyle.colour().safeGetAsRGB();
            this.color = ColourUtil.applyAlpha(newColour, colorAlpha);
        }

        if(!this.hasShadow() && !newStyle.properties().forceEnableShadow()) return;

        int shadowAlpha = newStyle.properties().forceEnableShadow() ? colorAlpha : ColourUtil.extractAlpha(shadowColor);
        if(newStyle.colour() != null && newStyle.shadowColour() != null && newStyle.shadowColour() == noAlphaShadowColour && newStyle.properties().autoGenerateShadow()) {
            // shadow colour has not changed but regular colour has, change shadow based on new colour
            this.shadowColor = ColourUtil.applyAlpha(ARGB.scaleRGB(newStyle.colour().safeGetAsRGB(), newStyle.properties().autoShadowMultiplier()), shadowAlpha);
        }
        else if(newStyle.shadowColour() != null ) {
            // shadow colour has changed, replace it
            this.shadowColor = ColourUtil.applyAlpha(newStyle.shadowColour(), shadowAlpha);
        }
    }
}
