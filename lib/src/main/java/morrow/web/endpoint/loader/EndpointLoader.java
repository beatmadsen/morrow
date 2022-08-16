package morrow.web.endpoint.loader;

import morrow.config.singleton.SingletonStore;
import morrow.web.endpoint.EndpointException;
import morrow.web.endpoint.loader.file.ConfigFileLoader;
import morrow.web.endpoint.loader.spec.EndpointSpec;
import morrow.web.endpoint.loader.spec.SpecLoader;

import java.util.List;
import java.util.stream.Stream;

public class EndpointLoader {
    private final ConfigFileLoader configFileLoader;
    private final SingletonStore singletonStore;

    private EndpointLoader(SingletonStore singletonStore) {
        this.singletonStore = singletonStore;
        configFileLoader = new ConfigFileLoader();
    }

    public static List<EndpointDescriptor> loadEndpoints(SingletonStore singletonStore) throws EndpointException {
        return new EndpointLoader(singletonStore).loadEndpoints();
    }

    private static Stream<EndpointDescriptor> streamClasses(SpecLoader l) {
        return l.loadClasses().stream();
    }

    public List<EndpointDescriptor> loadEndpoints() throws EndpointException {
        try {
            return configFileLoader.loadEndpointsFile()
                    .stream()
                    .map(this::specLoader)
                    .peek(SpecLoader::validate)
                    .flatMap(EndpointLoader::streamClasses)
                    .toList();
        } catch (Exception e) {
            throw new LoaderException("Could not load endpoints from endpoints.yml", e);
        }
    }

    private SpecLoader specLoader(EndpointSpec spec) {
        return new SpecLoader(singletonStore, spec);
    }

}
