package games.enchanted.eg_text_customiser.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import games.enchanted.eg_text_customiser.common.pack.TextOverrideManager;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MutableComponent.class)
public abstract class MutableComponentMixin {
    @ModifyReturnValue(
        at = @At(value = "RETURN"),
        method = "getStyle"
    )
    private Style eg_text_customiser$modifyGetStyleReturn(Style original) {
        if(!Thread.currentThread().getName().contains("Render thread")) return original;

        Style overriddenStyle = TextOverrideManager.applyColourOverride(original);
        if(overriddenStyle == original) {
            return original;
        }

        return overriddenStyle;
    }
}
