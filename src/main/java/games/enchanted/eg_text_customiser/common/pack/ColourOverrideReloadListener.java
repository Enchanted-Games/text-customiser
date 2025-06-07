package games.enchanted.eg_text_customiser.common.pack;

import games.enchanted.eg_text_customiser.common.Logging;
import games.enchanted.eg_text_customiser.common.pack.colour_override.ColourOverrideDefinition;
import games.enchanted.eg_text_customiser.common.util.ResourceLocationUtil;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

//?}
public class ColourOverrideReloadListener extends SimpleJsonResourceReloadListener<ColourOverrideDefinition>
//? if fabric {
    implements IdentifiableResourceReloadListener
//?}
{
    public static final ResourceLocation LOCATION = ResourceLocationUtil.ofMod("text");

    private static final FileToIdConverter LISTER = FileToIdConverter.json("eg_text_customiser/colour_overrides");

    public ColourOverrideReloadListener() {
        super(ColourOverrideDefinition.CODEC, LISTER);
    }

    @Override
    protected void apply(Map<ResourceLocation, ColourOverrideDefinition> overrideDefinitions, ResourceManager resourceManager, ProfilerFiller profiler) {
        Logging.info("Running ColourOverrideReloadListener");
        TextOverrideManager.clearCaches();
        overrideDefinitions.forEach(TextOverrideManager::registerOverride);
    }

    @Override
    public @NotNull String getName() {
        return LOCATION.toString();
    }

    //? if fabric {
    @Override
    public ResourceLocation getFabricId() {
        return LOCATION;
    }
    //?}
}
