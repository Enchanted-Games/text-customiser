package games.enchanted.eg_text_customiser.common.codepoint;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

public record Codepoints(int[] points) {
    public static Codec<Codepoints> CODEC = Codec.STRING.comapFlatMap(
        string -> {
            int[] points = string.codePoints().toArray();
            return DataResult.success(new Codepoints(points));
        },
        codepoints -> {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < codepoints.points().length; i++) {
                s.append((char) codepoints.points()[i]);
            }
            return s.toString();
        }
    );
}
