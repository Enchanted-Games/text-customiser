package games.enchanted.eg_text_customiser.common.codepoint;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

public record Codepoints(char[] points) {
    public static Codec<Codepoints> CODEC = Codec.STRING.comapFlatMap(
        string -> {
            char[] points = string.toCharArray();
            return DataResult.success(new Codepoints(points));
        },
        codepoints -> String.valueOf(codepoints.points())
    );
}
