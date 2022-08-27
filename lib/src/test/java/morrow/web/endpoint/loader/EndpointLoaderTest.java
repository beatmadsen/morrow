package morrow.web.endpoint.loader;

import morrow.config.singleton.Store;
import morrow.config.validation.Validation;
import morrow.web.endpoint.EndpointException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EndpointLoaderTest {


    private Store singletonStore;

    @BeforeEach
    void setUp() {
        singletonStore = new Store();
        singletonStore.put(new Validation(null));
    }

    @Test
    void loadEndpoints() throws EndpointException {
        var descriptors = EndpointLoader.loadEndpoints(singletonStore);
        assertEquals(5, descriptors.size());
    }
}