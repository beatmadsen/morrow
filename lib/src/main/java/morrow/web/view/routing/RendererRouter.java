package morrow.web.view.routing;

import morrow.web.view.Renderer;
import morrow.web.view.loader.ViewLoader;

import java.util.Map;

public class RendererRouter {


    public RendererRouter(Map<ViewLoader.KeyTuple, ? extends Class<? extends Renderer<?, ?>>> renderersByKey) {

    }

    public <I, O> Renderer<I, O> renderer(Class<? extends I> modelClass, String useCase) {
        return null; // TODO
    }
}
