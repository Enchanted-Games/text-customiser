//? if neoforge {
/*package games.enchanted.eg_text_customiser.neoforge;

import games.enchanted.eg_text_customiser.common.ModEntry;
import games.enchanted.eg_text_customiser.common.pack.text_override.ColourOverrideReloadListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;

/^*
 * This is the entry point for your mod's forge side.
 ^/
@Mod(value = "eg_text_customiser", dist = Dist.CLIENT)
public class NeoForgeEntry {
    public NeoForgeEntry(IEventBus bus) {
        ModEntry.init();

        bus.addListener((AddClientReloadListenersEvent event) -> event.addListener(ColourOverrideReloadListener.LOCATION, new ColourOverrideReloadListener()));
    }
}
*///?}