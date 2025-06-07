package games.enchanted.eg_text_customiser.common.mixin;

import games.enchanted.eg_text_customiser.common.duck.StyleAddedFields;
import games.enchanted.eg_text_customiser.common.text_override.TextOverrideManager;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSink;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(targets = "net/minecraft/client/gui/Font$StringRenderOutput")
public abstract class Font_StringRenderOutputMixin implements FormattedCharSink {
    @Shadow private @Final int color;

    @ModifyVariable(
        at = @At(value = "HEAD", ordinal = 0),
        method = "accept",
        argsOnly = true
    )
    private Style eg_text_customiser$modifyStyleConditionally(Style style) {
        ((StyleAddedFields) style).eg_text_customiser$setComparisonColour(color);
        return TextOverrideManager.applyStyleOverride(style);
    }

    @ModifyVariable(
        at = @At(value = "HEAD", ordinal = 0),
        method = "accept",
        argsOnly = true
    )
    private Style eg_text_customiser$applyComparisonColourToStyle(Style style) {
        ((StyleAddedFields) style).eg_text_customiser$setComparisonColour(color);
        return style;
    }
}
