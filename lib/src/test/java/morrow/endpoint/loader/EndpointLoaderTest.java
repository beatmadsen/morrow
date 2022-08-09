package morrow.endpoint.loader;

import morrow.config.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EndpointLoaderTest {

    private Validation validation;


    @BeforeEach
    void setUp() {
        validation = new Validation(null);
    }

    @Test
    void loadEndpoints() throws InvalidConfigurationException {
        var descriptors = EndpointLoader.loadEndpoints(validation);
        assertEquals(5, descriptors.size());
    }
}