package morrow.web.request;

import morrow.web.protocol.header.request.RequestHeaderMap;

public record Request(Path path, Method method, RequestHeaderMap headers) {

}
