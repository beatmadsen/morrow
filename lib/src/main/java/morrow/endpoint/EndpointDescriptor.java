package morrow.endpoint;

import morrow.endpoint.matcher.RouteMatcher;
import morrow.rest.Controller;
import morrow.rest.Method;

import java.util.Set;

public record EndpointDescriptor(
        RouteMatcher routeMatcher,
        Class<? extends Controller> controllerClass,
        Set<Method> allowedMethods,
        Set<Action> allowedActions
) {
}
