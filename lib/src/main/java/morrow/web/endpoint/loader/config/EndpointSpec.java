package morrow.web.endpoint.loader.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.Set;

public record EndpointSpec(
        @NotBlank
        @Pattern(regexp = "/?[a-zA-Z0-9\\-_]+(/[a-zA-z0-9\\-_]+)*/?")
        String path,
        @NotNull
        String controller,
        @NotEmpty
        Set<String> actions,
        @JsonProperty("sub-resources")
        List<EndpointSpec> subResources

) {

}
