package morrow.config.singleton.loader;

class ClassLoadException extends RuntimeException {
    ClassLoadException(Exception e) {
        super(e);
    }
}
