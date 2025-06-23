package games.enchanted.eg_text_customiser.common.fake_style;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum DecorationType implements StringRepresentable {
    NONE("none"),
    UNDERLINE("underline"),
    STRIKETHROUGH("strikethrough");

    private final String name;

    DecorationType(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}
