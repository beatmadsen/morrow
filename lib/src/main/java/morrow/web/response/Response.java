package morrow.web.response;

import morrow.web.protocol.MediaType;
import morrow.web.protocol.body.Body;
import morrow.web.response.status.StatusCode;

public record Response(MediaType mediaType, StatusCode statusCode, Body body) {
}
