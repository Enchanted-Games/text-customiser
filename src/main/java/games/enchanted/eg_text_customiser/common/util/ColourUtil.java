package games.enchanted.eg_text_customiser.common.util;

import org.xml.sax.helpers.ParserAdapter;

import java.util.Locale;

public class ColourUtil {
    public static int clampInt(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    /**
     * Converts argb to an int in argb decimal format
     */
    public static int ARGB_to_ARGBint(int a, int r, int g, int b) {
        int alpha = clampInt(a, 0, 255);
        int red = clampInt(r, 0, 255);
        int green = clampInt(g, 0, 255);
        int blue = clampInt(b, 0, 255);
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    /**
     * Converts an int in argb decimal format to an array of a, r, g, b
     */
    public static int[] ARGBint_to_ARGB(int argb) {
        int a = (argb >> 24) & 0xFF;
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;
        return new int[]{a, r, g, b};
    }

    /**
     * Takes an int in argb decimal format and returns an rgb int
     */
    public static int removeAlpha(int argb) {
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;
        return (r << 16) | (g << 8) | b;
    }

    /**
     * Takes an argb int and returns the alpha
     */
    public static int extractAlpha(int argb) {
        int[] argbArray = ARGBint_to_ARGB(argb);
        return removeAlpha(argbArray[0]);
    }

    /**
     * Takes an rgb int and alpha value and returns an argb int
     */
    public static int applyAlpha(int rgb, int alpha) {
        int[] rgbArray = RGBint_to_RGB(rgb);
        return ARGB_to_ARGBint(alpha, rgbArray[0], rgbArray[1], rgbArray[2]);
    }

    /**
     * Converts rgb to an int in rgb decimal format
     */
    public static int RGB_to_RGBint(int r, int g, int b) {
        int red = clampInt(r, 0, 255);
        int green = clampInt(g, 0, 255);
        int blue = clampInt(b, 0, 255);
        return (red << 16) | (green << 8) | blue;
    }

    /**
     * Converts an int in rgb decimal format to an array of r, g, b
     */
    public static int[] RGBint_to_RGB(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        return new int[]{r, g, b};
    }

    public static int darkenRGB(int rgb, float scale) {
        int[] rgbParts = RGBint_to_RGB(rgb);
        return RGB_to_RGBint((int) (rgbParts[0] * scale), (int) (rgbParts[1] * scale), (int) (rgbParts[2] * scale));
    }

    public static String formatIntAsHexString(int colour) {
        return String.format(Locale.ROOT, "#%06X", colour);
    }
}
