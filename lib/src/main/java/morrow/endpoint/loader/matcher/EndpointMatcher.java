package morrow.endpoint.loader.matcher;

import morrow.endpoint.PathSegment;
import morrow.rest.Method;

import java.util.List;

public class EndpointMatcher implements RouteMatcher {

    private final List<ActionMatcher> actionMatchers;

    public EndpointMatcher(List<ActionMatcher> actionMatchers) {
        this.actionMatchers = actionMatchers;
    }

    @Override
    public boolean matches(List<PathSegment> pathSegments, Method method) {
        return actionMatchers.stream().anyMatch(a -> a.matches(pathSegments, method));
    }
}
