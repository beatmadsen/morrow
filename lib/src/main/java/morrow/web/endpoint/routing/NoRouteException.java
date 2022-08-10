package morrow.web.endpoint.routing;

import morrow.web.exception.ClientException;
import morrow.web.protocol.CommonMediaType;
import morrow.web.request.Request;
import morrow.web.response.status.CommonStatusCode;
import morrow.web.response.Response;

class NoRouteException extends ClientException {
    public NoRouteException(Request request) {

    }

    @Override
    public Response response() {
        // TODO: create 404 hook for custom response
        return new Response(CommonMediaType.PLAIN_TEXT_UTF8, CommonStatusCode.NOT_FOUND);
    }
}
