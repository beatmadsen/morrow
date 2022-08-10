package morrow.web.exception;

public abstract class ServerException extends RuntimeException implements WebException {
    public ServerException(Throwable cause) {
        super(cause);
    }
}
