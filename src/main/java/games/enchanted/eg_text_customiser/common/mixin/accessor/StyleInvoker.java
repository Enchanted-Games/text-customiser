package games.enchanted.eg_text_customiser.common.mixin.accessor;

import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Style.class)
public interface StyleInvoker {
    @Invoker("<init>")
    static Style eg_text_customiser$invokeInit(
        @Nullable TextColor color,
        //? if minecraft: >= 1.21.4 {
        @Nullable Integer shadowColor,
         //?}
        @Nullable Boolean bold,
        @Nullable Boolean italic,
        @Nullable Boolean underlined,
        @Nullable Boolean strikethrough,
        @Nullable Boolean obfuscated,
        @Nullable ClickEvent clickEvent,
        @Nullable HoverEvent hoverEvent,
        @Nullable String insertion,
        @Nullable ResourceLocation font
    ) {
        throw new AssertionError("eg_text_customiser$invokeInit not asserted");
    }
}
