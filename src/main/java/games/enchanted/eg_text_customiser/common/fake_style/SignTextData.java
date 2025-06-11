package games.enchanted.eg_text_customiser.common.fake_style;

import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.Nullable;

public record SignTextData(DyeColor dyeColor, boolean isGlowingSignText, int darkColour, @Nullable Integer outlineColour) {
}
