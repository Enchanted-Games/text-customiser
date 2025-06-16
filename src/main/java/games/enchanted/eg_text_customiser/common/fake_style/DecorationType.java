package games.enchanted.eg_text_customiser.common.fake_style;

import net.minecraft.network.chat.Style;

public enum DecorationType {
    NONE(),
    UNDERLINE(),
    STRIKETHROUGH();

    public static DecorationType fromStyle(Style style) {
        if(style.isUnderlined()) {
            return UNDERLINE;
        } else if(style.isStrikethrough()) {
            return STRIKETHROUGH;
        }
        return NONE;
    }
}
