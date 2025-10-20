package games.enchanted.eg_text_customiser.common.codepoint;

import com.mojang.blaze3d.font.GlyphInfo;

public class CodepointGlyphInfo implements GlyphInfo {
    final float advance;
    final char codepoint;

    public CodepointGlyphInfo(float advance, char codepoint) {
        this.advance = advance;
        this.codepoint = codepoint;
    }

    @Override
    public float getAdvance() {
        return advance;
    }

    public char getCodepoint() {
        return codepoint;
    }
}
