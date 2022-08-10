package morrow.web.endpoint.matcher;

import morrow.web.Method;
import morrow.web.endpoint.Action;
import morrow.web.path.ParameterSegment;
import morrow.web.path.PathSegment;
import morrow.web.path.UncategorisedSegment;
import morrow.web.request.Request;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ActionMatcher implements RouteMatcher {

    private final List<PathSegment> specification;
    private final Action action;

    private ActionMatcher(List<PathSegment> specification, Action action) {
        this.specification = specification;
        this.action = action;
    }

    public static ActionMatcher from(List<PathSegment> routePrefix, Action a) {
        var specification = specificationFrom(routePrefix, a);
        return new ActionMatcher(specification, a);
    }

    private static List<PathSegment> specificationFrom(List<PathSegment> routePrefix, Action a) {

        return a.hasShallowPath() ? shallowSpecification(routePrefix) : deepSpecification(routePrefix);
    }

    /**
     * <code>lvl1_namespace(s)/lvl1_resource/parameter/lvl2_namespace(s)/lvl2_resource/parameter/.../lvlN_namespace(s)/lvlN_resource</code>
     * <p>
     * Example: <br>
     * <code>the_garage/cars/42/parts/small_parts/doors/3/handles</code>
     */
    private static List<PathSegment> deepSpecification(List<PathSegment> routePrefix) {
        var result = new LinkedList<PathSegment>();
        for (PathSegment pathSegment : routePrefix) {
            result.add(pathSegment);
            if (pathSegment.isResource()) {
                result.add(new ParameterSegment());
            }
        }
        if (result.size() < 2) {
            throw new RuntimeException("Wrong!");
        }
        result.removeLast();
        return result.stream().toList();
    }

    /**
     * <code>lvl1_namespace(s)/lvl2_namespace(s)/.../lvlN_namespace(s)/lvlN_resource/parameter</code>
     * <p>
     * Example: <br>
     * <code>the_garage/parts/small_parts/handles/1</code>
     */
    private static List<PathSegment> shallowSpecification(List<PathSegment> routePrefix) {
        var namespaces = routePrefix.stream().filter(PathSegment::isNamespace);
        var resourceAndParam = Stream.of(routePrefix.get(routePrefix.size() - 1), new ParameterSegment());
        return Stream.concat(namespaces, resourceAndParam).toList();
    }

    @Override
    public boolean matches(List<UncategorisedSegment> requestSegments, Method method) {
        return action.allowedMethods().contains(method) && pathIsMatch(requestSegments);
    }

    @Override
    public Optional<Action> inferAction(Request request) {
        return matches(request.path().segments(), request.method()) ? Optional.of(action) : Optional.empty();
    }

    private boolean pathIsMatch(List<UncategorisedSegment> requestSegments) {
        if (requestSegments.size() != specification.size()) {
            return false;
        }
        var reqIt = requestSegments.iterator();
        var specIt = specification.iterator();

        while (reqIt.hasNext()) {
            var reqSegment = reqIt.next();
            var specSegment = specIt.next();
            if (!specSegment.matches(reqSegment)) {
                return false;
            }
        }
        return true;
    }
}
