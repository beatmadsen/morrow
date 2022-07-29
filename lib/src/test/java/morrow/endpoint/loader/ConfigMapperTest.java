package morrow.endpoint.loader;

import morrow.endpoint.Action;
import morrow.endpoint.loader.config.ConfigMapper;
import morrow.endpoint.loader.config.EndpointConfig;
import morrow.rest.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class ConfigMapperTest {

    private ConfigMapper configMapper;

    @BeforeEach
    void setUp() {
        configMapper = new ConfigMapper();
    }

    @Test
    void shouldMapMainResource() throws InvalidConfigurationException {
        var endpointConfig = new EndpointConfig();
        endpointConfig.setActions(List.of("getById"));
        endpointConfig.setController("org.other.controller.EggsController");
        List<EndpointDescriptor> eds = configMapper.map(endpointConfig);
        var e = eds.get(0);
        assertIterableEquals(List.of(Action.GET_BY_ID), e.allowedActions());
        assertIterableEquals(List.of(Method.GET), e.allowedMethods());
    }

    @Test
    void shouldFailToMapInvalidResource() {

    }

    @Test
    void shouldMapSubResource() {
        /*
         TODO:
          descriptor should have pairs of (Method + path regex)
          that indicate to which incoming requests the endpoint applies
        */

    }


}