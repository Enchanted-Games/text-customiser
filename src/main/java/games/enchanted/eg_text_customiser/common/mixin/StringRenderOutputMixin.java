package games.enchanted.eg_text_customiser.common.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import games.enchanted.eg_text_customiser.common.util.ColourUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;

//? if minecraft: <= 1.21.4 {
/*import games.enchanted.eg_text_customiser.common.pack.TextOverrideManager;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
*///?}

@Mixin(targets = "net.minecraft.client.gui.Font$StringRenderOutput")
public class StringRenderOutputMixin {
    //? if minecraft: <= 1.21.4 {
    /*@Shadow @Final private boolean dropShadow;
    @Shadow @Final private float dimFactor;
    @Unique private int eg_text_customiser$originalColour;
    @Unique private int eg_text_customiser$originalShadowColour;

    @Inject(
        at = @At("TAIL"),
        method = "<init>"
    )
    private void eg_text_customiser$storeAdditionalColours(
        Font this$0,
        MultiBufferSource bufferSource,
        float x,
        float y,
        int color,
        boolean dropShadow,
        Matrix4f pose,
        Font.DisplayMode mode,
        int packedLightCoords,
        CallbackInfo ci
    ) {
        eg_text_customiser$originalColour = color;
        int[] colourParts = ColourUtil.ARGBint_to_ARGB(color);
        eg_text_customiser$originalShadowColour = ColourUtil.ARGB_to_ARGBint(colourParts[0], (int) (colourParts[1] * 0.25f), (int) (colourParts[2] * 0.25f), (int) (colourParts[3] * 0.25f));
    }

    @WrapOperation(
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;renderChar(Lnet/minecraft/client/gui/font/glyphs/BakedGlyph;ZZFFFLorg/joml/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFI)V"),
        method = "accept"
    )
    private void eg_text_customiser$replaceCharColour(
        Font instance,
        BakedGlyph glyph,
        boolean bold,
        boolean italic,
        float boldOffset,
        float x,
        float y,
        Matrix4f matrix,
        VertexConsumer buffer,
        float red,
        float green,
        float blue,
        float alpha,
        int packedLight,
        Operation<Void> original,
        @Local(ordinal = 0, argsOnly = true) Style style
    ) {
        // i hate this so much
        final int[][] newColour = {new int[4]};
        float dimFac = !dropShadow ? 0.25f : 1f;
        final int shadowColour = ColourUtil.ARGB_to_ARGBint((int) (alpha * 255), (int) ((red * dimFac) * 255), (int) ((green * dimFac) * 255), (int) ((blue * dimFac) * 255));
        float dimFac2 = !dropShadow ? 1f : 0.25f;
        final int mainColour = ColourUtil.ARGB_to_ARGBint((int) (alpha * 255), (int) ((red * dimFac2) * 255), (int) ((green * dimFac2) * 255), (int) ((blue * dimFac2) * 255));
        TextOverrideManager.replaceColour(
            mainColour,
            shadowColour,
            style,
            true,
            colourARGB -> {
                if(!dropShadow) {
                    newColour[0] = ColourUtil.ARGBint_to_ARGB(colourARGB);
                }
            },
            shadowARGB -> {
                if(dropShadow) {
                    newColour[0] = ColourUtil.ARGBint_to_ARGB(shadowARGB);
                }
            }
        );
        original.call(instance, glyph, bold, italic, boldOffset, x, y, matrix, buffer, newColour[0][1] / 255f, newColour[0][2] / 255f, newColour[0][3] / 255f, newColour[0][0] / 255f, packedLight);
    }
    *///?}
}