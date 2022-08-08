package morrow.endpoint.loader;

import morrow.config.Validation;
import morrow.endpoint.Action;
import morrow.endpoint.UncategorisedSegment;
import morrow.endpoint.loader.config.ConfigMapper;
import morrow.endpoint.loader.config.EndpointConfig;
import morrow.rest.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.other.controller.YokesController;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ConfigMapperTest {

    private ConfigMapper configMapper;

    @BeforeEach
    void setUp() {
        configMapper = new ConfigMapper(new Validation(null));
    }

    @Test
    void shouldMapMainResource() throws InvalidConfigurationException {
        var endpointConfig = new EndpointConfig();
        endpointConfig.setActions(Set.of("getById", "create"));
        endpointConfig.setController("org.other.controller.EggsController");
        endpointConfig.setPath("my-prefix/my_resource");
        List<EndpointDescriptor> eds = configMapper.map(endpointConfig);
        var e = eds.get(0);
        assertEquals(Set.of(Action.GET_BY_ID, Action.CREATE), e.allowedActions());
        assertEquals(Set.of(Method.GET, Method.POST), e.allowedMethods());

        var getByIdSegments = List.of(new UncategorisedSegment("my-prefix"), new UncategorisedSegment("my_resource"), new UncategorisedSegment("42"));
        assertTrue(e.routeMatcher().matches(getByIdSegments, Method.GET));
        var createSegments = List.of(new UncategorisedSegment("my-prefix"), new UncategorisedSegment("my_resource"));
        assertTrue(e.routeMatcher().matches(createSegments, Method.POST));
    }

    @Test
    void shouldFailToMapIncompleteResource() {
        var endpointConfig = new EndpointConfig();
        endpointConfig.setActions(Set.of("getById"));
        endpointConfig.setController("org.other.controller.EggsController");
        assertThrows(InvalidConfigurationException.class, () -> {
            configMapper.map(endpointConfig);
        });
    }

    @Test
    void shouldFailToMapIncorrectResource() {
        var endpointConfig = new EndpointConfig();
        endpointConfig.setActions(Set.of("getById"));
        endpointConfig.setController("org.other.controller.EggsController");
        endpointConfig.setPath("/56/");

        assertThrows(InvalidConfigurationException.class, () -> {
            configMapper.map(endpointConfig);
        });
    }

    @Test
    void shouldMapSubResource() throws InvalidConfigurationException {
        var subResource = new EndpointConfig();
        subResource.setPath("ns2/child");
        subResource.setActions(Set.of("updateById", "findMany"));
        subResource.setController("org.other.controller.YokesController");

        var endpointConfig = new EndpointConfig();
        endpointConfig.setActions(Set.of("getById", "create"));
        endpointConfig.setController("org.other.controller.EggsController");
        endpointConfig.setPath("ns1/parent");
        endpointConfig.setSubResources(List.of(subResource));

        List<EndpointDescriptor> eds = configMapper.map(endpointConfig);
        assertEquals(2, eds.size());
        var e = eds.stream()
                .filter(ed -> ed.controllerClass() == YokesController.class)
                .findFirst()
                .orElseThrow();

        assertEquals(Set.of(Action.UPDATE_BY_ID, Action.FIND_MANY), e.allowedActions());
        assertEquals(Set.of(Method.PUT, Method.PATCH, Method.GET), e.allowedMethods());

        // TODO: namespace semantics for sub-resources
        var updateByIdSegments = List.of(
                new UncategorisedSegment("ns2"),
                new UncategorisedSegment("child"),
                new UncategorisedSegment("456")
        );
        assertTrue(e.routeMatcher().matches(updateByIdSegments, Method.PUT));

        var findManySegments = List.of(
                new UncategorisedSegment("ns1"),
                new UncategorisedSegment("parent"),
                new UncategorisedSegment("16"),
                new UncategorisedSegment("ns2"),
                new UncategorisedSegment("child")
        );
        assertTrue(e.routeMatcher().matches(findManySegments, Method.GET));



    }


}