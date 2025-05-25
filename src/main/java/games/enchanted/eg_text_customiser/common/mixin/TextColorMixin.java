package games.enchanted.eg_text_customiser.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.network.chat.TextColor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TextColor.class)
public class TextColorMixin {
    @Shadow @Final @Nullable
    private String name;

    @ModifyReturnValue(
        at = @At("RETURN"),
        method = "getValue"
    )
    private int eg_text_customiser$replaceColour(int original) {
        if(name == null) return original;
        switch (name) {
            case "aqua": return 0x11ff00;
        }
        return original;
    }
}
