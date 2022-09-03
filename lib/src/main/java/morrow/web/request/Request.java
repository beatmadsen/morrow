package morrow.web.request;

import morrow.web.protocol.header.request.Map;

public record Request(Path path, Method method, Map headers) {

}
