package morrow.endpoint.loader;

import morrow.config.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EndpointLoaderTest {

    private EndpointLoader endpointLoader;


    @BeforeEach
    void setUp() {
        endpointLoader = new EndpointLoader(new Validation(null));
    }

    @Test
    void loadEndpoints() throws InvalidConfigurationException {
        var descriptors = endpointLoader.loadEndpoints();
        assertEquals(5, descriptors.size());
    }
}