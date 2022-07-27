package morrow.endpoint.loader;

import morrow.endpoint.Action;
import morrow.rest.Controller;
import morrow.rest.Method;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigMapper {

    private record PathNode(String path, List<PathNode> children, EndpointConfig config) {

    }

    private static class InvalidConfigurationRuntimeException extends RuntimeException {
        public InvalidConfigurationRuntimeException(String message, Throwable cause) {
            super(message, cause);
        }

        public InvalidConfigurationRuntimeException(String message) {
            super(message);
        }

        public InvalidConfigurationRuntimeException(Throwable cause) {
            super(cause);
        }
    }

    public List<EndpointDescriptor> map(EndpointConfig endpointConfig) throws InvalidConfigurationException {
        try {
            var root = buildTree(endpointConfig);
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
            Class<?> aClass = Class.forName(controller);
            if (!Controller.class.isAssignableFrom(aClass)) {
                throw new InvalidConfigurationRuntimeException("Found invalid controller implementation in class '" + controller + "'");
            }
            return (Class<? extends Controller>) aClass;
        } catch (ClassNotFoundException e) {
            throw new InvalidConfigurationRuntimeException("Could not find controller class '" + controller + "'", e);
        }
    }


    private Pattern mapPattern(String newPrefix) {
        // TODO
        return Pattern.compile(newPrefix);
    }

    private Set<Action> mapActions(List<String> actions) {
        return actions.stream().map(this::mapAction).collect(Collectors.toSet());
    }

    private Action mapAction(String a) {
        return switch (a) {
            case "getById" -> Action.GET_BY_ID;
            case "findMany" -> Action.FIND_MANY;
            case "create" -> Action.CREATE;
            case "updateById" -> Action.UPDATE_BY_ID;
            case "deleteById" -> Action.DELETE_BY_ID;
            default -> throw new InvalidConfigurationRuntimeException("Unknown action: '" + a + "'");
        };
    }

    private Set<Method> mapMethods(Set<Action> allowedActions) {
        return allowedActions.stream().flatMap(action -> action.allowedMethods().stream()).collect(Collectors.toSet());
    }


    private PathNode buildTree(EndpointConfig config) {
        var subResources = config.getSubResources();
        Stream<EndpointConfig> configs = subResources == null ? Stream.of() : subResources.stream();
        var children = configs.map(this::buildTree).toList();
        return new PathNode(config.getPath(), children, config);
    }
}
