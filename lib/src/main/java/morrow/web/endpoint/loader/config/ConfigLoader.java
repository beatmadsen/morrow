package morrow.web.endpoint.loader.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.List;

public class ConfigLoader {

    public List<EndpointConfig> loadEndpointFile() {
        try {
            var mapper = new ObjectMapper(new YAMLFactory());

            var is = getClass().getClassLoader().getResourceAsStream("endpoints.yml");
            return mapper.readValue(is, new TypeReference<>() {});
        } catch (Exception e) {
            throw new ConfigException("Could not parse endpoints.yml", e);
        }
    }


}
