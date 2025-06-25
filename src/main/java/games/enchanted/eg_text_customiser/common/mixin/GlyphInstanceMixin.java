package games.enchanted.eg_text_customiser.common.mixin;

import games.enchanted.eg_text_customiser.common.fake_style.DecorationType;
import org.spongepowered.asm.mixin.Mixin;

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

// needs to be a string reference so <1.21.1 can compile
@Mixin(targets = "net.minecraft.client.gui.font.glyphs.BakedGlyph$GlyphInstance")
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
        TextOverrideManager.replaceColour(color, shadowColor, style, this.hasShadow(), DecorationType.NONE, colourRGBA -> this.color = colourRGBA, shadowRGBA -> this.shadowColor = shadowRGBA);
    }
    //?}
}