package morrow.web.endpoint.routing;

import morrow.web.Controller;
import morrow.web.endpoint.EndpointDescriptor;
import morrow.web.exception.ClientException;
import morrow.web.request.Request;

import java.util.List;

public class Router {

    private final List<EndpointDescriptor> descriptors;

    public Router(List<EndpointDescriptor> descriptors) {
        this.descriptors = descriptors;
    }

    private static boolean matches(EndpointDescriptor descriptor, Request request) {
        return descriptor.isMethodAllowed(request.method()) &&
                descriptor.matchesRoute(request.path().segments(), request.method());
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

}
