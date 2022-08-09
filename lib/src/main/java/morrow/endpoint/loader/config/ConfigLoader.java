package morrow.endpoint.loader.config;

import org.apache.commons.text.CaseUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.io.InputStream;
import java.util.List;

public class ConfigLoader {

    public List<EndpointConfig> loadEndpointFile() {
        InputStream inputStream = endpointFile();
        return parse(inputStream);
    }

    private static List<EndpointConfig> parse(InputStream inputStream) {
        try {
            var c = new Constructor(Wrapper.class);
            c.setPropertyUtils(new CamelCasePropertyUtils());
            var yaml = new Yaml(c);
            Wrapper w = yaml.load(inputStream);
            return w.getEndpoints();
        } catch (Exception e) {
            throw new ConfigException("Could not parse endpoints.yml", e);
        }
    }

    private InputStream endpointFile() {
        var inputStream = getClass().getClassLoader().getResourceAsStream("endpoints.yml");
        if (inputStream == null) {
            throw new ConfigException("Could not locate endpoints.yml");
        }
        return inputStream;
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


    private static class CamelCasePropertyUtils extends PropertyUtils {
        @Override
        public Property getProperty(Class<?> type, String name) {
            return super.getProperty(type, toCamelCase(name));
        }

        private String toCamelCase(String name) {
            return CaseUtils.toCamelCase(name, false, '-', '_');
        }
    }
}
