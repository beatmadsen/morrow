package morrow.web.endpoint.loader.config;

import morrow.config.ConfigurationValidationException;
import morrow.config.LoadHelper;
import morrow.config.SingletonStore;
import morrow.config.Validation;
import morrow.web.Controller;
import morrow.web.endpoint.Action;
import morrow.web.endpoint.EndpointDescriptor;
import morrow.web.endpoint.matcher.EndpointMatcher;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SpecLoader {

    private final SingletonStore singletonStore;

    public SpecLoader(SingletonStore singletonStore) {
        this.singletonStore = singletonStore;
    }

    public List<EndpointDescriptor> map(EndpointSpec endpointSpec) {
        validate(endpointSpec);
        var tree = ResourceTree.from(endpointSpec);
        return traverseTree(tree);


    }

    private void validate(EndpointSpec endpointSpec) {
        try {
            singletonStore.get(Validation.class).validateConfig(endpointSpec);
        } catch (ConfigurationValidationException e) {
            throw new InvalidEndpointSpecException(endpointSpec, e);
        }


    }

    private List<EndpointDescriptor> traverseTree(ResourceTree tree) {
        return map(tree.traverseTree());
    }

    private List<EndpointDescriptor> map(List<IndexedSpec> configs) {
        return configs.stream().map(c -> {
            var actions = mapActions(c.config().actions());
            EndpointMatcher m = EndpointMatcher.from(c.index(), actions);
            return new EndpointDescriptor(singletonStore, m, loadClass(c.config().controller()), actions);
        }).toList();
    }

    private Class<? extends Controller> loadClass(String controller) {
        try {
            return new LoadHelper(Controller.class).loadClass(controller);
        } catch (Exception e) {
            throw new ClassLoadException(controller, e);
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
            default -> throw new UnknownActionException(a);
        };
    }

}
