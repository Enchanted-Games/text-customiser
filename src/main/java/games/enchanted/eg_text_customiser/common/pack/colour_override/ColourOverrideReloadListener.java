package games.enchanted.eg_text_customiser.common.pack.colour_override;

import games.enchanted.eg_text_customiser.common.Logging;
import games.enchanted.eg_text_customiser.common.ModConstants;
import games.enchanted.eg_text_customiser.common.pack.TextOverrideManager;
import games.enchanted.eg_text_customiser.common.pack.reload_listener.JsonReloadListener;
import games.enchanted.eg_text_customiser.common.util.ResourceLocationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class ColourOverrideReloadListener extends JsonReloadListener<ColourOverrideDefinition>
{
    public static final ResourceLocation NAME = ResourceLocationUtil.ofMod("colour_override_listener");

    private static final FileToIdConverter LISTER = FileToIdConverter.json("eg_text_customiser/color_overrides");

    public ColourOverrideReloadListener() {
        super(ColourOverrideDefinition.CODEC, LISTER, NAME);
    }

    @Override
    protected void apply(Map<ResourceLocation, ColourOverrideDefinition> overrideDefinitions, ResourceManager resourceManager, ProfilerFiller profiler) {
        Logging.info("Running ColourOverrideReloadListener");
        TextOverrideManager.clearCaches();
        overrideDefinitions.forEach(TextOverrideManager::registerOverride);
    }

    @Override
    protected void showErrors(Map<ResourceLocation, Exception> erroredFiles) {
        if(erroredFiles.isEmpty()) return;
        SystemToast.addOrUpdate(
            //? if minecraft: <= 1.21.4 {
            /*Minecraft.getInstance().getToasts(),
            *///?} else {
            Minecraft.getInstance().getToastManager(),
            //?}
            ModConstants.RELOAD_FAILED_TOAST,
            Component.translatableWithFallback("eg_text_customiser.toast.override_reload_failure.title", "Text Customiser").withStyle(Style.EMPTY.withBold(true)),
            Component.translatableWithFallback("eg_text_customiser.toast.override_reload_failure.desc", "Some Colour Overrides failed to load. See output log for more info")
        );
    }
}
