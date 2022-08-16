package morrow.web.view.loader.resolver.spec;

import morrow.web.view.Renderer;

public record ViewTuple(Class<?> modelClass, Class<? extends Renderer<?, ?>> rendererClass) {
}
