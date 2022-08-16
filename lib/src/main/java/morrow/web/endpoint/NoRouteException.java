package morrow.web.endpoint;

import morrow.web.exception.ClientException;
import morrow.web.protocol.mime.CommonMediaType;
import morrow.web.protocol.body.Body;
import morrow.web.request.Request;
import morrow.web.response.Response;
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
