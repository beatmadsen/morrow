package morrow.endpoint.routing;

import morrow.config.SingletonStore;
import morrow.endpoint.EndpointDescriptor;
import morrow.rest.Controller;
import morrow.rest.request.Request;

public class ControllerFactory {
    private final SingletonStore singletonStore;

    public ControllerFactory(SingletonStore singletonStore) {
        this.singletonStore = singletonStore;
    }

    public Controller controller(EndpointDescriptor descriptor, Request request) {
        try {
            return descriptor.controllerClass()
                    .getDeclaredConstructor(Controller.State.class)
                    .newInstance(state(descriptor, request));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Controller.State state(EndpointDescriptor descriptor, Request request) {
        var action = descriptor.routeMatcher().inferAction(request).orElseThrow();
        return new Controller.State(action, singletonStore);
    }

}
