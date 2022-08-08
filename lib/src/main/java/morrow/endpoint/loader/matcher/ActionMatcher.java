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

        /*
         TODO:
           we know the prefix and the allowed actions
           we can create a set of action matchers
           and in turn an endpoint matcher
           complication 1:
             resource part of prefix will be ignored for some actions (shallow resource routes)
           complication 2:
             infer path parameter location
        */
        return new ActionMatcher(specificationFrom(routePrefix, a), a);
    }

    private static List<PathSegment> specificationFrom(List<PathSegment> routePrefix, Action a) {

        return a.hasShallowPath() ? shallowSpecification(routePrefix, a) : deepSpecification(routePrefix);
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

    private static List<PathSegment> shallowSpecification(List<PathSegment> routePrefix, Action a) {
        // TODO: only take final namespaces + resource, add parameter (idea: action always takes param if it's shallow)
        return null;
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
