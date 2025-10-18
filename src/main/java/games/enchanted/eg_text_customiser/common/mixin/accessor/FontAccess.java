package games.enchanted.eg_text_customiser.common.mixin.accessor;

import net.minecraft.client.gui.Font;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Font.class)
public interface FontAccess {
    @Accessor("provider")
    Font.Provider eg_text_customiser$getProvider();
}
