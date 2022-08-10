package morrow.web.endpoint.loader;

import morrow.config.SingletonStore;
import morrow.config.Validation;
import morrow.web.Method;
import morrow.web.endpoint.EndpointDescriptor;
import morrow.web.endpoint.loader.config.ConfigMapper;
import morrow.web.endpoint.loader.config.EndpointConfig;
import morrow.web.path.UncategorisedSegment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ConfigMapperTest {

    private ConfigMapper configMapper;

    @BeforeEach
    void setUp() {
        SingletonStore singletonStore = new SingletonStore();
        singletonStore.put(new Validation(null));
        configMapper = new ConfigMapper(singletonStore);
    }

    @Test
    void shouldMapMainResource() {
        var endpointConfig = new EndpointConfig();
        endpointConfig.setActions(Set.of("getById", "create"));
        endpointConfig.setController("org.other.controller.EggsController");
        endpointConfig.setPath("my-prefix/my_resource");
        List<EndpointDescriptor> eds = configMapper.map(endpointConfig);
        var e = eds.get(0);
        assertTrue(e.isMethodAllowed(Method.GET));
        assertTrue(e.isMethodAllowed(Method.POST));
        var getByIdSegments = List.of(new UncategorisedSegment("my-prefix"), new UncategorisedSegment("my_resource"), new UncategorisedSegment("42"));
        assertTrue(e.matchesRoute(getByIdSegments, Method.GET));
        var createSegments = List.of(new UncategorisedSegment("my-prefix"), new UncategorisedSegment("my_resource"));
        assertTrue(e.matchesRoute(createSegments, Method.POST));
    }

    @Test
    void shouldFailToMapIncompleteResource() {
        var endpointConfig = new EndpointConfig();
        endpointConfig.setActions(Set.of("getById"));
        endpointConfig.setController("org.other.controller.EggsController");
        assertThrows(LoaderException.class, () -> configMapper.map(endpointConfig));
    }

    @Test
    void shouldFailToMapIncorrectResource() {
        var endpointConfig = new EndpointConfig();
        endpointConfig.setActions(Set.of("getById"));
        endpointConfig.setController("org.other.controller.EggsController");
        endpointConfig.setPath("/56$/");

        assertThrows(LoaderException.class, () -> configMapper.map(endpointConfig));
    }

    @Test
    void shouldMapSubResource() {
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
        var e = eds.get(1);

        assertTrue(e.isMethodAllowed(Method.GET));
        assertTrue(e.isMethodAllowed(Method.PUT));
        assertTrue(e.isMethodAllowed(Method.PATCH));

        var updateByIdSegments = List.of(
                new UncategorisedSegment("ns1"),
                new UncategorisedSegment("ns2"),
                new UncategorisedSegment("child"),
                new UncategorisedSegment("456")
        );
        assertTrue(e.matchesRoute(updateByIdSegments, Method.PUT));

        var findManySegments = List.of(
                new UncategorisedSegment("ns1"),
                new UncategorisedSegment("parent"),
                new UncategorisedSegment("16"),
                new UncategorisedSegment("ns2"),
                new UncategorisedSegment("child")
        );
        assertTrue(e.matchesRoute(findManySegments, Method.GET));


    }


}