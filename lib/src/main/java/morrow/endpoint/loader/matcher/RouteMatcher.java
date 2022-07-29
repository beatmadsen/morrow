package morrow.endpoint.loader.matcher;

import morrow.endpoint.Action;
import morrow.endpoint.PathSegment;
import morrow.endpoint.ResourceSegment;
import morrow.rest.Method;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface RouteMatcher {

    static RouteMatcher of(List<ResourceSegment> routePrefix, Set<Action> allowedActions) {
        if (allowedActions.isEmpty()) {
            throw new MatcherException("Need at least one allowed action for resource " + describe(routePrefix));
        }

        /*
         TODO:
           we know the prefix and the allowed actions
           we can create a set of action matchers
           and in turn an endpoint matcher
           complication: prefix will be ignored for some actions
        */

        throw new RuntimeException("not done yet");
    }

    static private String describe(List<ResourceSegment> routePrefix) {
        return routePrefix.isEmpty() ?
                "(root)" :
                routePrefix.stream().map(ResourceSegment::toString).collect(Collectors.joining());
    }

    boolean matches(List<PathSegment> pathSegments, Method method);


}
