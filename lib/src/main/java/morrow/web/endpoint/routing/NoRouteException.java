package morrow.web.endpoint.routing;

import morrow.web.exception.ClientException;
import morrow.web.protocol.CommonMediaType;
import morrow.web.request.Request;
import morrow.web.response.Response;
import morrow.web.response.body.Body;
import morrow.web.response.status.CommonStatusCode;

class NoRouteException extends ClientException {
    public NoRouteException(Request request) {

    }

    @Override
    public Response response() {
        // TODO: create 404 hook for custom response
        return new Response(CommonMediaType.JSON_UTF8, CommonStatusCode.NOT_FOUND, Body.of("{\"status\": \"not found\"}"));
    }
}
