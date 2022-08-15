package morrow.web.view.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import morrow.web.protocol.mime.MediaType;
import morrow.yaml.LoadingException;
import morrow.yaml.YamlLoader;

import java.util.List;
import java.util.Map;

public class ViewLoader {

    private record ViewTuple(String type, String subtype, String useCase, String model, String renderer){};

    public Object x() {
        try {
            var map = new YamlLoader<>(new TypeReference<Map<String, Map<String, Map<String, List<RenderSpec>>>>>() {
            }).loadResource("views.yml");

            var viewConfigStream = map.entrySet().stream().flatMap(subtypes -> {
                var type = subtypes.getKey();
                return subtypes.getValue().entrySet().stream().flatMap(useCases -> {
                    var subtype = useCases.getKey();
                    return useCases.getValue().entrySet().stream().flatMap(renderers -> {
                        var useCase = renderers.getKey();
                        return renderers.getValue().stream().map(u -> new ViewTuple(type, subtype, useCase, u.model, u.renderer));
                    });
                });
            }).map(t -> {
                Class<?> x = loadClass(t.model);
                Class<? extends Renderer<?,?>> y = loadClass(t.renderer);
                var mediaType = MediaType.freeHand(t.type, t.subtype, Map.of());
                return new ViewConfig(mediaType, t.useCase, x, y);
            });

            return map;
        } catch (LoadingException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> Class<? extends T> loadClass(String className) {
        // TODO
        return null;
    }

    public record RenderSpec(String model, String renderer) {
    }

    public record ViewConfig(MediaType mediaType, String useCase, Class<?> model, Class<? extends Renderer<?, ?>> renderer) {

    }

    public static abstract class Renderer<I, O> {
        public abstract O render(I model);

        /**
         * Resolves correct renderer and applies it to input model
         *
         * @param rendererName a hint to resolve the correct renderer when there are multiple configured implementations
         */
        protected <T> T renderChild(Object model, String rendererName) {
            throw new RuntimeException("soon");
        }

        protected <T> List<T> renderChildren(List<?> models, String rendererName) {
            throw new RuntimeException("soon");
        }

        protected <T> List<T> renderChildren(List<?> models) {
            return renderChildren(models, "default");
        }

        /**
         * Resolves default renderer for model class and applies it
         */
        protected <T> T renderChild(Object model) {
            return renderChild(model, "default");
        }


    }


}
