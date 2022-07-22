package morrow.rest.exception;

public abstract class ServerException extends RuntimeException implements X {
    public ServerException(Throwable cause) {
        super(cause);
    }
}
