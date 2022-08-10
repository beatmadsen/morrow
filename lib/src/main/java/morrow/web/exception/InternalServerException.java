package morrow.web.exception;

import morrow.Tracker;
import morrow.web.Response;

public class InternalServerException extends ServerException {

    public InternalServerException(Throwable cause) {
        super(cause);
    }

    @Override
    public Response response() {
        return null;
    }

    @Override
    public void track() {
        Tracker.serverException(new Tracker.MetaData(), this);
    }
}
