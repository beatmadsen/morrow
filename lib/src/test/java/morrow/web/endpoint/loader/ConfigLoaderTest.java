package morrow.web.endpoint.loader;

import morrow.web.endpoint.loader.config.ConfigLoader;
import morrow.web.endpoint.loader.config.EndpointConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigLoaderTest {

    private ConfigLoader configLoader;

    @BeforeEach
    void setUp() {
        configLoader = new ConfigLoader();
    }

    @Test
    void shouldBeAbleToDeserialize() {
        List<EndpointConfig> ed = configLoader.loadEndpointFile();
        assertEquals(2, ed.size());
    }


    @Test
    void shouldFindController() {
        List<EndpointConfig> eds = configLoader.loadEndpointFile();
        assertEquals("com.example.myapp.controller.CarsController", eds.get(0).controller());
    }
}