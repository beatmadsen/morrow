package morrow.endpoint.loader;

import morrow.endpoint.Action;
import morrow.endpoint.loader.matcher.RouteMatcher;
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
