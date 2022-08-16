package morrow.web.endpoint.loader.file;

import com.fasterxml.jackson.core.type.TypeReference;
import morrow.web.endpoint.loader.spec.EndpointSpec;
import morrow.yaml.YamlLoader;

import java.util.List;

public class ConfigFileLoader {
    public List<EndpointSpec> loadEndpointsFile() {
        try {
            return new YamlLoader<>(new TypeReference<List<EndpointSpec>>() {
            }).loadResource("endpoints.yml");
        } catch (Exception e) {
            throw new ConfigFileException(e);
        }
    }

}
