package morrow.rest.exception;

import morrow.rest.Response;

public class InternalServerException extends ServerException {

    public InternalServerException(Throwable cause) {
        super(cause);
    }

    @Override
    public Response response() {
        return null;
    }
}
