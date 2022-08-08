package morrow.endpoint.loader.matcher;

import morrow.endpoint.Action;
import morrow.endpoint.ParameterSegment;
import morrow.endpoint.PathSegment;
import morrow.endpoint.UncategorisedSegment;
import morrow.rest.Method;

import java.util.LinkedList;
import java.util.List;

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
     * namespace(s)/resource/parameter/namespace(s)/resource/parameter/.../namespace(s)/resource
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
     * namespace(s)/resource/parameter
     */
    private static List<PathSegment> shallowSpecification(List<PathSegment> routePrefix) {
        var result = new LinkedList<PathSegment>();
        result.addFirst(new ParameterSegment());
        var iter = routePrefix.listIterator(routePrefix.size());
        var doneWithResource = false;
        while (iter.hasPrevious()) {
            var item = iter.previous();
            if (doneWithResource) {
                if (item.isNamespace()) {
                    result.addFirst(item);
                } else {
                    break;
                }
            } else {
                if (!item.isResource()) {
                    throw new RuntimeException("Wrong!");
                }
                result.addFirst(item);
                doneWithResource = true;
            }
        }
        return result.stream().toList();
    }

    @Override
    public boolean matches(List<UncategorisedSegment> requestSegments, Method method) {
        return action.allowedMethods().contains(method) && pathIsMatch(requestSegments);
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
