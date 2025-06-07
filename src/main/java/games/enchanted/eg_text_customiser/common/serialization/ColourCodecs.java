package games.enchanted.eg_text_customiser.common.serialization;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.EitherCodec;
import games.enchanted.eg_text_customiser.common.fake_style.SpecialTextColour;
import net.minecraft.ChatFormatting;

import java.util.Locale;
import java.util.Objects;

public class ColourCodecs {
    public static Codec<Integer> RGB_HEX_CODEC = Codec.STRING.comapFlatMap(
        input -> {
            if(!input.startsWith("#")) {
                return DataResult.error(() -> "Invalid colour! Must start with a `#` or be a named colour. Value '" + input + "' not valid");
            }
            try {
                int n = Integer.parseInt(input.substring(1), 16);
                if (n < 0 || n > 0xFFFFFF) {
                    return DataResult.error(() -> "Hexadecimal value exceeds maximum of `0xffffff` " + input);
                }
                return DataResult.success(n);
            }
            catch (NumberFormatException numberFormatException) {
                return DataResult.error(() -> "Invalid hexadecimal value " + input);
            }
        },
        input -> {
            if(input == -1) {
                return "#FFFFFF";
            }
            return String.format(Locale.ROOT, "#%06X", input);
        }
    );

    public static Codec<String> NAMED_COLOUR_CODEC = Codec.STRING.comapFlatMap(
        input -> {
            if(Objects.equals(input, SpecialTextColour.DEFAULT_COLOUR_NAME)) {
                return DataResult.success(input);
            }
            ChatFormatting formatting = ChatFormatting.getByName(input);
            if(formatting == null) {
                return DataResult.error(() -> "Invalid colour! Must start with a `#` or be a named colour. Value '" + input + "' not valid");
            }
            if(formatting.isColor()) {
                return DataResult.success(input);
            }
            return DataResult.error(() -> "Invalid named colour '" + input + "'");
        },
        input -> input
    );

    public static EitherCodec<Integer, String> HEX_OR_NAMED_CODEC = new EitherCodec<>(RGB_HEX_CODEC, NAMED_COLOUR_CODEC);
}
