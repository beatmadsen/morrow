package morrow.web.response.serialization.action;

import morrow.web.protocol.mime.MediaType;
import morrow.web.response.Response;
import morrow.web.response.serialization.BodySerializer;
import morrow.web.response.status.CommonStatusCode;

public class ViewResult implements ActionResult {

    private final Object view;

    public ViewResult(Object view) {
        this.view = view;
    }

    @Override
    public Response serialize(MediaType mediaType) {
        return new Response(mediaType, CommonStatusCode.OK, BodySerializer.forMediaType(mediaType).body(view));
    }

}
