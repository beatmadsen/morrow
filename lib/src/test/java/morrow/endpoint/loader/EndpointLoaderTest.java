package morrow.endpoint.loader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EndpointLoaderTest {

    private EndpointLoader endpointLoader;
//    private DescriptorLoader descriptorLoader;

    private static class TestDescriptorLoader extends DescriptorLoader {

    }

    @BeforeEach
    void setUp() {
        endpointLoader = new EndpointLoader(new TestDescriptorLoader(), new ConfigMapper());
    }

    @Test
    void shouldBeAbleToX() {

        assertEquals(1, 2);
    }
}