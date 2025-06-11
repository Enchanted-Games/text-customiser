//? if fabric {
package games.enchanted.eg_text_customiser.fabric;

import games.enchanted.eg_text_customiser.common.ModEntry;
import games.enchanted.eg_text_customiser.common.pack.colour_override.ColourOverrideReloadListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class FabricClientEntry implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModEntry.init();
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new ColourOverrideReloadListener());
    }
}
//?}
