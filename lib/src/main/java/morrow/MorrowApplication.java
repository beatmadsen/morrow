package morrow;

import morrow.config.SingletonStore;
import morrow.config.Validation;
import morrow.web.Server;
import morrow.web.endpoint.Router;
import morrow.web.endpoint.EndpointException;
import morrow.web.request.Request;
import morrow.web.response.Response;

public class MorrowApplication {
    private final SingletonStore singletonStore;
    private final Server server;


    public MorrowApplication() throws EndpointException {
        singletonStore = new SingletonStore();
        loadSingletons();

        server = new Server(singletonStore, Router.load(singletonStore));
    }

    private void loadSingletons() {
        singletonStore.put(new Validation(singletonStore));
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
