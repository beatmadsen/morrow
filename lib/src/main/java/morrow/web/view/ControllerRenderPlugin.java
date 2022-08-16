package morrow.web.view;

import morrow.config.Validation;
import morrow.web.protocol.mime.MediaType;
import morrow.web.view.loader.ViewMappingsLoader;
import morrow.web.view.loader.resolver.MediaTypeSpecificRendererResolver;

import java.util.Map;

public class ControllerRenderPlugin {

    public static ControllerRenderPlugin load(Validation validation) throws ViewException {
        var v = new ViewMappingsLoader(validation);
        return new ControllerRenderPlugin(v.loadViewMappings());
    }

    private final Map<MediaType.Key, MediaTypeSpecificRendererResolver> resolvers;

    private ControllerRenderPlugin(Map<MediaType.Key, MediaTypeSpecificRendererResolver> resolvers) {
        this.resolvers = resolvers;
    }

    public <I, O> O render(I model, MediaType mediaType) {
        return render(model, mediaType, "default");
    }

    @SuppressWarnings("unchecked")
    public <I, O> O render(I model, MediaType mediaType, String useCase) {
        Class<? extends I> aClass = (Class<? extends I>) model.getClass();
        Renderer<I, O> renderer = lookupMappings(mediaType).renderer(aClass, useCase);
        return renderer.render(model);
    }

    private MediaTypeSpecificRendererResolver lookupMappings(MediaType mediaType) {
        var resolver = resolvers.get(mediaType.key());
        if (resolver == null) {
            throw new MissingMappingsException(mediaType);
        }
        return resolver;
    }

}
