package games.enchanted.eg_text_customiser.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import games.enchanted.eg_text_customiser.common.text_override.TextOverrideManager;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Font.class)
public class FontMixin {
    // this one is to make boldness and different width fonts work properly in most places
    @ModifyVariable(
        at = @At(value = "HEAD", ordinal = 0),
        method = "method_27516",
        argsOnly = true
    )
    private Style eg_text_customiser$modifyStyleConditionally(Style style) {
        return TextOverrideManager.applyStyleOverride(style);
    }
}
