package morrow.config.singleton;

class LookupException extends RuntimeException {

    LookupException(Class<?> key) {
        super("Did not find singleton of type %s".formatted(key));
    }
}
