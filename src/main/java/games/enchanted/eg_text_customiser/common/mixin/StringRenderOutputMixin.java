package games.enchanted.eg_text_customiser.common.mixin;

import org.spongepowered.asm.mixin.Mixin;

//? if minecraft: <= 1.21.4 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.VertexConsumer;
import games.enchanted.eg_text_customiser.common.pack.TextOverrideManager;
import games.enchanted.eg_text_customiser.common.util.ColourUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.network.chat.Style;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
*///?}

@Mixin(targets = "net.minecraft.client.gui.Font$StringRenderOutput")
public class StringRenderOutputMixin {
    //? if minecraft: <= 1.21.4 {
    /*@Shadow @Final private boolean dropShadow;

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
        float dimFac2 = !dropShadow ? 1f : 4f;
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