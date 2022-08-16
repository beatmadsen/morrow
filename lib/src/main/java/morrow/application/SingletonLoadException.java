package morrow.application;

class SingletonLoadException extends ApplicationException {

    SingletonLoadException(Exception e) {
        super("Failed to load singletons: " + e.getMessage(), e);
    }
}
