package morrow.web.view.routing;

import morrow.web.view.KeyTuple;
import morrow.web.view.Renderer;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class RendererRouter {


    private final Map<KeyTuple, ? extends Class<? extends Renderer<?, ?>>> renderersByKey;

    public RendererRouter(Map<KeyTuple, ? extends Class<? extends Renderer<?, ?>>> renderersByKey) {

        this.renderersByKey = renderersByKey;
    }

    public <I, O> Renderer<I, O> renderer(Class<? extends I> modelClass, String useCase) {
        try {
            Class<? extends Renderer<?, ?>> aClass = renderersByKey.get(new KeyTuple(useCase, modelClass));
            return (Renderer<I, O>) aClass.getDeclaredConstructor(RendererRouter.class).newInstance(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
