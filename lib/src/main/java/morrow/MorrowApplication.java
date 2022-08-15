package morrow;

import morrow.config.SingletonStore;
import morrow.config.Validation;
import morrow.web.Server;
import morrow.web.endpoint.EndpointDescriptor;
import morrow.web.endpoint.loader.EndpointLoader;
import morrow.web.endpoint.loader.InvalidConfigurationException;
import morrow.web.request.Request;
import morrow.web.response.Response;
import org.tinylog.Logger;

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
        try {
            return EndpointLoader.loadEndpoints(singletonStore);
        } catch (Exception e) {
            Logger.error("Failed to load endpoints: "+ e.getMessage(), e);
            throw e;
        }
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
