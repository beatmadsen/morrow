package morrow.endpoint.loader;

import morrow.config.Validation;
import morrow.endpoint.loader.config.ConfigLoader;
import morrow.endpoint.loader.config.ConfigMapper;

import java.util.List;

public class EndpointLoader {
    private final ConfigLoader configLoader;
    private final ConfigMapper configMapper;

    public EndpointLoader(Validation validation) {
        configLoader = new ConfigLoader();
        configMapper = new ConfigMapper(validation);
    }


    public List<EndpointDescriptor> loadEndpoints() throws InvalidConfigurationException {
        try {
            return configLoader.loadEndpoints().stream().flatMap(c -> configMapper.map(c).stream()).toList();
        } catch (LoaderException e) {
            throw new InvalidConfigurationException("Could not load endpoints from endpoints.yml", e);
        }
    }
}
