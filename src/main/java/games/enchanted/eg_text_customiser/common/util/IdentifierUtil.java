package games.enchanted.eg_text_customiser.common.util;

import games.enchanted.eg_text_customiser.common.ModConstants;
import net.minecraft.resources.Identifier;

public class IdentifierUtil {
    public static Identifier ofMod(String path) {
        return Identifier.fromNamespaceAndPath(ModConstants.MOD_ID, path);
    }
}
