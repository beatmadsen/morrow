package morrow.web.endpoint.loader.file;

import morrow.web.endpoint.loader.config.EndpointSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigFileLoaderTest {

    private ConfigFileLoader configFileLoader;

    @BeforeEach
    void setUp() {
        configFileLoader = new ConfigFileLoader();
    }

    @Test
    void shouldBeAbleToDeserialize() {
        List<EndpointSpec> ed = configFileLoader.loadEndpointsFile();
        assertEquals(2, ed.size());
    }


    @Test
    void shouldFindController() {
        List<EndpointSpec> eds = configFileLoader.loadEndpointsFile();
        assertEquals("com.example.myapp.controller.CarsController", eds.get(0).controller());
    }
}