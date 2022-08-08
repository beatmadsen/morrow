package morrow.endpoint.loader.matcher;

import morrow.endpoint.Action;
import morrow.endpoint.PathSegment;
import morrow.endpoint.UncategorisedSegment;
import morrow.rest.Method;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EndpointMatcher implements RouteMatcher {

    public static EndpointMatcher from(List<PathSegment> routePrefix, Set<Action> allowedActions) {
        if (allowedActions.isEmpty()) {
            throw new MatcherException("Need at least one allowed action for resource " + describe(routePrefix));
        }
        var ams = allowedActions.stream()
                .flatMap(a -> ActionMatcher.allFrom(routePrefix, a).stream())
                .toList();
        return new EndpointMatcher(ams);
    }

    private static String describe(List<PathSegment> routePrefix) {
        return routePrefix.isEmpty() ?
                "(root)" :
                routePrefix.stream().map(PathSegment::toString).collect(Collectors.joining());
    }

    private final List<ActionMatcher> actionMatchers;

    private EndpointMatcher(List<ActionMatcher> actionMatchers) {
        this.actionMatchers = actionMatchers;
    }

    @Override
    public boolean matches(List<UncategorisedSegment> pathSegments, Method method) {
        return actionMatchers.stream().anyMatch(a -> a.matches(pathSegments, method));
    }
}
