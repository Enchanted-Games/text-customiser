package games.enchanted.eg_text_customiser.common.text_override.fake_style;

import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.SignText;
import org.jetbrains.annotations.Nullable;

public record FakeStyle(
    @Nullable SpecialTextColour colour,
    @Nullable Integer shadowColour,
    @Nullable Boolean bold,
    @Nullable Boolean italic,
    @Nullable Boolean underlined,
    @Nullable Boolean strikethrough,
    @Nullable Boolean obfuscated,
    @Nullable ResourceLocation font
) {
    public static FakeStyle fromStyle(Style style) {
        return new FakeStyle(
            SpecialTextColour.fromTextColor(style.getColor()),
            style.getShadowColor(),
            style.isBold(),
            style.isItalic(),
            style.isUnderlined(),
            style.isStrikethrough(),
            style.isObfuscated(),
            style.getFont()
        );
    }

    public static FakeStyle fromSignText(SignText signText, boolean isGlowingOutline) {
        return new FakeStyle(SpecialTextColour.fromSignText(signText, isGlowingOutline), null, null, null, null, null, null, null);
    }
}
