package morrow.web.response;

import morrow.web.protocol.MediaType;

public record Response(MediaType mediaType, StatusCode statusCode) {
}
