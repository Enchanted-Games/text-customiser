package games.enchanted.eg_text_customiser.common.mixin.glyph;

import games.enchanted.eg_text_customiser.common.codepoint.CodepointGlyphInfo;
import games.enchanted.eg_text_customiser.common.fake_style.DecorationType;
import games.enchanted.eg_text_customiser.common.pack.TextOverrideManager;
import net.minecraft.client.gui.font.glyphs.BakedSheetGlyph;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.gui.font.glyphs.BakedSheetGlyph$GlyphInstance")
public abstract class GlyphInstanceMixin {
    @Mutable @Shadow @Final private int shadowColor;
    @Mutable @Shadow @Final private int color;

    @Shadow abstract boolean hasShadow();

    @Inject(
        at = @At("TAIL"),
        method = "<init>"
    )
    private void eg_text_customiser$replaceGlyphTextColours(
        float x,
        float y,
        int color,
        int shadowColor,
        BakedSheetGlyph bakedGlyph,
        Style style,
        float boldOffset,
        float shadowOffset,
        CallbackInfo ci
    ) {
        Integer codepoint = null;
        if(bakedGlyph.info() instanceof CodepointGlyphInfo codepointGlyphInfo) {
            codepoint = codepointGlyphInfo.getCodepoint();
        }
        TextOverrideManager.replaceColour(color, shadowColor, style, this.hasShadow(), DecorationType.NONE, codepoint, colourRGBA -> this.color = colourRGBA, shadowRGBA -> this.shadowColor = shadowRGBA);
    }
}