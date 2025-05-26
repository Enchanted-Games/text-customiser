package games.enchanted.eg_text_customiser.common.mixin;

import games.enchanted.eg_text_customiser.common.mixin.accessor.TextColorAccess;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(Style.class)
public class StyleMixin {
    @Mutable @Shadow @Final @Nullable TextColor color;

    @Inject(
        at = @At("TAIL"),
        method = "<init>"
    )
    private void eg_text_customiser$replaceTextStyleProperties(TextColor color, Integer shadowColor, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, ClickEvent clickEvent, HoverEvent hoverEvent, String insertion, ResourceLocation font, CallbackInfo ci) {
        if(color == null) {
            this.color = TextColor.fromRgb(0xffaaff);
            return;
        }
        @Nullable String namedColour = ((TextColorAccess) (Object) color).eg_text_customiser$getName();
        if(namedColour == null) return;
        if(Objects.equals(namedColour, "white")) {
            this.color = TextColor.fromRgb(0xbbbbff);
        }
    }
}
