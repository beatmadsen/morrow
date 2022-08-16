package morrow.web.view.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import morrow.config.Validation;
import morrow.web.protocol.mime.MediaType;
import morrow.web.view.loader.resolver.MediaTypeSpecificRendererResolver;
import morrow.web.view.ViewException;
import morrow.web.view.loader.resolver.ResolverLoader;
import morrow.web.view.loader.spec.RenderSpec;
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
                .map(e -> rendererTuple(type, e));
    }

    private RendererTuple rendererTuple(String type, Map.Entry<String, Map<String, List<RenderSpec>>> useCasesForGivenSubtype) {
        var subtype = useCasesForGivenSubtype.getKey();
        var mediaType = MediaType.freeHand(type, subtype);
        var resolver = createResolver(useCasesForGivenSubtype);
        return new RendererTuple(mediaType, resolver);
    }

    private MediaTypeSpecificRendererResolver createResolver(Map.Entry<String, Map<String, List<RenderSpec>>> useCasesBySubtype) {
        var renderSpecsByUseCase = useCasesBySubtype.getValue();
        var loader = new ResolverLoader(validation, renderSpecsByUseCase);
        return loader.loadResolver();
    }

    private record RendererTuple(MediaType mediaType, MediaTypeSpecificRendererResolver resolver) {
    }


}
