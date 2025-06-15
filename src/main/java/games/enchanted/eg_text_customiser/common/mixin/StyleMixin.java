package games.enchanted.eg_text_customiser.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import games.enchanted.eg_text_customiser.common.duck.StyleAdditions;
import games.enchanted.eg_text_customiser.common.fake_style.SignTextData;
import games.enchanted.eg_text_customiser.common.mixin.accessor.StyleInvoker;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Objects;
import java.util.Optional;

@Mixin(Style.class)
public abstract class StyleMixin implements StyleAdditions {
    @Unique @Nullable private SignTextData eg_text_customiser$signTextData = null;

    //? if minecraft: >= 1.21.4 {
    @Mutable @Shadow @Final Integer shadowColor;
    //?}
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
        at = @At(value = "NEW", target =
            //? if minecraft: >= 1.21.4 {
            "(Lnet/minecraft/network/chat/TextColor;Ljava/lang/Integer;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Lnet/minecraft/network/chat/ClickEvent;Lnet/minecraft/network/chat/HoverEvent;Ljava/lang/String;Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/network/chat/Style;"
            //?} else {
            /*"(Lnet/minecraft/network/chat/TextColor;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Lnet/minecraft/network/chat/ClickEvent;Lnet/minecraft/network/chat/HoverEvent;Ljava/lang/String;Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/network/chat/Style;"
            *///?}
        ),
        method = "*"
    )
    private Style eg_text_customiser$initialiseFieldsOnNewInstance(
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
        @Nullable ResourceLocation font,
        Operation<Style> original
    ) {
        Style newStyle = original.call(
            color,
            //? if minecraft: >= 1.21.4 {
            shadowColor,
            //?}
            bold,
            italic,
            underlined,
            strikethrough,
            obfuscated,
            clickEvent,
            hoverEvent,
            insertion,
            font
        );
        ((StyleAdditions) newStyle).eg_text_customiser$setSignTextData(this.eg_text_customiser$getSignTextData());
        return newStyle;
    }

    @WrapOperation(
        at = @At(value = "INVOKE", target = "Ljava/util/Objects;equals(Ljava/lang/Object;Ljava/lang/Object;)Z"),
        method = "equals",
        remap = false
    )
    private boolean eg_text_customiser$addEqualityForSignTextField(Object a, Object b, Operation<Boolean> original) {
        if((a instanceof Style styleA) && (b instanceof Style styleB)) {
            return original.call(a, b) && Objects.equals(((StyleAdditions) styleA).eg_text_customiser$getSignTextData(), ((StyleAdditions) styleB).eg_text_customiser$getSignTextData());
        }
        return original.call(a, b);
    }

    @Override
    public Style eg_text_customiser$withSignTextData(SignTextData signTextData) {
        Style newStyle = StyleInvoker.eg_text_customiser$invokeInit(
            this.color,
            //? if minecraft: >= 1.21.4 {
            this.shadowColor,
             //?}
            this.bold,
            this.italic,
            this.underlined,
            this.strikethrough,
            this.obfuscated,
            this.clickEvent,
            this.hoverEvent,
            this.insertion,
            this.font
        );
        ((StyleAdditions) newStyle).eg_text_customiser$setSignTextData(signTextData);
        return newStyle;
    }

    @Override
    public void eg_text_customiser$setSignTextData(SignTextData signTextData) {
        this.eg_text_customiser$signTextData = signTextData;
    }

    @Override
    public SignTextData eg_text_customiser$getSignTextData() {
        return this.eg_text_customiser$signTextData;
    }
}
