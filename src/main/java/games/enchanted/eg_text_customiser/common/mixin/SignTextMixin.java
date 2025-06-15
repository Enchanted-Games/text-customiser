package games.enchanted.eg_text_customiser.common.mixin;

import games.enchanted.eg_text_customiser.common.duck.StyleAdditions;
import games.enchanted.eg_text_customiser.common.fake_style.SignTextData;
//? if minecraft: <= 1.21.4 {
/*import net.minecraft.client.renderer.blockentity.SignRenderer;
*///?} else {
import net.minecraft.client.renderer.blockentity.AbstractSignRenderer;
//?}
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.SignText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;

@Mixin(SignText.class)
public abstract class SignTextMixin {
    @Shadow public abstract DyeColor getColor();
    @Shadow public abstract boolean hasGlowingText();

    @ModifyVariable(
        at = @At("TAIL"),
        method = "<init>([Lnet/minecraft/network/chat/Component;[Lnet/minecraft/network/chat/Component;Lnet/minecraft/world/item/DyeColor;Z)V",
        ordinal = 0,
        argsOnly = true
    )
    private Component[] eg_text_customiser$addSignTextDataToComponent(Component[] originalComponents) {
        if(originalComponents.length == 0) return originalComponents;
        List<Component> modifiedComponents = new ArrayList<>();
        for (Component component : originalComponents) {
            if (!(component instanceof MutableComponent mutableComponent)) {
                modifiedComponents.add(component);
                continue;
            }

            SignTextData signTextData;

            int darkColour =
                //? if minecraft: <= 1.21.4 {
                /*SignRenderer.getDarkColor((SignText) (Object) this);
                *///?} else {
                AbstractSignRenderer.getDarkColor((SignText) (Object) this);
                //?}

            if(this.hasGlowingText()) {
                signTextData = new SignTextData(this.getColor(), this.hasGlowingText(), this.getColor().getTextColor(), darkColour);
            } else {
                signTextData = new SignTextData(this.getColor(), this.hasGlowingText(), darkColour, null);
            }

            Style style = mutableComponent.getStyle();
            style = ((StyleAdditions) style).eg_text_customiser$withSignTextData(signTextData);
            mutableComponent.setStyle(style);
            modifiedComponents.add(mutableComponent);
        }
        return modifiedComponents.toArray(new Component[0]);
    }
}
