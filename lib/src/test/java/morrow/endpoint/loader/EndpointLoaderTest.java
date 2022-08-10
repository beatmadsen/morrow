package morrow.endpoint.loader;

import morrow.config.SingletonStore;
import morrow.config.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EndpointLoaderTest {


    private SingletonStore singletonStore;

    @BeforeEach
    void setUp() {
        singletonStore = new SingletonStore();
        singletonStore.put(new Validation(null));
    }

    @Test
    void loadEndpoints() throws InvalidConfigurationException {
        var descriptors = EndpointLoader.loadEndpoints(singletonStore);
        assertEquals(5, descriptors.size());
    }
}