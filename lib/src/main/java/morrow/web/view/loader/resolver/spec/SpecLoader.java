package morrow.web.view.loader.resolver.spec;

import morrow.config.LoadHelper;
import morrow.config.validation.Validation;
import morrow.web.view.Renderer;

public class SpecLoader {

    private final RenderSpec renderSpec;
    private final Validation validation;

    public SpecLoader(Validation validation, RenderSpec renderSpec) {
        this.validation = validation;
        this.renderSpec = renderSpec;
    }

    public ViewTuple loadClasses() {
        try {
            Class<?> modelClass = new LoadHelper(Object.class).loadClass(renderSpec.model());
            Class<? extends Renderer<?, ?>> rendererClass = new LoadHelper(Renderer.class).loadClass(renderSpec.renderer());
            return new ViewTuple(modelClass, rendererClass);
        } catch (LoadHelper.ClassLoadException e) {
            throw new ClassLoadException(renderSpec, e);
        }
    }

    public void validate() {
        validation.validator().validate(renderSpec);
    }
}
