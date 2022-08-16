package morrow.web.endpoint;

import morrow.config.SingletonStore;
import morrow.web.Controller;
import morrow.web.endpoint.loader.EndpointDescriptor;
import morrow.web.endpoint.loader.EndpointLoader;
import morrow.web.exception.ClientException;
import morrow.web.request.Request;
import org.tinylog.Logger;

import java.util.List;

public class Router {

    private final List<EndpointDescriptor> descriptors;

    private Router(List<EndpointDescriptor> descriptors) {
        this.descriptors = descriptors;
    }

    private static boolean matches(EndpointDescriptor descriptor, Request request) {
        return descriptor.isMethodAllowed(request.method()) &&
                descriptor.matchesRoute(request.path().segments(), request.method());
    }

    public static Router load(SingletonStore singletonStore) throws EndpointException {
        return new Router(loadEndpoints(singletonStore));
    }

    private static List<EndpointDescriptor> loadEndpoints(SingletonStore singletonStore) throws EndpointException {
        try {
            return EndpointLoader.loadEndpoints(singletonStore);
        } catch (Exception e) {
            Logger.error("Failed to load endpoints: " + e.getMessage(), e);
            throw e;
        }
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
