package morrow.web.view;

import morrow.web.protocol.mime.MediaType;
import morrow.web.view.loader.ViewLoader;
import morrow.web.view.routing.RendererRouter;

import java.util.Map;

public class ControllerRenderPlugin {

    public static ControllerRenderPlugin load() throws LoadException {
        try {
            var v = new ViewLoader();
            return new ControllerRenderPlugin(v.loadViews());
        } catch (Exception e) {
            throw new LoadException("Could not load render plugin: " + e.getMessage(), e);
        }
    }

    public static class LoadException extends Exception {
        public LoadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private final Map<MediaType.Key, RendererRouter> routers;

    ControllerRenderPlugin(Map<MediaType.Key, RendererRouter> routers) {
        this.routers = routers;
    }

    public <I, O> O render(I model, MediaType mediaType) {
        return render(model, mediaType, "default");
    }

    public <I, O> O render(I model, MediaType mediaType, String useCase) {

        Class<? extends I> aClass = (Class<? extends I>) model.getClass();
        var router = routers.get(mediaType.key());
        Renderer<I, O> renderer = router.renderer(aClass, useCase);
        return renderer.render(model);
    }
}
