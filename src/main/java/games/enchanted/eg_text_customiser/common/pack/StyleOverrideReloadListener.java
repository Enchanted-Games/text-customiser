package games.enchanted.eg_text_customiser.common.pack;

import games.enchanted.eg_text_customiser.common.Logging;
import games.enchanted.eg_text_customiser.common.pack.style_override.StyleOverrideDefinition;
import games.enchanted.eg_text_customiser.common.util.ResourceLocationUtil;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

//? if fabric {
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
//?}

public class StyleOverrideReloadListener extends SimpleJsonResourceReloadListener<StyleOverrideDefinition>
//? if fabric {
    implements IdentifiableResourceReloadListener
//?}
{
    public static final ResourceLocation LOCATION = ResourceLocationUtil.ofMod("text");

    private static final FileToIdConverter LISTER = FileToIdConverter.json("eg_text_customiser/style_overrides");

    public StyleOverrideReloadListener() {
        super(StyleOverrideDefinition.CODEC, LISTER);
    }

    @Override
    protected void apply(Map<ResourceLocation, StyleOverrideDefinition> overrideDefinitions, ResourceManager resourceManager, ProfilerFiller profiler) {
        Logging.info("Running TextOverrideReloadListener");
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
