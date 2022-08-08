package morrow.endpoint.loader.config;

import morrow.config.Validation;
import morrow.endpoint.Action;
import morrow.endpoint.loader.EndpointDescriptor;
import morrow.endpoint.loader.InvalidConfigurationException;
import morrow.endpoint.loader.LoaderException;
import morrow.endpoint.loader.matcher.EndpointMatcher;
import morrow.rest.Controller;
import morrow.rest.Method;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConfigMapper {

    private final Validation validation;

    public ConfigMapper(Validation validation) {
        this.validation = validation;
    }

    public List<EndpointDescriptor> map(EndpointConfig endpointConfig) throws InvalidConfigurationException {
        try {
            validate(endpointConfig);
            var tree = ResourceTree.from(endpointConfig);
            return traverseTree(tree);
        } catch (LoaderException r) {
            throw new InvalidConfigurationException(r.getMessage(), r);
        }


    }

    private void validate(EndpointConfig endpointConfig) {
        var violations = validation.validator().validate(endpointConfig);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }

    }

    private List<EndpointDescriptor> traverseTree(ResourceTree tree) {
        return map(tree.traverseTree());
    }

    private List<EndpointDescriptor> map(List<IndexedConfig> configs) {
        return configs.stream().map(c -> {
            var actions = mapActions(c.config().getActions());
            EndpointMatcher m = EndpointMatcher.from(c.index(), actions);
            return new EndpointDescriptor(m, mapController(c.config().getController()), mapMethods(actions), actions);
        }).toList();
    }


//
//    private EndpointDescriptor createDescriptor(ResourceNode root, List<ResourceSegment> routePrefix) {
//        var config = root.config();
//        Class<? extends Controller> controllerClass = mapController(config.getController());
//        var allowedActions = mapActions(config.getActions());
//        return new EndpointDescriptor(RouteMatcher.of(routePrefix, allowedActions), controllerClass, mapMethods(allowedActions), allowedActions);
//    }

    @SuppressWarnings("unchecked")
    private Class<? extends Controller> mapController(String controller) {
        try {
            Class<?> aClass = Class.forName(controller);
            if (!Controller.class.isAssignableFrom(aClass)) {
                throw new ConfigException("Found invalid controller implementation in class '" + controller + "'");
            }
            return (Class<? extends Controller>) aClass;
        } catch (ClassNotFoundException e) {
            throw new ConfigException("Could not find controller class '" + controller + "'", e);
        }
    }

    private Set<Action> mapActions(Set<String> actions) {
        return actions.stream().map(this::mapAction).collect(Collectors.toSet());
    }

    private Action mapAction(String a) {
        return switch (a) {
            case "getById" -> Action.GET_BY_ID;
            case "findMany" -> Action.FIND_MANY;
            case "create" -> Action.CREATE;
            case "updateById" -> Action.UPDATE_BY_ID;
            case "deleteById" -> Action.DELETE_BY_ID;
            default -> throw new ConfigException("Unknown action: '" + a + "'");
        };
    }

    private Set<Method> mapMethods(Set<Action> allowedActions) {
        return allowedActions.stream().flatMap(action -> action.allowedMethods().stream()).collect(Collectors.toSet());
    }

}
