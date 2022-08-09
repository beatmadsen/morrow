package morrow;

import morrow.config.SingletonStore;
import morrow.config.Validation;
import morrow.endpoint.EndpointDescriptor;
import morrow.endpoint.loader.EndpointLoader;
import morrow.endpoint.loader.InvalidConfigurationException;
import morrow.endpoint.routing.Router;
import morrow.rest.Response;
import morrow.rest.request.Request;

import java.util.List;

public class MorrowApplication {
    private final SingletonStore singletonStore;
    private final Router router;


    public MorrowApplication() throws InvalidConfigurationException {
        singletonStore = new SingletonStore();
        loadSingletons();
        router = new Router(loadEndpoints());
    }

    private void loadSingletons() {
        singletonStore.put(new Validation(singletonStore));
    }

    private List<EndpointDescriptor> loadEndpoints() throws InvalidConfigurationException {
        return EndpointLoader.loadEndpoints(singletonStore.get(Validation.class));
    }

    public Response serve(Request request) {
        return new Response();
    }

    public void onShutdown() {
        try {
            // TODO: shutdown hooks
            singletonStore.close();
        } catch (Exception e) {
            // TODO
            throw new RuntimeException(e);
        }

    }
}
