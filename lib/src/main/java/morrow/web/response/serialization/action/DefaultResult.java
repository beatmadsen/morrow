package morrow.web.response.serialization.action;

import morrow.web.Action;
import morrow.web.protocol.body.Body;
import morrow.web.protocol.mime.MediaType;
import morrow.web.response.Response;
import morrow.web.response.status.CommonStatusCode;
import morrow.web.response.status.StatusCode;

public class DefaultResult implements ActionResult {

    private final Action action;

    public DefaultResult(Action action) {
        this.action = action;
    }

    @Override
    public Response serialize(MediaType mediaType) {
        return new Response(mediaType, statusCode(), Body.empty());
    }

    private StatusCode statusCode() {
        return switch (action) {
            case CREATE -> CommonStatusCode.CREATED;
            case DELETE_BY_ID, UPDATE_BY_ID -> CommonStatusCode.NO_CONTENT;
            case FIND_MANY, GET_BY_ID -> CommonStatusCode.OK;
        };
    }
}
