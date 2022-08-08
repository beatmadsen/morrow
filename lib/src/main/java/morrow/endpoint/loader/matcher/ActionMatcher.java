package morrow.endpoint.loader.matcher;

import morrow.endpoint.Action;
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

    public static List<ActionMatcher> allFrom(List<PathSegment> routePrefix, Action a) {

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
        return a.allowedMethods().stream().map(m -> new ActionMatcher(List.of(), m)).toList();
    }

    @Override
    public boolean matches(List<UncategorisedSegment> pathSegments, Method method) {
        return this.method == method && pathIsMatch(pathSegments);
    }

    private boolean pathIsMatch(List<UncategorisedSegment> pathSegments) {
        // TODO
        return true;
    }
}
