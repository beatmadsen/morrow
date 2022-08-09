package morrow.endpoint.matcher;

import morrow.endpoint.Action;
import morrow.path.PathSegment;
import morrow.path.UncategorisedSegment;
import morrow.rest.Method;
import morrow.rest.request.Request;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class EndpointMatcher implements RouteMatcher {

    public static EndpointMatcher from(List<PathSegment> routePrefix, Set<Action> allowedActions) {
        if (allowedActions.isEmpty()) {
            throw new MatcherException("Need at least one allowed action for resource " + describe(routePrefix));
        }
        var ams = allowedActions.stream()
                .map(a -> ActionMatcher.from(routePrefix, a))
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
    public boolean matches(List<UncategorisedSegment> requestSegments, Method method) {
        return actionMatchers.stream().anyMatch(a -> a.matches(requestSegments, method));
    }

    @Override
    public Optional<Action> inferAction(Request request) {
        return actionMatchers.stream().flatMap(am -> am.inferAction(request).stream()).findAny();
    }
}
