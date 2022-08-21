package morrow.web.response.serialization;

import morrow.web.protocol.body.Body;
import morrow.web.protocol.mime.MediaType;

public interface BodySerializer {
    static BodySerializer forMediaType(MediaType mediaType) {
        if (mediaType.type() == MediaType.Type.APPLICATION && mediaType.subtype() == MediaType.Subtype.JSON) {
            return new JsonBodySerializer();
        }
        throw new RuntimeException("Cannot render " + mediaType.contentTypeHeaderValue());
    }

    Body body(Object view);
}
