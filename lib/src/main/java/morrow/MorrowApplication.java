package morrow;

import morrow.config.SingletonStore;
import morrow.config.Validation;
import morrow.endpoint.EndpointDescriptor;
import morrow.endpoint.loader.EndpointLoader;
import morrow.endpoint.loader.InvalidConfigurationException;
import morrow.rest.Response;
import morrow.rest.Server;
import morrow.rest.request.Request;

import java.util.List;

public class MorrowApplication {
    private final SingletonStore singletonStore;
    private final Server server;


    public MorrowApplication() throws InvalidConfigurationException {
        singletonStore = new SingletonStore();
        loadSingletons();
        var endpointDescriptors = loadEndpoints();


        server = new Server(endpointDescriptors, singletonStore);
    }

    private void loadSingletons() {
        singletonStore.put(new Validation(singletonStore));
    }

    private List<EndpointDescriptor> loadEndpoints() throws InvalidConfigurationException {
        return EndpointLoader.loadEndpoints(singletonStore);
    }


    public Response serve(Request request) {

        return server.serve(request);
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
