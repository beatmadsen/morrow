package morrow.web.exception;

import morrow.Tracker;
import morrow.web.response.Response;
import org.tinylog.Logger;

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
        Logger.error("Internal server error from exception: " + getMessage(), this);
        Tracker.serverException(new Tracker.MetaData(), this);
    }
}
