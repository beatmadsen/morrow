package morrow.web.view;

import java.util.Map;

public class MediaTypeSpecificRendererResolver {


    private final Map<KeyTuple, ? extends Class<? extends Renderer<?, ?>>> renderersByKey;

    public MediaTypeSpecificRendererResolver(Map<KeyTuple, ? extends Class<? extends Renderer<?, ?>>> renderersByKey) {

        this.renderersByKey = renderersByKey;
    }

    public <I, O> Renderer<I, O> renderer(Class<? extends I> modelClass, String useCase) {
        try {
            Class<? extends Renderer<?, ?>> aClass = renderersByKey.get(new KeyTuple(useCase, modelClass));
            return (Renderer<I, O>) aClass.getDeclaredConstructor(MediaTypeSpecificRendererResolver.class).newInstance(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
