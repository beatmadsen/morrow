package morrow.web.view.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import morrow.config.LoadHelper;
import morrow.web.protocol.mime.MediaType;
import morrow.web.view.Renderer;
import morrow.web.view.routing.RendererRouter;
import morrow.yaml.LoadingException;
import morrow.yaml.YamlLoader;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ViewLoader {


    private record ViewTuple(String useCase, Class<?> modelClass, Class<? extends Renderer<?, ?>> rendererClass) {
    }

    public record KeyTuple(String useCase, Class<?> modelClass) {
    }

    private record RendererTuple(MediaType mediaType, RendererRouter rendererRouter) {
    }

    public Map<MediaType, RendererRouter> loadViews() {
        try {
            var loader = new YamlLoader<Map<String, Map<String, Map<String, List<RenderSpec>>>>>(new TypeReference<Map<String, Map<String, Map<String, List<RenderSpec>>>>>() {
            });

            return loader.loadResource("views.yml")
                    .entrySet()
                    .stream()
                    .flatMap(subtypes -> streamRenderTuples(subtypes.getKey(), subtypes.getValue()))
                    .collect(Collectors.toMap(RendererTuple::mediaType, RendererTuple::rendererRouter));
//
        } catch (LoadingException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<RendererTuple> streamRenderTuples(String type, Map<String, Map<String, List<RenderSpec>>> renderSpecsBySubtype) {
        return renderSpecsBySubtype
                .entrySet()
                .stream()
                .flatMap(useCases -> {
                    var mediaType = MediaType.freeHand(type, useCases.getKey(), Map.of());
                    return streamRenderTuples(useCases.getValue(), mediaType);
                });
    }

    private Stream<RendererTuple> streamRenderTuples(Map<String, List<RenderSpec>> renderSpecsByUseCase, MediaType mediaType) {
        return renderSpecsByUseCase
                .entrySet()
                .stream()
                .map(renderers -> renderTuple(mediaType, renderers.getKey(), renderers.getValue()));
    }

    private RendererTuple renderTuple(MediaType mediaType, String useCase, List<RenderSpec> renderSpecs) {
        var renderersByKey = renderSpecs
                .stream()
                .map(t -> map(useCase, t))
                .peek(this::validate)
                .collect(Collectors.toMap(ViewLoader::asKey, ViewTuple::rendererClass));

        return new RendererTuple(mediaType, new RendererRouter(renderersByKey));
    }

    private static KeyTuple asKey(ViewTuple t) {
        return new KeyTuple(t.useCase(), t.modelClass());
    }

    private static ViewTuple map(String useCase, RenderSpec t) {
        try {
            Class<?> modelClass = new LoadHelper(Object.class).loadClass(t.model);
            Class<? extends Renderer<?, ?>> rendererClass = new LoadHelper(Renderer.class).loadClass(t.renderer);
            return new ViewTuple(useCase, modelClass, rendererClass);
        } catch (LoadHelper.ClassLoadException e) {
            throw new RuntimeException(e);
        }
    }

    private void validate(ViewTuple viewTuple) {
        // TODO
    }


    public record RenderSpec(String model, String renderer) {
    }


}
