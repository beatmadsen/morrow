package morrow.web.view.loader.resolver;

import morrow.web.view.KeyTuple;
import morrow.web.view.Renderer;

import java.util.Map;

public class MediaTypeSpecificRendererResolver {

    private final Map<KeyTuple, ? extends Class<? extends Renderer<?, ?>>> renderersByKey;

    public MediaTypeSpecificRendererResolver(Map<KeyTuple, ? extends Class<? extends Renderer<?, ?>>> renderersByKey) {

        this.renderersByKey = renderersByKey;
    }

    public <I, O> Renderer<I, O> renderer(Class<? extends I> modelClass, String useCase) {
        var aClass = renderersByKey.get(new KeyTuple(useCase, modelClass));
        if (aClass == null) {
            throw new MissingRendererException(useCase, modelClass);
        }
        return instantiate(aClass);
    }

    @SuppressWarnings("unchecked")
    private <I, O> Renderer<I, O> instantiate(Class<? extends Renderer<?, ?>> aClass) {
        try {
            return (Renderer<I, O>) aClass.getDeclaredConstructor(MediaTypeSpecificRendererResolver.class).newInstance(this);
        } catch (Exception e) {
            throw new RendererInstantiationException(e);
        }
    }
}
