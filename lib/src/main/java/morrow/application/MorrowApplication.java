package morrow.application;

import morrow.config.singleton.SingletonStore;
import morrow.config.validation.Validation;
import morrow.web.Server;
import morrow.web.endpoint.Router;
import morrow.web.request.Request;
import morrow.web.response.Response;
import morrow.web.view.ControllerRenderPlugin;

public class MorrowApplication {
    private final SingletonStore singletonStore;
    private final Server server;


    public MorrowApplication() throws ApplicationException {
        singletonStore = new SingletonStore();
        loadSingletons();
        server = new Server(singletonStore);
    }

    private void loadSingletons() throws ApplicationException {
        try {
            singletonStore.put(new Validation(singletonStore));
            singletonStore.put(ControllerRenderPlugin.load(singletonStore));
            singletonStore.put(Router.load(singletonStore));
        } catch (Exception e) {
            throw new SingletonLoadException(e);
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
