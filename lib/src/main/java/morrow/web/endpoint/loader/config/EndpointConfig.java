package morrow.web.endpoint.loader.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.Set;

public class EndpointConfig {
    @NotBlank
    @Pattern(regexp = "/?[a-zA-Z0-9\\-_]+(/[a-zA-z0-9\\-_]+)*/?")
    private String path;
    @NotNull
    private String controller;

    @NotEmpty
    private Set<String> actions;
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

    public Set<String> getActions() {
        return actions;
    }

    public void setActions(Set<String> actions) {
        this.actions = actions;
    }
}
