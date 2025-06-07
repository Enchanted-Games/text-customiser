package games.enchanted.eg_text_customiser.common;

import games.enchanted.eg_text_customiser.common.pack.colour_override.ColourOverrideDefinition;
import games.enchanted.eg_text_customiser.common.pack.style_override.StyleOverrideDefinition;
import games.enchanted.eg_text_customiser.common.pack.property_tests.colour.ColourPredicates;

/**
 * This is the entry point for your mod's common side, called by each modloader specific side.
 */
public class ModEntry {
    public static void init() {
        Logging.info("Mod is loading on a {} environment!", ModConstants.TARGET_PLATFORM);
        ColourPredicates.registerColourPredicates();
        ColourOverrideDefinition.printExample();
    }
}
