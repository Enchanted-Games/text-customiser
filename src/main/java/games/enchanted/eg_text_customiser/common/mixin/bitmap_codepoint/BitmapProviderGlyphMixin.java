package games.enchanted.eg_text_customiser.common.mixin.bitmap_codepoint;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.font.GlyphInfo;
import games.enchanted.eg_text_customiser.common.duck.BitmapGlyphAdditions;
import games.enchanted.eg_text_customiser.common.codepoint.CodepointGlyphInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/client/gui/font/providers/BitmapProvider$Glyph", priority = 900)
public class BitmapProviderGlyphMixin implements BitmapGlyphAdditions {
    @Unique private char eg_text_customiser$codepoint;

    @WrapOperation(
        at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/font/GlyphInfo;simple(F)Lcom/mojang/blaze3d/font/GlyphInfo;"),
        method = "info"
    )
    private GlyphInfo eg_text_customiser$setCodepointOnInfo(float advance, Operation<GlyphInfo> original) {
        return new CodepointGlyphInfo(advance, this.eg_text_customiser$getCodepoint());
    }

    @Override
    public void eg_text_customiser$setCodepoint(char codepoint) {
        this.eg_text_customiser$codepoint = codepoint;
    }

    @Override
    public char eg_text_customiser$getCodepoint() {
        return this.eg_text_customiser$codepoint;
    }
}
