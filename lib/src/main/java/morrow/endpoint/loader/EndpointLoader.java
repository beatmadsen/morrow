package morrow.endpoint.loader;

import morrow.config.Validation;
import morrow.endpoint.EndpointDescriptor;
import morrow.endpoint.loader.config.ConfigLoader;
import morrow.endpoint.loader.config.ConfigMapper;
import morrow.endpoint.loader.config.EndpointConfig;

import java.util.List;
import java.util.stream.Stream;

public class EndpointLoader {
    private final ConfigLoader configLoader;
    private final ConfigMapper configMapper;

    private EndpointLoader(Validation validation) {
        configLoader = new ConfigLoader();
        configMapper = new ConfigMapper(validation);
    }

    public static List<EndpointDescriptor> loadEndpoints(Validation validation) throws InvalidConfigurationException {
        return new EndpointLoader(validation).loadEndpoints();
    }


    public List<EndpointDescriptor> loadEndpoints() throws InvalidConfigurationException {
        try {
            return configLoader.loadEndpointFile().stream().flatMap(this::streamDescriptors).toList();
        } catch (LoaderException e) {
            throw new InvalidConfigurationException("Could not load endpoints from endpoints.yml", e);
        }
    }

    private Stream<EndpointDescriptor> streamDescriptors(EndpointConfig c) {
        return configMapper.map(c).stream();
    }
}
