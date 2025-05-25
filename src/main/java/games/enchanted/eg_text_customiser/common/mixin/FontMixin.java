package games.enchanted.eg_text_customiser.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/client/gui/Font$StringRenderOutput")
public class FontMixin {
    @WrapOperation(
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;scaleRGB(IF)I"),
        method = "Lnet/minecraft/client/gui/Font$StringRenderOutput;getShadowColor(Lnet/minecraft/network/chat/Style;I)I"
    )
    private int eg_text_customiser$replaceShadowColour(int originalColour, float originalScale, Operation<Integer> original, Style textStyle, int rawTextColour) {
        if(textStyle.getColor() == null) {
            return original.call(originalColour, originalScale);
        }
        return original.call(originalColour, 0.7f);
    }
}
