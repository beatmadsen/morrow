package morrow.web.endpoint.loader;

import morrow.config.singleton.SingletonStore;
import morrow.config.validation.Validation;
import morrow.web.endpoint.EndpointException;
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
    void loadEndpoints() throws EndpointException {
        var descriptors = EndpointLoader.loadEndpoints(singletonStore);
        assertEquals(5, descriptors.size());
    }
}