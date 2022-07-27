package morrow.endpoint.loader;

import morrow.endpoint.Action;
import morrow.rest.Controller;
import morrow.rest.Method;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ConfigMapper {

    private record PathNode(String path, List<PathNode> children, EndpointConfig config) {

    }

    public List<EndpointDescriptor> map(EndpointConfig endpointConfig) throws InvalidConfigurationException {
        try {
            PathNode root = buildTree(endpointConfig);

            return traverseTree(root);
        } catch (InvalidConfigurationRuntimeException r) {
            throw new InvalidConfigurationException(r.getMessage(), r);
        }


    }

    private List<EndpointDescriptor> traverseTree(PathNode root) {
        return traverseTree(root, "").toList();
    }

    private Stream<EndpointDescriptor> traverseTree(PathNode root, String pathPrefix) {
        var newPrefix = pathPrefix + root.path();
        var descriptor = createDescriptor(root, newPrefix);

        var ds = Stream.of(descriptor);
        var cs = root.children().stream().flatMap(c -> traverseTree(c, newPrefix));

        return Stream.concat(ds, cs);
    }

    private EndpointDescriptor createDescriptor(PathNode root, String newPrefix) {
        var config = root.config();
        Class<? extends Controller> controllerClass = mapController(config.getController());
        var allowedActions = mapActions(config.getActions());
        return new EndpointDescriptor(mapPattern(newPrefix), controllerClass, mapMethods(allowedActions), allowedActions);
    }

    @SuppressWarnings("unchecked")
    private Class<? extends Controller> mapController(String controller) {
        try {
            return (Class<? extends Controller>) Class.forName(controller);
        } catch (Exception e) {
            throw new InvalidConfigurationRuntimeException(e);
        }
    }

    private static class InvalidConfigurationRuntimeException extends RuntimeException {
        public InvalidConfigurationRuntimeException(String message, Throwable cause) {
            super(message, cause);
        }

        public InvalidConfigurationRuntimeException(Throwable cause) {
            super(cause);
        }
    }

    private Pattern mapPattern(String newPrefix) {
        // TODO
        return Pattern.compile(newPrefix);
    }

    private List<Action> mapActions(List<String> actions) {
        return actions.stream().map(this::mapAction).toList();
    }

    private Action mapAction(String a) {
        try {
            return Action.valueOf(a.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidConfigurationRuntimeException("Unknown action: '" + a + "'", e);
        }
    }

    private List<Method> mapMethods(List<Action> allowedActions) {
        return allowedActions.stream().map(Action::method).toList();
    }


    private PathNode buildTree(EndpointConfig config) {
        var subResources = config.getSubResources();
        Stream<EndpointConfig> configs = subResources == null ? Stream.of() : subResources.stream();
        var children = configs.map(this::buildTree).toList();
        return new PathNode(config.getPath(), children, config);
    }
}
