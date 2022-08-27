package morrow.config.singleton.loader;

public class LoaderException extends Exception {
    public LoaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoaderException(String message) {
        super(message);
    }
}
