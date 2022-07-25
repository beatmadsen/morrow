package morrow.endpoint;

import org.apache.commons.text.CaseUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.util.List;

public class EndpointLoader {

    public static class Wrapper {
        private List<EndpointDescriptor> endpoints;

        public List<EndpointDescriptor> getEndpoints() {
            return endpoints;
        }

        public void setEndpoints(List<EndpointDescriptor> endpoints) {
            this.endpoints = endpoints;
        }
    }

    public List<EndpointDescriptor> loadEndpoints() {
        var c = new Constructor(Wrapper.class);
        c.setPropertyUtils(new CamelCasePropertyUtils());
        var yaml = new Yaml(c);
        var inputStream = getClass().getClassLoader().getResourceAsStream("endpoints.yml");
        Wrapper w = yaml.load(inputStream);
        return w.getEndpoints();
    }

    private static class CamelCasePropertyUtils extends PropertyUtils {
        @Override
        public Property getProperty(Class<? extends Object> type, String name) {
            return super.getProperty(type, toCamelCase(name));
        }

        private String toCamelCase(String name) {
            return CaseUtils.toCamelCase(name, false, '-', '_');
        }
    }
}
