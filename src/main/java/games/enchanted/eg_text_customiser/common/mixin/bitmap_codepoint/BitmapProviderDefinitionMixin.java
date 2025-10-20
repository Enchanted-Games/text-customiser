package games.enchanted.eg_text_customiser.common.mixin.bitmap_codepoint;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.font.UnbakedGlyph;
import games.enchanted.eg_text_customiser.common.duck.BitmapGlyphAdditions;
import net.minecraft.client.gui.font.CodepointMap;
import net.minecraft.client.gui.font.providers.BitmapProvider;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export = true)
@Mixin(BitmapProvider.Definition.class)
public class BitmapProviderDefinitionMixin {
    // set the points on Glyph when loading so we can set it on GlyphInfo later when baking the Glyph
    @WrapOperation(
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/font/CodepointMap;put(ILjava/lang/Object;)Ljava/lang/Object;"),
        method = "load"
    )
    private <T extends UnbakedGlyph> Object eg_text_customiser$setCodepointOnGlyph(CodepointMap<T> instance, int index, Object value, Operation<T> original) {
        if(value != null) {
            ((BitmapGlyphAdditions) value).eg_text_customiser$setCodepoint(index);
        }
        return original.call(instance, index, value);
    }
}
