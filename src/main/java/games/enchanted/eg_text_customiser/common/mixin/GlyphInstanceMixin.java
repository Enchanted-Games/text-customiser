
package games.enchanted.eg_text_customiser.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

//? if minecraft: >= 1.21.5 {
import games.enchanted.eg_text_customiser.common.pack.TextOverrideManager;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//?}

@Pseudo
@Mixin(BakedGlyph.GlyphInstance.class)
public abstract class GlyphInstanceMixin {
    //? if minecraft: >= 1.21.5 {
    @Mutable @Shadow @Final private int shadowColor;
    @Mutable @Shadow @Final private int color;

    @Shadow abstract boolean hasShadow();

    @Inject(
        at = @At("TAIL"),
        method = "<init>"
    )
    private void eg_text_customiser$replaceGlyphTextColours(float x, float y, int color, int shadowColor, BakedGlyph bakedGlyph, Style style, float boldOffset, float shadowOffset, CallbackInfo ci) {
        TextOverrideManager.replaceColour(color, shadowColor, style, this.hasShadow(), colourRGBA -> this.color = colourRGBA, shadowRGBA -> this.shadowColor = shadowRGBA);
    }
    //?}
}