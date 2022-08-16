package morrow.web.endpoint.loader;

import morrow.config.SingletonStore;
import morrow.web.endpoint.EndpointDescriptor;
import morrow.web.endpoint.EndpointException;
import morrow.web.endpoint.loader.file.ConfigFileLoader;
import morrow.web.endpoint.loader.config.SpecLoader;
import morrow.web.endpoint.loader.config.EndpointSpec;

import java.util.List;
import java.util.stream.Stream;

public class EndpointLoader {
    private final ConfigFileLoader configFileLoader;
    private final SpecLoader specLoader;

    private EndpointLoader(SingletonStore singletonStore) {
        configFileLoader = new ConfigFileLoader();
        specLoader = new SpecLoader(singletonStore);
    }

    public static List<EndpointDescriptor> loadEndpoints(SingletonStore singletonStore) throws EndpointException {
        return new EndpointLoader(singletonStore).loadEndpoints();
    }


    public List<EndpointDescriptor> loadEndpoints() throws EndpointException {
        try {
            return configFileLoader.loadEndpointsFile().stream().flatMap(this::streamDescriptors).toList();
        } catch (Exception e) {
            throw new LoaderException("Could not load endpoints from endpoints.yml", e);
        }
    }

    private Stream<EndpointDescriptor> streamDescriptors(EndpointSpec c) {
        return specLoader.map(c).stream();
    }
}
