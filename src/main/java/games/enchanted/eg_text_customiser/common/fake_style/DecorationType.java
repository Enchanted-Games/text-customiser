package games.enchanted.eg_text_customiser.common.fake_style;

import net.minecraft.network.chat.Style;
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

    public static DecorationType fromStyle(Style style) {
        if(style.isUnderlined()) {
            return UNDERLINE;
        } else if(style.isStrikethrough()) {
            return STRIKETHROUGH;
        }
        return NONE;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}
