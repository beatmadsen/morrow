package morrow.endpoint;

import morrow.config.SingletonStore;
import morrow.endpoint.matcher.RouteMatcher;
import morrow.path.UncategorisedSegment;
import morrow.rest.Controller;
import morrow.rest.Method;
import morrow.rest.request.Request;

import java.util.List;
import java.util.Set;

public class EndpointDescriptor {
    private final RouteMatcher routeMatcher;
    private final Class<? extends Controller> controllerClass;
    private final Set<Action> allowedActions;
    private final SingletonStore singletonStore;

    public EndpointDescriptor(SingletonStore singletonStore, RouteMatcher routeMatcher, Class<? extends Controller> controllerClass, Set<Action> allowedActions) {
        this.singletonStore = singletonStore;
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
        return new Controller.State(action, singletonStore);
    }

}
