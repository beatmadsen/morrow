package morrow.rest;

import morrow.config.SingletonStore;
import morrow.config.Validation;
import morrow.endpoint.EndpointDescriptor;
import morrow.endpoint.routing.Router;
import morrow.rest.exception.ClientException;
import morrow.rest.exception.InternalServerException;
import morrow.rest.exception.ServerException;
import morrow.rest.request.Request;

import java.util.List;

public class Server {

    private final Router router;
    private final Validation validation;

    public Server(List<EndpointDescriptor> endpointDescriptors, SingletonStore singletonStore) {
        router = new Router(endpointDescriptors);
        validation = singletonStore.get(Validation.class);
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
