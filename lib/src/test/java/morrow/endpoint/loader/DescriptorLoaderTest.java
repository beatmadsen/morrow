package morrow.endpoint.loader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DescriptorLoaderTest {

    private DescriptorLoader descriptorLoader;

    @BeforeEach
    void setUp() {
        descriptorLoader = new DescriptorLoader();
    }

    @Test
    void shouldBeAbleToDeserialize() {
        List<EndpointConfig> ed = descriptorLoader.loadEndpoints();
        assertEquals(1, ed.size());
    }


    @Test
    void shouldFindController() {
        List<EndpointConfig> eds = descriptorLoader.loadEndpoints();
        assertEquals("com.example.myapp.controller.CarsController", eds.get(0).getController());
    }
}