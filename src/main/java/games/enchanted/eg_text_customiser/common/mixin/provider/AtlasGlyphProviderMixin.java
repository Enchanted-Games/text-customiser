package games.enchanted.eg_text_customiser.common.mixin.provider;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.textures.GpuTextureView;
import games.enchanted.eg_text_customiser.common.fake_style.DecorationType;
import games.enchanted.eg_text_customiser.common.pack.TextOverrideManager;
import net.minecraft.client.gui.font.AtlasGlyphProvider;
import net.minecraft.client.gui.font.GlyphRenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/client/gui/font/AtlasGlyphProvider$1")
public class AtlasGlyphProviderMixin {
    @WrapOperation(
        at = @At(value = "NEW", target = "Lnet/minecraft/client/gui/font/AtlasGlyphProvider$Instance;"),
        method = "createGlyph"
    )
    private AtlasGlyphProvider.Instance eg_text_customiser$replaceAtlasGlyphColours(GlyphRenderTypes renderTypes, GpuTextureView textureView, TextureAtlasSprite sprite, float x, float y, int color, int shadowColor, float shadowOffset, Operation<AtlasGlyphProvider.Instance> original, float ox, float oy, int ocolor, int oshadowColor, Style style) {
        final int[] newCols = {color, shadowColor};
        TextOverrideManager.replaceColour(
            color,
            shadowColor,
            style,
            false,
            DecorationType.NONE,
            null,
            colourARGB -> newCols[0] = colourARGB,
            shadowARGB -> newCols[1] = shadowARGB
        );
        return original.call(renderTypes, textureView, sprite, x, y, newCols[0], newCols[1], shadowOffset);
    }
}
