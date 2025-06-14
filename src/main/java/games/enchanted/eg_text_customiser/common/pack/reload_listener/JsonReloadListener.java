package games.enchanted.eg_text_customiser.common.pack.reload_listener;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import games.enchanted.eg_text_customiser.common.Logging;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public abstract class JsonReloadListener<T> extends SimplePreparableReloadListener<Map<ResourceLocation, T>>
//? if fabric {
    implements IdentifiableResourceReloadListener
//?}
{
    protected final Codec<T> resourceCodec;
    protected final ResourceLocation listenerName;
    protected final FileToIdConverter fileToIdConverter;

    protected JsonReloadListener(Codec<T> resourceCodec, FileToIdConverter fileToIdConverter, ResourceLocation listenerName) {
        this.resourceCodec = resourceCodec;
        this.listenerName = listenerName;
        this.fileToIdConverter = fileToIdConverter;
    }

    @Override
    protected @NotNull Map<ResourceLocation, T> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<ResourceLocation, T> map = new HashMap<>();
        parseFromDirectory(resourceManager, this.fileToIdConverter, this.resourceCodec, map);
        return map;
    }

    protected abstract void showErrors(Map<ResourceLocation, Exception> erroredFiles);

    private void parseFromDirectory(ResourceManager resourceManager, FileToIdConverter fileToIdConverter, Codec<T> resourceCodec, Map<ResourceLocation, T> outputMap) {
        Map<ResourceLocation, Exception> erroredFiles = new HashMap<>();
        for(Map.Entry<ResourceLocation, Resource> resource : fileToIdConverter.listMatchingResources(resourceManager).entrySet()) {
            ResourceLocation rawFileLocation = resource.getKey();
            ResourceLocation idLocation = fileToIdConverter.fileToId(rawFileLocation);

            try(Reader resourceReader = resource.getValue().openAsReader()) {
                resourceCodec.parse(JsonOps.INSTANCE, JsonParser.parseReader(resourceReader)).ifSuccess(parsedResource -> {
                    T prevValue = outputMap.putIfAbsent(idLocation, parsedResource);
                    if(prevValue != null) {
                        throw new IllegalStateException("Duplicate resource found '" + rawFileLocation.toString() + "', ignoring.");
                    }
                }).ifError(error -> {
                    Logging.error("Failed to parse file '{}', with error '{}'", rawFileLocation, error);
                    erroredFiles.put(rawFileLocation, new Exception("Failed to parse file '" + rawFileLocation + "', with error '" + error.message() + "'"));
                });
            } catch (IOException error) {
                Logging.error("Failed to read file '{}', with error '{}'", rawFileLocation, error);
                erroredFiles.put(rawFileLocation, error);
            } catch (JsonParseException error) {
                Logging.error("Failed to parse file '{}', with error '{}'", rawFileLocation, error);
                erroredFiles.put(rawFileLocation, error);
            }
        }
        showErrors(erroredFiles);
    }

    @Override
    public @NotNull String getName() {
        return listenerName.toString();
    }

    //? if fabric {
    @Override
    public ResourceLocation getFabricId() {
        return listenerName;
    }
    //?}
}
