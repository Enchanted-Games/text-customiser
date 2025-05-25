//? if fabric {
package games.enchanted.eg_text_customiser.fabric;

import games.enchanted.eg_text_customiser.common.ModEntry;
import net.fabricmc.api.ModInitializer;

public class FabricCommonEntry implements ModInitializer {
    @Override
    public void onInitialize() {
        ModEntry.init();
    }
}
//?}