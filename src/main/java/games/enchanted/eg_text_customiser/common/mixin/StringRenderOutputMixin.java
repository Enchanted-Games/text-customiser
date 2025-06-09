package games.enchanted.eg_text_customiser.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import games.enchanted.eg_text_customiser.common.fake_style.FakeStyle;
import games.enchanted.eg_text_customiser.common.fake_style.SpecialTextColour;
import games.enchanted.eg_text_customiser.common.pack.TextOverrideManager;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.ARGB;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/client/gui/Font$StringRenderOutput")
public class StringRenderOutputMixin {
    @Shadow @Final private int color;

    // this is so hacky but it works
    @Unique @Nullable private Integer eg_text_customiser$successfulMainTextMatch = null;

    @WrapOperation(
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;scaleRGB(IF)I"),
        method = "Lnet/minecraft/client/gui/Font$StringRenderOutput;getShadowColor(Lnet/minecraft/network/chat/Style;I)I"
    )
    private int eg_text_customiser$replaceTextShadowColour(int textColour, float scale, Operation<Integer> original) {
        if(eg_text_customiser$successfulMainTextMatch != null) {
            int modified = original.call(eg_text_customiser$successfulMainTextMatch + 0xff000000, 1f);
            eg_text_customiser$successfulMainTextMatch = null;
            return modified;
        }
        return original.call(textColour, scale);
    }

    @ModifyReturnValue(
        at = @At(
            value = "RETURN",
            ordinal = 1
        ),
        method = "Lnet/minecraft/client/gui/Font$StringRenderOutput;getTextColor(Lnet/minecraft/network/chat/TextColor;)I"
    )
    private int eg_text_customiser$replaceTextColour(int original, @Nullable @Local(ordinal = 0, argsOnly = true) TextColor textColor) {
        FakeStyle fakeStyle = new FakeStyle(new SpecialTextColour(ARGB.opaque(original == -1 ? color : original) - 0xff000000), null, null, null, null, null, null, null);
        FakeStyle overrideStyle = TextOverrideManager.applyFakeColourOverride(fakeStyle);
        if(overrideStyle.colour() == null) {
            eg_text_customiser$successfulMainTextMatch = null;
            return original;
        }
        eg_text_customiser$successfulMainTextMatch = overrideStyle.shadowColour();
        return overrideStyle.colour().safeGetAsRGB() + 0xff000000;
    }
}
