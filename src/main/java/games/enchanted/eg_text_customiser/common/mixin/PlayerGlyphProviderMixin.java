package games.enchanted.eg_text_customiser.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import games.enchanted.eg_text_customiser.common.fake_style.DecorationType;
import games.enchanted.eg_text_customiser.common.pack.TextOverrideManager;
import net.minecraft.client.gui.font.PlayerGlyphProvider;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Supplier;

@Mixin(targets = "net/minecraft/client/gui/font/PlayerGlyphProvider$1$1")
public class PlayerGlyphProviderMixin {
    @WrapOperation(
        at = @At(value = "NEW", target = "Lnet/minecraft/client/gui/font/PlayerGlyphProvider$Instance;"),
        method = "createGlyph"
    )
    private PlayerGlyphProvider.Instance eg_text_customiser$replacePlayerGlyphColours(Supplier<PlayerSkinRenderCache.RenderInfo> skin, boolean hat, float x, float y, int color, int shadowColor, float shadowOffset, Operation<PlayerGlyphProvider.Instance> original, float ox, float oy, int ocolor, int oshadowColor, Style style) {
        final int[] newCols = {color, shadowColor};
        TextOverrideManager.replaceColour(
            color,
            shadowColor,
            style,
            false,
            DecorationType.NONE,
            colourARGB -> newCols[0] = colourARGB,
            shadowARGB -> newCols[1] = shadowARGB
        );
        return original.call(skin, hat, x, y, newCols[0], newCols[1], shadowOffset);
    }
}
