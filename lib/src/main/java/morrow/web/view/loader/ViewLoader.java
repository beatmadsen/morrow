package morrow.web.view.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import morrow.config.LoadHelper;
import morrow.web.protocol.mime.MediaType;
import morrow.yaml.LoadingException;
import morrow.yaml.YamlLoader;

import java.util.List;
import java.util.Map;

public class ViewLoader {

    private record ViewTuple(String type, String subtype, String useCase, String model, String renderer) {
    }

    ;

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
                try {
                    Class<?> x = new LoadHelper(Object.class).loadClass(t.model);
                    Class<? extends Renderer<?, ?>> y = new LoadHelper(Renderer.class).loadClass(t.renderer);
                    var mediaType = MediaType.freeHand(t.type, t.subtype, Map.of());
                    return new ViewConfig(mediaType, t.useCase, x, y);
                } catch (LoadHelper.ClassLoadException e) {
                    throw new RuntimeException(e);
                }
            }).peek(this::validate).map(c -> {
                return new ViewDescriptor(c.mediaType, c.useCase, c.model, c.renderer);
            });

            return map;
        } catch (LoadingException e) {
            throw new RuntimeException(e);
        }
    }

    public class ViewDescriptor {

        private final MediaType mediaType;
        private final String useCase;
        private final Class<?> modelClass;
        private final Class<? extends Renderer<?, ?>> renderer;

        public ViewDescriptor(MediaType mediaType, String useCase, Class<?> modelClass, Class<? extends Renderer<?, ?>> renderer) {
            this.mediaType = mediaType;
            this.useCase = useCase;
            this.modelClass = modelClass;
            this.renderer = renderer;
        }

        public boolean matches(MediaType mediaType, String useCase) {
            return false; // TODO
        }

        public <I, O> Renderer<I, O> renderer(UseCaseRendererRouter router) {
            return null; // TODO
        }
    }

    public static class UseCaseRendererRouter {
        public Renderer<?, ?> renderer(Object model) {
            return null; // TODO
        }
    }

    private void validate(ViewConfig viewConfig) {
        // TODO
    }

    public record RenderSpec(String model, String renderer) {
    }

    public record ViewConfig(MediaType mediaType, String useCase, Class<?> model,
                             Class<? extends Renderer<?, ?>> renderer) {

    }

    public static abstract class Renderer<I, O> {

        private final UseCaseRendererRouter router;

        public Renderer(UseCaseRendererRouter router) {
            this.router = router;
        }

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
