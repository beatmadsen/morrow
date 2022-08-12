package morrow.web.endpoint.loader.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.Set;

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
public record EndpointConfig(
        @NotBlank
        @Pattern(regexp = "/?[a-zA-Z0-9\\-_]+(/[a-zA-z0-9\\-_]+)*/?")
        String path,
        @NotNull
        String controller,
        @NotEmpty
        Set<String> actions,
        @JsonProperty("sub-resources")
        List<EndpointConfig> subResources

) {
//
//
//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    public String getController() {
//        return controller;
//    }
//
//    public void setController(String controller) {
//        this.controller = controller;
//    }
//
//    public List<EndpointConfig> getSubResources() {
//        return subResources;
//    }
//
//    public void setSubResources(List<EndpointConfig> subResources) {
//        this.subResources = subResources;
//    }
//
//    public Set<String> getActions() {
//        return actions;
//    }
//
//    public void setActions(Set<String> actions) {
//        this.actions = actions;
//    }
}
