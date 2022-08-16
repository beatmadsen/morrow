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


    private record ViewTuple(Class<?> modelClass, Class<? extends Renderer<?, ?>> rendererClass) {
    }

    public record KeyTuple(String useCase, Class<?> modelClass) {
    }


    public Map<MediaType, RendererRouter> loadViews() {
        try {
            var loader = new YamlLoader<Map<String, Map<String, Map<String, List<RenderSpec>>>>>(new TypeReference<Map<String, Map<String, Map<String, List<RenderSpec>>>>>() {
            });

            return loader.loadResource("views.yml")
                    .entrySet()
                    .stream()
                    .flatMap(subtypes -> streamRenderTuples(subtypes.getKey(), subtypes.getValue()))
                    .collect(Collectors.toMap(RendererTuple::mediaType, RendererTuple::router));
        } catch (LoadingException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<RendererTuple> streamRenderTuples(String type, Map<String, Map<String, List<RenderSpec>>> renderSpecsBySubtype) {

        return renderSpecsBySubtype
                .entrySet()
                .stream()
                .map(useCasesBySubtype -> {
                    var subtype = useCasesBySubtype.getKey();
                    var mediaType = MediaType.freeHand(type, subtype, Map.of());
                    var rendererRouter = streamRenderTuples(useCasesBySubtype.getValue(), mediaType);
                    return new RendererTuple(mediaType, rendererRouter);
                });
    }

    private record RendererTuple(MediaType mediaType, RendererRouter router) {}


    private RendererRouter streamRenderTuples(Map<String, List<RenderSpec>> renderSpecsByUseCase, MediaType mediaType) {
        var renderersByKey = renderSpecsByUseCase
                .entrySet()
                .stream()
                .flatMap(renderers -> {
                    var useCase = renderers.getKey();
                    return routerEntries(useCase, renderers.getValue());
                }).collect(Collectors.toMap(Y::keyTuple, Y::rendererClass));

        return new RendererRouter(renderersByKey);
    }

    private record Y(KeyTuple keyTuple, Class<? extends Renderer<?, ?>> rendererClass) {

    }




    private Stream<Y> routerEntries(String useCase, List<RenderSpec> renderSpecs) {
        return renderSpecs
                .stream()
                .map(ViewLoader::loadClasses)
                .peek(this::validate).
                map(vt -> new Y(new KeyTuple(useCase, vt.modelClass), vt.rendererClass));
    }



    private static ViewTuple loadClasses(RenderSpec t) {
        try {
            Class<?> modelClass = new LoadHelper(Object.class).loadClass(t.model);
            Class<? extends Renderer<?, ?>> rendererClass = new LoadHelper(Renderer.class).loadClass(t.renderer);
            return new ViewTuple(modelClass, rendererClass);
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
