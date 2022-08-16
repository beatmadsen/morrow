package morrow.web.endpoint;

public abstract class EndpointException extends Exception {
    public EndpointException(Throwable cause) {
        super(cause);
    }

    public EndpointException(String message, Throwable cause) {
        super(message, cause);
    }

    public EndpointException(String message) {
        super(message);
    }
}
