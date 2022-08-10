package morrow.web;

import morrow.config.SingletonStore;
import morrow.config.Validation;
import morrow.web.endpoint.EndpointDescriptor;
import morrow.web.endpoint.routing.Router;
import morrow.web.exception.ClientException;
import morrow.web.exception.InternalServerException;
import morrow.web.exception.ServerException;
import morrow.web.request.Request;
import morrow.web.response.Response;

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
