package games.enchanted.eg_text_customiser.common.mixin;

import games.enchanted.eg_text_customiser.common.duck.StyleAdditions;
import games.enchanted.eg_text_customiser.common.fake_style.FakeStyle;
import games.enchanted.eg_text_customiser.common.fake_style.SpecialTextColour;
import games.enchanted.eg_text_customiser.common.mixin.accessor.TextColorAccess;
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

import java.util.Objects;

@Mixin(BakedGlyph.GlyphInstance.class)
public abstract class GlyphInstanceMixin {
    @Mutable @Shadow @Final private int shadowColor;
    @Mutable @Shadow @Final private int color;

    @Shadow abstract boolean hasShadow();

    @Inject(
        at = @At("TAIL"),
        method = "<init>"
    )
    private void eg_text_customiser$replaceTextColours(float x, float y, int color, int shadowColor, BakedGlyph bakedGlyph, Style style, float boldOffset, float shadowOffset, CallbackInfo ci) {
//        if(!style.isBold()) {
//            return;
//        }
//        if(style.getColor() != null && !((StyleAdditions)style).eg_text_customiser$isSign()) {
//            if(Objects.equals(((TextColorAccess) (Object) style.getColor()).eg_text_customiser$getName(), "gold")) {
//            int alpha = ColourUtil.extractAlpha(color);
//            this.color = ColourUtil.applyAlpha(0xff00ff, alpha);
//            }
//        }
//        if(((StyleAdditions)style).eg_text_customiser$isSign()) {
//            return;
//        }
        int noAlphaColour = ColourUtil.removeAlpha(color);
        SpecialTextColour textColour;
        if(style.getColor() != null) {
            textColour = SpecialTextColour.fromTextColor(style.getColor());
        } else {
            textColour = new SpecialTextColour(noAlphaColour);
        }
        int noAlphaShadowColour = ColourUtil.removeAlpha(shadowColor);
        FakeStyle fakeStyle = new FakeStyle(textColour, noAlphaShadowColour, style.isBold(), style.isItalic(), style.isUnderlined(), style.isStrikethrough(), style.isObfuscated(), style.getFont());
        FakeStyle newStyle = TextOverrideManager.applyFakeColourOverride(fakeStyle);

        boolean colourChanged = false;
        if(newStyle.colour() != null) {
            int newColour = newStyle.colour().safeGetAsRGB();
            colourChanged = newStyle.colour().safeGetAsRGB() != noAlphaColour;
            int alpha = ColourUtil.extractAlpha(color);
            this.color = ColourUtil.applyAlpha(newColour, alpha);
        }

        if(!this.hasShadow()) return;

        int shadowAlpha = ColourUtil.extractAlpha(shadowColor);
        if (newStyle.colour() != null && newStyle.shadowColour() != null && newStyle.shadowColour() == noAlphaShadowColour) {
            // shadow colour has not changed but regular colour has, change shadow based on new colour
            this.shadowColor = ColourUtil.applyAlpha(ARGB.scaleRGB(newStyle.colour().safeGetAsRGB(), 0.25f), shadowAlpha);
//            this.shadowColor = 0xffff00ff;
        }
        else if(newStyle.shadowColour() != null && colourChanged) {
            // shadow colour has changed, replace it
            this.shadowColor = ColourUtil.applyAlpha(newStyle.shadowColour(), shadowAlpha);
//            this.shadowColor = 0xffffff00;
        }
//        if(newStyle.shadowColour() != null && this.hasShadow()) {
//            int alpha = ColourUtil.extractAlpha(shadowColor);
//            this.shadowColor = ColourUtil.applyAlpha(newStyle.shadowColour(), alpha);
//        }
    }
}
