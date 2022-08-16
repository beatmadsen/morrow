package morrow.application;

public abstract class ApplicationException extends Exception {

    public ApplicationException(String message, Exception cause) {
        super(message, cause);
    }
}
