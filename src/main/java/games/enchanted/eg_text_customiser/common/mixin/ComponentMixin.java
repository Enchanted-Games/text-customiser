package games.enchanted.eg_text_customiser.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import games.enchanted.eg_text_customiser.common.duck.StyleAdditions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Component.class)
public interface ComponentMixin {
    // prevent text shadows from inheriting if it is from a colour override
    @WrapOperation(
        at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Style;applyTo(Lnet/minecraft/network/chat/Style;)Lnet/minecraft/network/chat/Style;"),
        method = "visit(Lnet/minecraft/network/chat/FormattedText$StyledContentConsumer;Lnet/minecraft/network/chat/Style;)Ljava/util/Optional;"
    )
    private Style eg_text_customiser$preventShadowColourInheritance(Style thisStyle, Style otherStyle, Operation<Style> original) {
        if(((StyleAdditions) otherStyle).eg_text_customiser$shouldInheritShadowColour() || otherStyle.isEmpty()) {
            return original.call(thisStyle, otherStyle);
        }
        if(thisStyle.isEmpty() && !((StyleAdditions) otherStyle).eg_text_customiser$shouldInheritShadowColour()) {
            return original.call(thisStyle, otherStyle);
        }

        return original.call(thisStyle, ((StyleAdditions) otherStyle).eg_text_customiser$resetShadowColour());
    }
}
