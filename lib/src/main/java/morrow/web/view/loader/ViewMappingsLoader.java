package morrow.web.view.loader;

import morrow.config.Validation;
import morrow.web.protocol.mime.MediaType;
import morrow.web.view.ViewException;
import morrow.web.view.loader.file.ConfigFileLoader;
import morrow.web.view.loader.resolver.MediaTypeSpecificRendererResolver;
import morrow.web.view.loader.resolver.ResolverLoader;
import morrow.web.view.loader.resolver.spec.RenderSpec;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ViewMappingsLoader {

    private static final Collector<RendererTuple, ?, Map<MediaType.Key, MediaTypeSpecificRendererResolver>>
            resolverByMediaTypeKeyCollector = Collectors.toMap(RendererTuple::mediaTypeKey, RendererTuple::resolver);
    private final Validation validation;

    public ViewMappingsLoader(Validation validation) {
        this.validation = validation;
    }


    public Map<MediaType.Key, MediaTypeSpecificRendererResolver> loadViewMappings() throws ViewException {
        try {
            return new ConfigFileLoader().loadViewsFile()
                    .entrySet()
                    .stream()
                    .flatMap(subtypes -> streamRendererTuples(subtypes.getKey(), subtypes.getValue()))
                    .collect(resolverByMediaTypeKeyCollector);
        } catch (Exception e) {
            throw new LoaderException(e);
        }
    }

    private Stream<RendererTuple> streamRendererTuples(String type, Map<String, Map<String, List<RenderSpec>>> renderSpecsBySubtype) {

        return renderSpecsBySubtype
                .entrySet()
                .stream()
                .map(e -> rendererTuple(type, e));
    }

    private RendererTuple rendererTuple(String type, Map.Entry<String, Map<String, List<RenderSpec>>> useCasesForGivenSubtype) {
        var subtype = useCasesForGivenSubtype.getKey();
        var mediaType = MediaType.freeHand(type, subtype);
        var resolver = createResolver(useCasesForGivenSubtype);
        return new RendererTuple(mediaType.key(), resolver);
    }

    private MediaTypeSpecificRendererResolver createResolver(Map.Entry<String, Map<String, List<RenderSpec>>> useCasesBySubtype) {
        var renderSpecsByUseCase = useCasesBySubtype.getValue();
        var loader = new ResolverLoader(validation, renderSpecsByUseCase);
        return loader.loadResolver();
    }

    private record RendererTuple(MediaType.Key mediaTypeKey, MediaTypeSpecificRendererResolver resolver) {
    }


}
