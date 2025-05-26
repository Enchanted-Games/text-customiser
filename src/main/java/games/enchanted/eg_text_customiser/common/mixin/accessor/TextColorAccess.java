package games.enchanted.eg_text_customiser.common.mixin.accessor;

import net.minecraft.network.chat.TextColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TextColor.class)
public interface TextColorAccess {
    @Accessor("name")
    String eg_text_customiser$getName();
}
