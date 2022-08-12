package morrow.web.endpoint.loader.config;

import morrow.config.SingletonStore;
import morrow.config.Validation;
import morrow.web.Controller;
import morrow.web.endpoint.Action;
import morrow.web.endpoint.EndpointDescriptor;
import morrow.web.endpoint.matcher.EndpointMatcher;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConfigMapper {

    private final SingletonStore singletonStore;

    public ConfigMapper(SingletonStore singletonStore) {
        this.singletonStore = singletonStore;
    }

    public List<EndpointDescriptor> map(EndpointConfig endpointConfig) {
        validate(endpointConfig);
        var tree = ResourceTree.from(endpointConfig);
        return traverseTree(tree);


    }

    private void validate(EndpointConfig endpointConfig) {
        var validator = singletonStore.get(Validation.class).validator();
        var violations = validator.validate(endpointConfig);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }

    }

    private List<EndpointDescriptor> traverseTree(ResourceTree tree) {
        return map(tree.traverseTree());
    }

    private List<EndpointDescriptor> map(List<IndexedConfig> configs) {
        return configs.stream().map(c -> {
            var actions = mapActions(c.config().actions());
            EndpointMatcher m = EndpointMatcher.from(c.index(), actions);
            return new EndpointDescriptor(singletonStore, m, mapController(c.config().controller()), actions);
        }).toList();
    }

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

}
