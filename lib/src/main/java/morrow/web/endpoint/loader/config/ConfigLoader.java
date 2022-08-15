package morrow.web.endpoint.loader.config;

import com.fasterxml.jackson.core.type.TypeReference;
import morrow.yaml.YamlLoader;

import java.util.List;

public class ConfigLoader {
    public List<EndpointConfig> loadEndpointFile() {
        try {
            return new YamlLoader<>(new TypeReference<List<EndpointConfig>>() {
            }).loadResource("endpoints.yml");
        } catch (Exception e) {
            throw new ConfigException("Could not parse endpoints.yml", e);
        }
    }
}
