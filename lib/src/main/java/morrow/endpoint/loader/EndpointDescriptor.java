package morrow.endpoint.loader;

import morrow.endpoint.Action;
import morrow.rest.Controller;
import morrow.rest.Method;

import java.util.List;
import java.util.regex.Pattern;

public record EndpointDescriptor(
        Pattern pathPattern,
        Class<? extends Controller> controllerClass,
        List<Method> allowedMethods,
        List<Action> allowedActions
) {
}
