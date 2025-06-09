package games.enchanted.eg_text_customiser.common.mixin;

import games.enchanted.eg_text_customiser.common.duck.StyleAdditions;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

import java.util.Optional;

@Mixin(Style.class)
public abstract class StyleMixin implements StyleAdditions {
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

    @Override
    public Style eg_text_customiser$resetShadowColour() {
        return checkEmptyAfterChange(create(Optional.ofNullable(this.color), Optional.empty(), Optional.ofNullable(this.bold), Optional.ofNullable(this.italic), Optional.ofNullable(this.underlined), Optional.ofNullable(this.strikethrough), Optional.ofNullable(this.obfuscated), Optional.ofNullable(this.clickEvent), Optional.ofNullable(this.hoverEvent), Optional.ofNullable(this.insertion), Optional.ofNullable(this.font)), this.shadowColor, null);
    }
}
