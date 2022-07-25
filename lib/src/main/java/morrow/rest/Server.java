package morrow.rest;

import morrow.rest.endpoint.Endpoint;
import morrow.rest.exception.ClientException;
import morrow.rest.exception.InternalServerException;
import morrow.rest.exception.ServerException;
import morrow.rest.request.Request;

public class Server {

    public Server() {
        // TODO: load validators and endpoints
    }

    public Response serve(Request request) {
        try {
            validate(request);
            var c = createController(request);
            c.beforeAction();
            return c.action();
        } catch (ClientException | ServerException e) {
            e.track();
            return e.response();
        } catch (Exception e) {
            var serverException = new InternalServerException(e);
            serverException.track();
            return serverException.response();
        }

    }

    private Controller createController(Request request) {
        return Endpoint.lookup(request.path()).controller(request);
    }


    private void validate(Request request) throws ClientException {

    }

}
