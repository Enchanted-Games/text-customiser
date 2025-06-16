//? if neoforge {
/*package games.enchanted.eg_text_customiser.neoforge;

import games.enchanted.eg_text_customiser.common.ModEntry;
import games.enchanted.eg_text_customiser.common.pack.colour_override.ColourOverrideReloadListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
//? if minecraft: >= 1.21.5 {
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
//?} else {
/^import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
^///?}

/^
 * This is the entry point for your mod's forge side.
 ^/
@Mod(value = "eg_text_customiser", dist = Dist.CLIENT)
public class NeoForgeEntry {
    public NeoForgeEntry(IEventBus bus) {
        ModEntry.init();

        //? if minecraft: >= 1.21.5 {
        bus.addListener((AddClientReloadListenersEvent event) -> event.addListener(ColourOverrideReloadListener.NAME, new ColourOverrideReloadListener()));
        //?} else {
        /^bus.addListener((RegisterClientReloadListenersEvent event) -> event.registerReloadListener(new ColourOverrideReloadListener()));
        ^///?}
    }
}
*/