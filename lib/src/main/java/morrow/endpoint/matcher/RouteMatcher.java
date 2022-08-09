package morrow.endpoint.matcher;

import morrow.endpoint.Action;
import morrow.path.UncategorisedSegment;
import morrow.rest.Method;
import morrow.rest.request.Request;

import java.util.List;
import java.util.Optional;

public interface RouteMatcher {

    boolean matches(List<UncategorisedSegment> requestSegments, Method method);


    Optional<Action> inferAction(Request request);
}
