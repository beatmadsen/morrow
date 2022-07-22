package morrow.rest;

import morrow.rest.exception.ClientException;
import morrow.rest.exception.InternalServerException;
import morrow.rest.exception.ServerException;
import morrow.rest.request.Request;

public class Server {
    public Response serve(Request request) {
        try {
            validate(request);
            var c = createController(request);
            c.beforeAction();
            var response = c.action();
            return response;
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
        return Controller.forEndpoint(request.path()).apply(request);
    }


    private void validate(Request request) throws ClientException {

    }

}
