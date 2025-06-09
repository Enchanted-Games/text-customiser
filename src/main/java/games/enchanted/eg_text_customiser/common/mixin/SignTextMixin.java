package games.enchanted.eg_text_customiser.common.mixin;

import games.enchanted.eg_text_customiser.common.duck.StyleAdditions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.level.block.entity.SignText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;

@Mixin(SignText.class)
public abstract class SignTextMixin {
    @ModifyVariable(
        at = @At("HEAD"),
        method = "<init>([Lnet/minecraft/network/chat/Component;[Lnet/minecraft/network/chat/Component;Lnet/minecraft/world/item/DyeColor;Z)V",
        ordinal = 0,
        argsOnly = true
    )
    private static Component[] eg_text_customiser$addSignTextDataToComponent(Component[] originalComponents) {
        if(originalComponents.length == 0) return originalComponents;
        List<Component> modifiedComponents = new ArrayList<>();
        for (Component component : originalComponents) {
            if (!(component instanceof MutableComponent mutableComponent)) {
                modifiedComponents.add(component);
                continue;
            }
            Style style = mutableComponent.getStyle();
            style = ((StyleAdditions) style).eg_text_customiser$setIsSign();
            mutableComponent.setStyle(style);
            modifiedComponents.add(mutableComponent);
        }
        return modifiedComponents.toArray(new Component[0]);
    }
}
