package games.enchanted.eg_text_customiser.common.mixin;

import games.enchanted.eg_text_customiser.common.duck.StyleAdditions;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BakedGlyph.GlyphInstance.class)
public class GlyphInstanceMixin {
    @Mutable
    @Shadow @Final private int shadowColor;

    @Inject(
        at = @At("TAIL"),
        method = "<init>"
    )
    private void a(float f, float g, int i, int j, BakedGlyph bakedGlyph, Style style, float h, float k, CallbackInfo ci) {
        if(((StyleAdditions)style).eg_text_customiser$isSign()) {
            this.shadowColor = 0xff00bbff;
        }
    }
}
