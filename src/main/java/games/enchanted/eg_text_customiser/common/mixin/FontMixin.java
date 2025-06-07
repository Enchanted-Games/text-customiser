package games.enchanted.eg_text_customiser.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import games.enchanted.eg_text_customiser.common.fake_style.FakeStyle;
import games.enchanted.eg_text_customiser.common.fake_style.SpecialTextColour;
import games.enchanted.eg_text_customiser.common.pack.TextOverrideManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/client/gui/Font$StringRenderOutput")
public class FontMixin {
    @WrapOperation(
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;scaleRGB(IF)I"),
        method = "Lnet/minecraft/client/gui/Font$StringRenderOutput;getShadowColor(Lnet/minecraft/network/chat/Style;I)I"
    )
    private int eg_text_customiser$replaceTextShadowColour(int textColour, float scale, Operation<Integer> original) {
        FakeStyle fakeStyle = new FakeStyle(new SpecialTextColour(textColour - 0xff000000), null, null, null, null, null, null, null);
        FakeStyle overrideStyle = TextOverrideManager.applyFakeColourOverride(fakeStyle);
        if(overrideStyle.colour() == null) {
            return original.call(textColour, scale);
        }
        return original.call(overrideStyle.colour().safeGetAsRGB() + 0xff000000, scale);
    }

    @ModifyReturnValue(
        at = @At(
            value = "RETURN",
            ordinal = 1
        ),
        method = "Lnet/minecraft/client/gui/Font$StringRenderOutput;getTextColor(Lnet/minecraft/network/chat/TextColor;)I"
    )
    private int eg_text_customiser$replaceTextColour(int original) {
        FakeStyle fakeStyle = new FakeStyle(new SpecialTextColour(original - 0xff000000), null, null, null, null, null, null, null);
        FakeStyle overrideStyle = TextOverrideManager.applyFakeColourOverride(fakeStyle);
        if(overrideStyle.colour() == null) {
            return original;
        }
        return overrideStyle.colour().safeGetAsRGB() + 0xff000000;
    }
}
