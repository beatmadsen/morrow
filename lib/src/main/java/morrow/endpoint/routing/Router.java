package morrow.endpoint.routing;

import morrow.config.SingletonStore;
import morrow.endpoint.EndpointDescriptor;
import morrow.rest.Controller;
import morrow.rest.exception.ClientException;
import morrow.rest.request.Request;

import java.util.List;

public class Router {

    private final List<EndpointDescriptor> descriptors;
    private final ControllerFactory controllerFactory;

    public Router(List<EndpointDescriptor> descriptors, SingletonStore singletonStore) {
        this.descriptors = descriptors;
        controllerFactory = new ControllerFactory(singletonStore);
    }

    public Controller route(Request request) throws ClientException {
        var descriptor = findDescriptor(request);
        return controllerFactory.controller(descriptor, request);
    }

    private EndpointDescriptor findDescriptor(Request request) throws NoRouteException {
        return descriptors.stream()
                .filter(d -> matches(d, request))
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiple routes match request: " + a + ", " + b);
                })
                .orElseThrow(() -> new NoRouteException(request));
    }

    private static boolean matches(EndpointDescriptor descriptor, Request request) {
        return descriptor.allowedMethods().contains(request.method()) &&
                descriptor.routeMatcher().matches(request.path().segments(), request.method());
    }

}
