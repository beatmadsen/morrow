package morrow.web.view.loader.resolver;

class RendererInstantiationException extends RuntimeException {
    RendererInstantiationException(Exception e) {
        super("Could not instantiate renderer: " + e.getMessage(), e);
    }
}
