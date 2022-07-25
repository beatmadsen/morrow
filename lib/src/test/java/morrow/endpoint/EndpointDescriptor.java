package morrow.endpoint;

import java.util.List;

public class EndpointDescriptor {
    private String path, controller;

    private List<String> actions;
    private List<EndpointDescriptor> subResources;

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

    public List<EndpointDescriptor> getSubResources() {
        return subResources;
    }

    public void setSubResources(List<EndpointDescriptor> subResources) {
        this.subResources = subResources;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }
}
