package morrow.web.endpoint.matcher;

import morrow.web.endpoint.Action;
import morrow.web.path.UncategorisedSegment;
import morrow.web.request.Method;
import morrow.web.request.Request;

import java.util.List;
import java.util.Optional;

public interface RouteMatcher {

    boolean matches(List<UncategorisedSegment> requestSegments, Method method);


    Optional<Action> inferAction(Request request);
}
