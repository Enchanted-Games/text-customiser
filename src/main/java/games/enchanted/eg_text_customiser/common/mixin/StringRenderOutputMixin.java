package games.enchanted.eg_text_customiser.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import games.enchanted.eg_text_customiser.common.duck.EffectAdditions;
import games.enchanted.eg_text_customiser.common.fake_style.DecorationType;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Slice;

//? if minecraft: <= 1.21.4 {

/*import net.minecraft.client.gui.Font;
import com.mojang.blaze3d.vertex.VertexConsumer;
import games.enchanted.eg_text_customiser.common.pack.TextOverrideManager;
import games.enchanted.eg_text_customiser.common.util.ColourUtil;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
*///?}

//? if minecraft: <= 1.21.4 {
/*@Mixin(targets = "net.minecraft.client.gui.Font$StringRenderOutput")
*///?} else {
@Mixin(targets = "net.minecraft.client.gui.Font$PreparedTextBuilder")
//?}
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
        final int[][] newColour = {new int[4]};
        final int mainColour = ColourUtil.calcMainColour(dropShadow, alpha, red, green, blue);
        final int shadowColour = ColourUtil.calcShadowColour(dropShadow, alpha, red, green, blue);
        TextOverrideManager.replaceColour(
            mainColour,
            shadowColour,
            style,
            true,
            DecorationType.NONE,
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

    @WrapOperation(
        at = @At(value = "INVOKE", target =
            //? if minecraft: <= 1.21.4 {
            /*"Lnet/minecraft/client/gui/Font$StringRenderOutput;addEffect(Lnet/minecraft/client/gui/font/glyphs/BakedGlyph$Effect;)V"
            *///?} else {
            "Lnet/minecraft/client/gui/Font$PreparedTextBuilder;addEffect(Lnet/minecraft/client/gui/font/glyphs/BakedGlyph$Effect;)V"
            //?}
            ,
            ordinal = 0
        ),
        slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Style;isStrikethrough()Z")
        ),
        method = "accept"
    )
    private void eg_text_customiser$applyOverrideToStrikethroughEffect(@Coerce Object instance, BakedGlyph.Effect effect, Operation<Void> original, @Local(argsOnly = true) Style style) {
        boolean isShadow =
        //? if minecraft: <= 1.21.4 {
        /*this.dropShadow;
        *///?} else {
        false;
        //?}

        BakedGlyph.Effect newEffect = ((EffectAdditions) (Object) effect).eg_text_customiser$applyEffectOverride(style, DecorationType.STRIKETHROUGH, isShadow);
        original.call(instance, newEffect);
    }

    @WrapOperation(
        at = @At(value = "INVOKE", target =
            //? if minecraft: <= 1.21.4 {
            /*"Lnet/minecraft/client/gui/Font$StringRenderOutput;addEffect(Lnet/minecraft/client/gui/font/glyphs/BakedGlyph$Effect;)V"
             *///?} else {
            "Lnet/minecraft/client/gui/Font$PreparedTextBuilder;addEffect(Lnet/minecraft/client/gui/font/glyphs/BakedGlyph$Effect;)V"
            //?}
            ,
            ordinal = 0
        ),
        slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Style;isUnderlined()Z")
        ),
        method = "accept"
    )
    private void eg_text_customiser$applyOverrideToUnderlineEffect(@Coerce Object instance, BakedGlyph.Effect effect, Operation<Void> original, @Local(argsOnly = true) Style style) {
        boolean isShadow =
        //? if minecraft: <= 1.21.4 {
        /*this.dropShadow;
        *///?} else {
        false;
        //?}

        BakedGlyph.Effect newEffect = ((EffectAdditions) (Object) effect).eg_text_customiser$applyEffectOverride(style, DecorationType.UNDERLINE, isShadow);
        original.call(instance, newEffect);
    }
}