package morrow.web.endpoint.loader;

import morrow.config.singleton.Lookup;
import morrow.web.Action;
import morrow.web.Controller;
import morrow.web.endpoint.loader.matcher.RouteMatcher;
import morrow.web.path.UncategorisedSegment;
import morrow.web.request.Method;
import morrow.web.request.Request;

import java.util.List;
import java.util.Set;

public class EndpointDescriptor {
    private final RouteMatcher routeMatcher;
    private final Class<? extends Controller> controllerClass;
    private final Set<Action> allowedActions;
    private final Lookup singletonLookup;

    public EndpointDescriptor(Lookup singletonLookup, RouteMatcher routeMatcher, Class<? extends Controller> controllerClass, Set<Action> allowedActions) {
        this.singletonLookup = singletonLookup;
        this.routeMatcher = routeMatcher;
        this.controllerClass = controllerClass;
        this.allowedActions = allowedActions;
    }

    public boolean isMethodAllowed(Method m) {
        return allowedActions.stream().flatMap(a -> a.allowedMethods().stream()).anyMatch(mm -> mm == m);
    }

    public boolean matchesRoute(List<UncategorisedSegment> requestSegments, Method method) {
        return routeMatcher.matches(requestSegments, method);
    }

    public Controller controller(Request request) {
        try {
            var state = state(request);
            return controllerClass.getDeclaredConstructor(Controller.State.class).newInstance(state);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Controller.State state(Request request) {
        var action = routeMatcher.inferAction(request).orElseThrow();
        return new Controller.State(action, singletonLookup, request.headers());
    }

}
