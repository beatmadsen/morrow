package morrow.web.view.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import morrow.config.Validation;
import morrow.web.protocol.mime.MediaType;
import morrow.web.view.KeyTuple;
import morrow.web.view.Renderer;
import morrow.web.view.ViewException;
import morrow.web.view.loader.spec.RenderSpec;
import morrow.web.view.loader.spec.SpecLoader;
import morrow.web.view.MediaTypeSpecificRendererResolver;
import morrow.yaml.YamlLoader;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ViewLoader {

    private final Validation validation;

    public ViewLoader(Validation validation) {
        this.validation = validation;
    }


    public Map<MediaType.Key, MediaTypeSpecificRendererResolver> loadViews() throws ViewException {
        try {
            var loader = new YamlLoader<Map<String, Map<String, Map<String, List<RenderSpec>>>>>(new TypeReference<Map<String, Map<String, Map<String, List<RenderSpec>>>>>() {
            });

            return loader.loadResource("views.yml")
                    .entrySet()
                    .stream()
                    .flatMap(subtypes -> streamRendererTuples(subtypes.getKey(), subtypes.getValue()))
                    .collect(Collectors.toMap(rendererTuple -> rendererTuple.mediaType().key(), RendererTuple::resolver));
        } catch (Exception e) {
            throw new LoaderException(e);
        }
    }

    private Stream<RendererTuple> streamRendererTuples(String type, Map<String, Map<String, List<RenderSpec>>> renderSpecsBySubtype) {

        return renderSpecsBySubtype
                .entrySet()
                .stream()
                .map(useCasesBySubtype -> {
                    var subtype = useCasesBySubtype.getKey();
                    var mediaType = MediaType.freeHand(type, subtype, Map.of());
                    var resolver = createResolver(useCasesBySubtype.getValue());
                    return new RendererTuple(mediaType, resolver);
                });
    }

    private record RendererTuple(MediaType mediaType, MediaTypeSpecificRendererResolver resolver) {
    }

    


    private MediaTypeSpecificRendererResolver createResolver(Map<String, List<RenderSpec>> renderSpecsByUseCase) {
        var renderersByKey = renderSpecsByUseCase
                .entrySet()
                .stream()
                .flatMap(renderers -> {
                    var useCase = renderers.getKey();
                    return resolverEntries(useCase, renderers.getValue());
                }).collect(Collectors.toMap(ResolverEntry::keyTuple, ResolverEntry::rendererClass));

        return new MediaTypeSpecificRendererResolver(renderersByKey);
    }

    private record ResolverEntry(KeyTuple keyTuple, Class<? extends Renderer<?, ?>> rendererClass) {

    }


    private Stream<ResolverEntry> resolverEntries(String useCase, List<RenderSpec> renderSpecs) {
        return renderSpecs.stream()
                .map(renderSpec -> new SpecLoader(validation, renderSpec))
                .peek(SpecLoader::validate)
                .map(SpecLoader::loadClasses)
                .map(vt -> new ResolverEntry(new KeyTuple(useCase, vt.modelClass()), vt.rendererClass()));
    }


}
