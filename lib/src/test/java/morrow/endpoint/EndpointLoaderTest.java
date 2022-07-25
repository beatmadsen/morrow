package morrow.endpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EndpointLoaderTest {

    private EndpointLoader endpointLoader;

    @BeforeEach
    void setUp() {
        endpointLoader = new EndpointLoader();
    }

    @Test
    void shouldBeAbleToDeserialize() {
        List<EndpointDescriptor> ed = endpointLoader.loadEndpoints();
        assertEquals(1, ed.size());
    }


    @Test
    void shouldFindController() {
        List<EndpointDescriptor> eds = endpointLoader.loadEndpoints();
        assertEquals("com.example.myapp.controller.CarsController", eds.get(0).getController());
    }
}