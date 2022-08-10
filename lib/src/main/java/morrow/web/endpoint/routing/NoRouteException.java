package morrow.web.endpoint.routing;

import morrow.web.Response;
import morrow.web.exception.ClientException;
import morrow.web.protocol.StandardMediaType;
import morrow.web.request.Request;

class NoRouteException extends ClientException {
    public NoRouteException(Request request) {

    }

    @Override
    public Response response() {
        // TODO: create 404 hook for custom response
        return new Response(StandardMediaType.PLAIN_TEXT_UTF8);
    }
}
