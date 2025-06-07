package games.enchanted.eg_text_customiser.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import games.enchanted.eg_text_customiser.common.pack.TextOverrideManager;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MutableComponent.class)
public abstract class MutableComponentMixin {
    @ModifyReturnValue(
        at = @At(value = "RETURN"),
        method = "getStyle"
    )
    private Style eg_text_customiser$modifyGetStyleReturn(Style original) {
        if(original.getColor() != null && original.getColor().getValue() == 0xffaaff) {
            return original;
        }
        return eg_text_customiser$overrideComponentStyle(original);
    }

    @ModifyVariable(
        at = @At(value = "HEAD", ordinal = 0),
        method = "Lnet/minecraft/network/chat/MutableComponent;setStyle(Lnet/minecraft/network/chat/Style;)Lnet/minecraft/network/chat/MutableComponent;",
        argsOnly = true
    )
    private Style eg_text_customiser$modifySetStyleArg(Style original) {
        return eg_text_customiser$overrideComponentStyle(original);
    }

    @Unique
    private Style eg_text_customiser$overrideComponentStyle(Style style) {
        return TextOverrideManager.applyColourOverride(style);
    }
}
