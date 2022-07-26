package morrow.endpoint.loader;

import java.util.List;

public class EndpointConfig {
    private String path, controller;

    private List<String> actions;
    private List<EndpointConfig> subResources;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public List<EndpointConfig> getSubResources() {
        return subResources;
    }

    public void setSubResources(List<EndpointConfig> subResources) {
        this.subResources = subResources;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }
}
