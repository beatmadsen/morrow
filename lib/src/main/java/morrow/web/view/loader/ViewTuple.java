package morrow.web.view.loader;

import morrow.web.view.Renderer;

public record ViewTuple(Class<?> modelClass, Class<? extends Renderer<?, ?>> rendererClass) {
}
