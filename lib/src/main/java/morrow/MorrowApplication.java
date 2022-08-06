package morrow;

import morrow.config.SingletonStore;
import morrow.rest.Response;
import morrow.rest.request.Request;

public class MorrowApplication {
    private final SingletonStore singletonStore;


    public MorrowApplication() {
        singletonStore = new SingletonStore();
        loadSingletons();
    }

    private void loadSingletons() {
        singletonStore.put(new MyValidation(singletonStore));
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
