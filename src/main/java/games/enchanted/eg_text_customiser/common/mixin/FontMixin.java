package games.enchanted.eg_text_customiser.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;

@Mixin(targets = "net/minecraft/client/gui/Font$StringRenderOutput")
public class FontMixin {
    @WrapOperation(
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font$StringRenderOutput;getShadowColor(Lnet/minecraft/network/chat/Style;I)I"),
        method = "Lnet/minecraft/client/gui/Font$StringRenderOutput;accept(ILnet/minecraft/network/chat/Style;I)Z"
    )
    private int eg_text_customiser$replaceShadowColour(@Coerce Object instance, Style textStyle, int rawTextColour, Operation<Integer> original, @Local(ordinal = 0) TextColor textColour) {

        return 0;
    }
}
