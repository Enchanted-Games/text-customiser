package games.enchanted.eg_text_customiser.common.mixin;

import games.enchanted.eg_text_customiser.common.text_override.TextOverrideManager;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(targets = "net/minecraft/client/gui/Font$StringRenderOutput")
public class FontMixin {
    @ModifyVariable(
        at = @At(value = "HEAD", ordinal = 0),
        method = "accept",
        argsOnly = true
    )
    private Style eg_text_customiser$modifyStyleConditionally(Style style) {
        boolean hasOverride = TextOverrideManager.styleHasOverride(style);
        if (hasOverride) {
            return style.withColor(TextColor.fromRgb(0xffaa0f));
        }
        return style;
    }
}
