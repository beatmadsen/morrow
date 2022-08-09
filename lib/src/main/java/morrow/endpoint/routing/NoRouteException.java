package morrow.endpoint.routing;

import morrow.rest.Response;
import morrow.rest.exception.ClientException;
import morrow.rest.request.Request;

class NoRouteException extends ClientException {
    public NoRouteException(Request request) {

    }

    @Override
    public Response response() {
        // TODO: 404
        return new Response();
    }
}
