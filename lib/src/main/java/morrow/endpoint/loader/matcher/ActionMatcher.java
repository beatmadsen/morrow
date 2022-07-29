package morrow.endpoint.loader.matcher;

import morrow.endpoint.PathSegment;
import morrow.endpoint.UncategorisedSegment;
import morrow.rest.Method;

import java.util.List;

public class ActionMatcher implements RouteMatcher {

    private final List<PathSegment> pathSegments;
    private final Method method;

    public ActionMatcher(List<PathSegment> pathSegments, Method method) {
        this.pathSegments = pathSegments;
        this.method = method;
    }

    @Override
    public boolean matches(List<UncategorisedSegment> pathSegments, Method method) {
        return this.method == method && this.pathSegments.equals(pathSegments);
    }
}
