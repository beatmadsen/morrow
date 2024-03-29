package morrow.web;

import morrow.config.singleton.Lookup;
import morrow.config.validation.Validation;
import morrow.web.endpoint.Router;
import morrow.web.exception.ClientException;
import morrow.web.exception.InternalServerException;
import morrow.web.exception.ServerException;
import morrow.web.request.Request;
import morrow.web.response.Response;

public class Server {

    private final Router router;
    private final Validation validation;

    public Server(Lookup singletonStore) {
        router = singletonStore.get(Router.class);
        validation = singletonStore.get(Validation.class);
    }

    public Response serve(Request request) {
        try {
            validate(request);
            var c = createController(request);
            return c.run();
        } catch (ClientException | ServerException e) {
            e.track();
            return e.response();
        } catch (Exception e) {
            var serverException = new InternalServerException(e);
            serverException.track();
            return serverException.response();
        }

    }

    private Controller createController(Request request) throws ClientException {
        return router.route(request);
    }


    private void validate(Request request) throws ClientException {
        var violations = validation.validator().validate(request);
        if (!violations.isEmpty()) {
            throw new InvalidRequestException();
        }
    }

    private static class InvalidRequestException extends ClientException {
        @Override
        public Response response() {
            return null;
        }
    }
}
