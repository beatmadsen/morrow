package morrow.web.endpoint.routing;

import morrow.web.Response;
import morrow.web.exception.ClientException;
import morrow.web.request.Request;

class NoRouteException extends ClientException {
    public NoRouteException(Request request) {

    }

    @Override
    public Response response() {
        // TODO: 404
        return new Response();
    }
}
