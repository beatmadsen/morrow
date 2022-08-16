package morrow.web.view;

public abstract class ViewException extends Exception {
    public ViewException(String message, Throwable cause) {
        super(message, cause);
    }
}
