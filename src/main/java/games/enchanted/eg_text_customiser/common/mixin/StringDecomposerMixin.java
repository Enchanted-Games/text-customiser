package games.enchanted.eg_text_customiser.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import games.enchanted.eg_text_customiser.common.duck.StyleShadowReset;
import games.enchanted.eg_text_customiser.common.pack.TextOverrideManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.util.StringDecomposer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// For legacy colour code support
@Mixin(value = StringDecomposer.class, priority = 5000)
public class StringDecomposerMixin {
    @WrapOperation(
        at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Style;applyLegacyFormat(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/Style;"),
        method = "iterateFormatted(Ljava/lang/String;ILnet/minecraft/network/chat/Style;Lnet/minecraft/network/chat/Style;Lnet/minecraft/util/FormattedCharSink;)Z"
    )
    private static Style eg_text_customiser$replaceStyleForLegacyFormatting(Style instance, ChatFormatting legacyChatFormat, Operation<Style> original, String text, int skip, Style currentStyle, Style defaultStyle) {
        Style styleWithLegacyColourApplied = original.call(((StyleShadowReset) instance).eg_text_customiser$resetShadowColour(), legacyChatFormat);
        return TextOverrideManager.applyColourOverride(styleWithLegacyColourApplied);
    }
}
