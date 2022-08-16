package morrow.web.view;

import morrow.web.protocol.mime.MediaType;
import morrow.web.view.loader.ViewLoader;
import morrow.web.view.routing.MediaTypeSpecificRendererResolver;

import java.util.Map;

public class ControllerRenderPlugin {

    public static ControllerRenderPlugin load() throws ViewException {
        var v = new ViewLoader();
        return new ControllerRenderPlugin(v.loadViews());
    }

    private final Map<MediaType.Key, MediaTypeSpecificRendererResolver> resolvers;

    ControllerRenderPlugin(Map<MediaType.Key, MediaTypeSpecificRendererResolver> resolvers) {
        this.resolvers = resolvers;
    }

    public <I, O> O render(I model, MediaType mediaType) {
        return render(model, mediaType, "default");
    }

    public <I, O> O render(I model, MediaType mediaType, String useCase) {

        Class<? extends I> aClass = (Class<? extends I>) model.getClass();
        var router = resolvers.get(mediaType.key());
        Renderer<I, O> renderer = router.renderer(aClass, useCase);
        return renderer.render(model);
    }

}
