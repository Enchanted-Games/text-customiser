package games.enchanted.eg_text_customiser.common.text_override;

import games.enchanted.eg_text_customiser.common.Logging;
import games.enchanted.eg_text_customiser.common.text_override.fake_style.FakeStyle;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class TextOverrideManager {
    // TODO: move TextOverrideDefinition to a proper place, and make it not hardcoded
    public static TextOverrideDefinition testDef = new TextOverrideDefinition(true);

    private static final Map<FakeStyle, ResourceLocation> STYLE_TO_TEXT_OVERRIDE_LOCATION = new HashMap<>();
    private static final Map<Style, FakeStyle> STYLE_CACHE = new HashMap<>();

    static {
//        STYLE_TO_TEXT_OVERRIDE.put(new FakeStyle(new SpecialTextColour(6684672), null, false, false, false, false, false, ResourceLocation.withDefaultNamespace("default")), ResourceLocation.fromNamespaceAndPath(ModConstants.MOD_ID, "inv_text_override"));
//        STYLE_TO_TEXT_OVERRIDE.put(new FakeStyle(SpecialTextColour.fromTextColor(TextColor.fromLegacyFormat(ChatFormatting.RED)), null, null, null, null, null, null, null), ResourceLocation.fromNamespaceAndPath(ModConstants.MOD_ID, "red_text_override"));
//        STYLE_TO_TEXT_OVERRIDE.put(new FakeStyle(SpecialTextColour.fromTextColor(TextColor.fromLegacyFormat(ChatFormatting.BOLD)), null, null, null, null, null, null, null), ResourceLocation.fromNamespaceAndPath(ModConstants.MOD_ID, "bold_text_override"));
    }

    public static boolean styleHasOverride(Style style) {
        FakeStyle fakeStyle = STYLE_CACHE.get(style);
        if(fakeStyle == null) {
            Logging.info("Cached style: {}", style);
            fakeStyle = FakeStyle.fromStyle(style);
            STYLE_CACHE.put(style, fakeStyle);
        }
        return testDef.styleMatches(fakeStyle);
    }
}
