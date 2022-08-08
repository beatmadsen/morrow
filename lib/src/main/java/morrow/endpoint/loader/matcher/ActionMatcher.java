package morrow.endpoint.loader.matcher;

import morrow.endpoint.Action;
import morrow.endpoint.PathSegment;
import morrow.endpoint.UncategorisedSegment;
import morrow.rest.Method;

import java.util.List;

public class ActionMatcher implements RouteMatcher {

    private final List<PathSegment> specification;
    private final Action action;

    public ActionMatcher(List<PathSegment> specification, Action action) {
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
        return List.of();
    }

    @Override
    public boolean matches(List<UncategorisedSegment> pathSegments, Method method) {
        return action.allowedMethods().contains(method) && pathIsMatch(pathSegments);
    }

    private boolean pathIsMatch(List<UncategorisedSegment> pathSegments) {
        // TODO
        return true;
    }
}
