package games.enchanted.eg_text_customiser.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import games.enchanted.eg_text_customiser.common.duck.EffectAdditions;
import games.enchanted.eg_text_customiser.common.fake_style.DecorationType;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.client.gui.font.glyphs.BakedSheetGlyph;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(targets = "net.minecraft.client.gui.Font$PreparedTextBuilder")
public class StringRenderOutputMixin {
    @WrapOperation(
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/Font$PreparedTextBuilder;addEffect(Lnet/minecraft/client/gui/font/TextRenderable;)V",
            ordinal = 0
        ),
        slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Style;isStrikethrough()Z")
        ),
        method = "accept(ILnet/minecraft/network/chat/Style;Lnet/minecraft/client/gui/font/glyphs/BakedGlyph;)Z"
    )
    private void eg_text_customiser$applyOverrideToStrikethroughEffect(@Coerce Object instance, TextRenderable effect, Operation<Void> original, @Local(argsOnly = true) Style style) {
        TextRenderable newEffect = ((EffectAdditions) effect).eg_text_customiser$applyEffectOverride(style, DecorationType.STRIKETHROUGH, false);
        original.call(instance, newEffect);
    }

    @WrapOperation(
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/Font$PreparedTextBuilder;addEffect(Lnet/minecraft/client/gui/font/TextRenderable;)V",
            ordinal = 0
        ),
        slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Style;isUnderlined()Z")
        ),
        method = "accept(ILnet/minecraft/network/chat/Style;Lnet/minecraft/client/gui/font/glyphs/BakedGlyph;)Z"
    )
    private void eg_text_customiser$applyOverrideToUnderlineEffect(@Coerce Object instance, TextRenderable effect, Operation<Void> original, @Local(argsOnly = true) Style style) {
        TextRenderable newEffect = ((EffectAdditions) effect).eg_text_customiser$applyEffectOverride(style, DecorationType.UNDERLINE, false);
        original.call(instance, newEffect);
    }
}