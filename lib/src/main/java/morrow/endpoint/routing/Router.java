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
        return descriptor.controller(request);
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
        return descriptor.isMethodAllowed(request.method()) &&
                descriptor.matchesRoute(request.path().segments(), request.method());
    }

}
