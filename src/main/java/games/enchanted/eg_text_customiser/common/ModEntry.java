package games.enchanted.eg_text_customiser.common;

/**
 * This is the entry point for your mod's common side, called by each modloader specific side.
 */
public class ModEntry {
    public static void init() {
        Logging.info("Mod is loading on a {} environment!", ModConstants.TARGET_PLATFORM);
    }
}
