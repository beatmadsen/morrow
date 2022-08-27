package morrow.config.singleton.loader;

class InstantiationException extends RuntimeException {
    InstantiationException(Exception e) {
        super(e);
    }
}
