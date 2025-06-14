package games.enchanted.eg_text_customiser.common.serialization;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.EitherCodec;
import games.enchanted.eg_text_customiser.common.util.ColourUtil;
import net.minecraft.ChatFormatting;

import java.util.List;
import java.util.Locale;

public class ColourCodecs {
    public static Codec<Integer> RGB_HEX_CODEC = Codec.STRING.comapFlatMap(
        input -> {
            if(!input.startsWith("#")) {
                return DataResult.error(() -> "Invalid colour! Must start with a `#`. Value '" + input + "' not valid");
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

    public static Codec<Integer> RGB_INT_LIST_CODEC = Codec.INT.listOf().comapFlatMap(
        (input) -> {
            if(input.size() != 3) {
                return DataResult.error(() -> "Invalid colour! Must be a list of 3 ints, got '" + input.size() + "' instead.");
            }
            return DataResult.success(ColourUtil.RGB_to_RGBint(input.get(0), input.get(1), input.get(2)));
        },
        (input) -> {
            int[] rgb = ColourUtil.RGBint_to_RGB(input);
            return List.of(rgb[0], rgb[1], rgb[2]);
        }
    );

    public static Codec<String> NAMED_COLOUR_CODEC = Codec.STRING.comapFlatMap(
        input -> {
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

    public static Codec<Integer> HEX_OR_RGB_LIST_CODEC = Codec.withAlternative(RGB_HEX_CODEC, RGB_INT_LIST_CODEC);
    public static EitherCodec<Integer, String> HEX_OR_NAMED_CODEC = new EitherCodec<>(HEX_OR_RGB_LIST_CODEC, NAMED_COLOUR_CODEC);
}
