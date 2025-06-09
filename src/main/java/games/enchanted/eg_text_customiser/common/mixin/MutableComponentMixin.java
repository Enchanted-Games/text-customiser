package games.enchanted.eg_text_customiser.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import games.enchanted.eg_text_customiser.common.duck.StyleAdditions;
import games.enchanted.eg_text_customiser.common.pack.TextOverrideManager;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Objects;

@Mixin(MutableComponent.class)
public abstract class MutableComponentMixin {
    @Shadow
    private Style style;

    @ModifyReturnValue(
        at = @At(value = "RETURN"),
        method = "getStyle"
    )
    private Style eg_text_customiser$modifyGetStyleReturn(Style original) {
        // TODO: fix shadow colour inheriting to other component siblings
        if(style.isEmpty()) return original;
        if(!Thread.currentThread().getName().contains("Render thread")) return original;
        Style newStyle = TextOverrideManager.applyColourOverride(original);
        if(!Objects.equals(newStyle.getShadowColor(), original.getShadowColor())) {
            ((StyleAdditions) newStyle).eg_text_customiser$setInheritShadowColour(false);
        }
        return newStyle;
    }
}

