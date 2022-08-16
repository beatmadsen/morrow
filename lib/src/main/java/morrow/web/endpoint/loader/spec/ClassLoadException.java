package morrow.web.endpoint.loader.spec;

class ClassLoadException extends RuntimeException {
    ClassLoadException(String controller, Exception e) {
        super("Could not load controller class %s: %s".formatted(controller, e.getMessage()), e);
    }
}
