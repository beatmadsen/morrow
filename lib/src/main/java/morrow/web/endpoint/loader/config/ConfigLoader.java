package morrow.web.endpoint.loader.config;

import morrow.yaml.YamlLoader;

import java.util.List;

public class ConfigLoader {

    public List<EndpointConfig> loadEndpointFile() {
        try {
            return YamlLoader.yielding(Wrapper.class).loadResource("endpoints.yml").getEndpoints();
        } catch (Exception e) {
            throw new ConfigException("Could not parse endpoints.yml", e);
        }
    }

    public static class Wrapper {
        private List<EndpointConfig> endpoints;

        public List<EndpointConfig> getEndpoints() {
            return endpoints;
        }

        public void setEndpoints(List<EndpointConfig> endpoints) {
            this.endpoints = endpoints;
        }
    }


}
