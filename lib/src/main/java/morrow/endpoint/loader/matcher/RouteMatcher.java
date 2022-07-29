package morrow.endpoint.loader.matcher;

import morrow.endpoint.Action;
import morrow.endpoint.PathSegment;
import morrow.endpoint.ResourceSegment;
import morrow.rest.Method;

import java.util.List;
import java.util.Set;

public interface RouteMatcher {

    static RouteMatcher of(List<ResourceSegment> routePrefix, Set<Action> allowedActions) {
        /*
         TODO:
           we know the prefix and the allowed actions
           we can create a set of action matchers
           and in turn an endpoint matcher
           complication: prefix will be ignored for some actions
        */

        throw new RuntimeException("not done yet");
    }

    boolean matches(List<PathSegment> pathSegments, Method method);


}
