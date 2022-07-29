package morrow.endpoint.loader;

import morrow.endpoint.loader.config.ConfigLoader;
import morrow.endpoint.loader.config.ConfigMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EndpointLoaderTest {

    private EndpointLoader endpointLoader;
//    private DescriptorLoader descriptorLoader;

    private static class TestConfigLoader extends ConfigLoader {

    }

    @BeforeEach
    void setUp() {
        endpointLoader = new EndpointLoader(new TestConfigLoader(), new ConfigMapper());
    }

    @Test
    void shouldBeAbleToX() {

        assertEquals(1, 2);
    }
}