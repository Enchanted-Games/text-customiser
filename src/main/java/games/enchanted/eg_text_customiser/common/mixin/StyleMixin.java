package games.enchanted.eg_text_customiser.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import games.enchanted.eg_text_customiser.common.duck.StyleAdditions;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(Style.class)
public abstract class StyleMixin implements StyleAdditions {
    @Unique private boolean eg_text_customiser$inheritShadowColour = true;

    @Shadow
    private static <T> Style checkEmptyAfterChange(Style style, T oldValue, T newValue) {
        return null;
    }

    @Shadow
    private static Style create(Optional<TextColor> par1, Optional<Integer> par2, Optional<Boolean> par3, Optional<Boolean> par4, Optional<Boolean> par5, Optional<Boolean> par6, Optional<Boolean> par7, Optional<ClickEvent> par8, Optional<HoverEvent> par9, Optional<String> par10, Optional<ResourceLocation> par11) {
        return null;
    }

    @Mutable @Shadow @Final Integer shadowColor;
    @Shadow @Final @Nullable TextColor color;
    @Shadow @Final @Nullable Boolean bold;
    @Shadow @Final @Nullable Boolean italic;
    @Shadow @Final @Nullable Boolean underlined;
    @Shadow @Final @Nullable Boolean strikethrough;
    @Shadow @Final @Nullable Boolean obfuscated;
    @Shadow @Final @Nullable ClickEvent clickEvent;
    @Shadow @Final @Nullable HoverEvent hoverEvent;
    @Shadow @Final @Nullable String insertion;
    @Shadow @Final @Nullable ResourceLocation font;

    @WrapOperation(
        at = @At(value = "NEW", target = "(Lnet/minecraft/network/chat/TextColor;Ljava/lang/Integer;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Lnet/minecraft/network/chat/ClickEvent;Lnet/minecraft/network/chat/HoverEvent;Ljava/lang/String;Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/network/chat/Style;"),
        method = "*"
    )
    private Style handler(@Nullable TextColor color, @Nullable Integer shadowColor, @Nullable Boolean bold, @Nullable Boolean italic, @Nullable Boolean underlined, @Nullable Boolean strikethrough, @Nullable Boolean obfuscated, @Nullable ClickEvent clickEvent, @Nullable HoverEvent hoverEvent, @Nullable String insertion, @Nullable ResourceLocation font, Operation<Style> original) {
        Style newStyle = original.call(color, shadowColor, bold, italic, underlined, strikethrough, obfuscated, clickEvent, hoverEvent, insertion, font);
        if(this.eg_text_customiser$inheritShadowColour) {
            ((StyleAdditions) newStyle).eg_text_customiser$setInheritShadowColour(true);
        }
        return newStyle;
    }

    @Override
    public Style eg_text_customiser$resetShadowColour() {
        Style resetStyle = checkEmptyAfterChange(create(Optional.ofNullable(this.color), Optional.empty(), Optional.ofNullable(this.bold), Optional.ofNullable(this.italic), Optional.ofNullable(this.underlined), Optional.ofNullable(this.strikethrough), Optional.ofNullable(this.obfuscated), Optional.ofNullable(this.clickEvent), Optional.ofNullable(this.hoverEvent), Optional.ofNullable(this.insertion), Optional.ofNullable(this.font)), this.shadowColor, null);
        if(resetStyle != null) {
            ((StyleAdditions) resetStyle).eg_text_customiser$setInheritShadowColour(true);
        }
        return resetStyle;
    }

    @Override
    public boolean eg_text_customiser$shouldInheritShadowColour() {
        return this.eg_text_customiser$inheritShadowColour;
    }

    @Override
    public void eg_text_customiser$setInheritShadowColour(boolean newInheritance) {
        this.eg_text_customiser$inheritShadowColour = newInheritance;
    }
}
