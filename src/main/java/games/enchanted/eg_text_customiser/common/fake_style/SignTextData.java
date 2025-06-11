package games.enchanted.eg_text_customiser.common.fake_style;

import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.Nullable;

public record SignTextData(DyeColor dyeColor, boolean isSignGlowing, int darkColour, @Nullable Integer outlineColour) {
}
