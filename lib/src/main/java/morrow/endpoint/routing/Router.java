package morrow.endpoint.routing;

import morrow.endpoint.EndpointDescriptor;
import morrow.rest.Controller;
import morrow.rest.exception.ClientException;
import morrow.rest.request.Request;

import java.util.List;

public class Router {

    private final List<EndpointDescriptor> descriptors;

    public Router(List<EndpointDescriptor> descriptors) {
        this.descriptors = descriptors;
    }

    public Controller route(Request request) throws ClientException {
        var descriptor = findDescriptor(request);

        return instantiateController(request, descriptor);

    }

    private static Controller instantiateController(Request request, EndpointDescriptor descriptor) {
        try {
            return descriptor.controllerClass()
                    .getDeclaredConstructor(Controller.State.class)
                    .newInstance(state(descriptor, request));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private EndpointDescriptor findDescriptor(Request request) throws NoRouteException {
        return descriptors.stream()
                .filter(d -> matches(d, request))
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiple routes match request: " + a + ", " + b);
                })
                .orElseThrow(() -> new NoRouteException(request));
    }

    private static Controller.State state(EndpointDescriptor descriptor, Request request) {
        var action = descriptor.routeMatcher().inferAction(request).orElseThrow();
        return new Controller.State(action);
    }

    private static boolean matches(EndpointDescriptor descriptor, Request request) {
        return descriptor.allowedMethods().contains(request.method()) &&
                descriptor.routeMatcher().matches(request.path().segments(), request.method());
    }

}
