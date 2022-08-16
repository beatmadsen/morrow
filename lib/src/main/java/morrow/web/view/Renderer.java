package morrow.web.view;

import morrow.web.view.routing.MediaTypeSpecificRendererResolver;

import java.util.List;

/**
 *
 * @param <I> single instance of input model (necessary because of format of views.yml).
 *           If a list/aggregate input is needed it should be wrapped in an instance of a wrapper class.
 * @param <O> single instance or aggregate of output models as translated by renderer
 */
public abstract class Renderer<I, O> {

    private final MediaTypeSpecificRendererResolver router;

    public Renderer(MediaTypeSpecificRendererResolver router) {
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
