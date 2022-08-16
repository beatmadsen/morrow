package morrow.web.view.loader.resolver;

class MissingRendererException extends RuntimeException {
    MissingRendererException(String useCase, Class<?> modelClass) {
        super("Could not resolve a renderer for use case %s and model class %s".formatted(useCase, modelClass));
    }
}
