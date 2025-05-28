package games.enchanted.eg_text_customiser.common.serialization;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import java.util.Locale;

public class ColourCodecs {
    public static Codec<Integer> HEX_RGB_STRING_CODEC = Codec.STRING.comapFlatMap(
        input -> {
            // TODO: better error messages
            if(!input.startsWith("#")) {
                return DataResult.error(() -> "invalid ");
            }
            try {
                int n = Integer.parseInt(input.substring(1), 16);
                if (n < 0 || n > 0xFFFFFF) {
                    return DataResult.error(() -> "out of range " + input);
                }
                return DataResult.success(n);
            }
            catch (NumberFormatException numberFormatException) {
                return DataResult.error(() -> "invalid " + input);
            }
        },
        input -> String.format(Locale.ROOT, "#%06X", input)
    );

    public static Codec<Integer> COLOUR_CODEC = Codec.withAlternative(
        Codec.INT,
        HEX_RGB_STRING_CODEC
    );
}
