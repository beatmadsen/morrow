package morrow.endpoint.loader.matcher;

import morrow.endpoint.UncategorisedSegment;
import morrow.rest.Method;

import java.util.List;

public interface RouteMatcher {

    boolean matches(List<UncategorisedSegment> requestSegments, Method method);


}
