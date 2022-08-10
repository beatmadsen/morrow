package morrow.web.response.rendering;

import morrow.web.endpoint.Action;
import morrow.web.protocol.body.Body;
import morrow.web.protocol.mime.MediaType;

import java.util.Map;

public interface BodyRenderer {
    static BodyRenderer forMediaType(MediaType mediaType) {
        if (mediaType.type() == MediaType.Type.APPLICATION && mediaType.subtype() == MediaType.Subtype.JSON) {
            return new JsonBodyRenderer();
        }
        throw new RuntimeException("Cannot render " + mediaType.contentTypeHeaderValue());
    }

    Body body(Action action, Map<String, Object> renderState);
}
