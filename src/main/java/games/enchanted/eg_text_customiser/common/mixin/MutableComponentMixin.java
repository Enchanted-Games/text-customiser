package games.enchanted.eg_text_customiser.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import games.enchanted.eg_text_customiser.common.duck.StyleAdditions;
import games.enchanted.eg_text_customiser.common.pack.TextOverrideManager;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Objects;

@Mixin(MutableComponent.class)
public abstract class MutableComponentMixin {
    @Unique
    private boolean eg_text_customiser$isSign = false;

    @Shadow
    private Style style;

    @ModifyReturnValue(
        at = @At(value = "RETURN"),
        method = "getStyle"
    )
    private Style eg_text_customiser$modifyGetStyleReturn(Style original) {
        // TODO: fix colour overrides not respecting colour inheritance properly
        // /tellraw @s ["plain text ",{text:"[",color:green},{text:"Diamonds"},{text:"]",color:green}]
        if(style.isEmpty() || eg_text_customiser$isSign) return original;
        if(!Thread.currentThread().getName().contains("Render thread")) return original;
        Style newStyle = TextOverrideManager.applyColourOverride(original);
        if(!Objects.equals(newStyle.getShadowColor(), original.getShadowColor())) {
            ((StyleAdditions) newStyle).eg_text_customiser$setInheritShadowColour(false);
        }
        return newStyle;
    }
}

