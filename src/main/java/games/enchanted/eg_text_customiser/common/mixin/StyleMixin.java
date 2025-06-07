package games.enchanted.eg_text_customiser.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import games.enchanted.eg_text_customiser.common.duck.StyleAddedFields;
import games.enchanted.eg_text_customiser.common.text_override.TextOverrideManager;
import games.enchanted.eg_text_customiser.common.text_override.fake_style.FakeStyle;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Debug(export = true)
@Mixin(Style.class)
public abstract class StyleMixin implements StyleAddedFields {
    @Shadow public abstract boolean isEmpty();

    @Unique private FakeStyle overrideStyle = new FakeStyle(null, null, null, null, null, null, null, null);
    @Unique @Nullable private Integer eg_text_customier$comparisonColour = null;

    @Inject(
        at = @At("TAIL"),
        method = "<init>"
    )
    private void eg_text_customiser$calculateFakeStyle(TextColor color, Integer shadowColor, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, ClickEvent clickEvent, HoverEvent hoverEvent, String insertion, ResourceLocation font, CallbackInfo ci) {
        if(isEmpty()) return;
        overrideStyle = TextOverrideManager.getOverrideForStyle((Style) (Object) this);
    }

    @ModifyReturnValue(
        at = @At("RETURN"),
        method = "getColor"
    )
    public TextColor eg_text_customiser$overrideBold(TextColor original) {
        return overrideStyle.getAsTextColor(original);
    }

    @ModifyReturnValue(
        at = @At("RETURN"),
        method = "isBold"
    )
    public boolean eg_text_customiser$overrideBold(boolean original) {
        return overrideStyle.bold(original);
    }

    @ModifyReturnValue(
        at = @At("RETURN"),
        method = "getFont"
    )
    public ResourceLocation eg_text_customiser$overrideFont(ResourceLocation original) {
        return overrideStyle.font(original);
    }

    @Override
    public void eg_text_customiser$setComparisonColour(Integer colour) {
        eg_text_customier$comparisonColour = colour;
    }

    @Override
    public @Nullable Integer eg_text_customiser$getComparisonColour() {
        return eg_text_customier$comparisonColour;
    }
}
