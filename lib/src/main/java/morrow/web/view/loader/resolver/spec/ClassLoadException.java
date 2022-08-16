package morrow.web.view.loader.resolver.spec;

class ClassLoadException extends RuntimeException {
    ClassLoadException(RenderSpec renderSpec, Exception e) {
        super("Failed to load classes referred to in RenderSpec %s, %s".formatted(renderSpec, e.getMessage()), e);
    }
}
