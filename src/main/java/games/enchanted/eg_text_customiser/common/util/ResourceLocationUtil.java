package games.enchanted.eg_text_customiser.common.util;

import games.enchanted.eg_text_customiser.common.ModConstants;
import net.minecraft.resources.ResourceLocation;

public class ResourceLocationUtil {
    public static ResourceLocation ofMod(String path) {
        return ResourceLocation.fromNamespaceAndPath(ModConstants.MOD_ID, path);
    }
}
