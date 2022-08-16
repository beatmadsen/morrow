package morrow.web.endpoint.loader;

import morrow.config.SingletonStore;
import morrow.config.Validation;
import morrow.web.endpoint.EndpointDescriptor;
import morrow.web.endpoint.loader.config.EndpointSpec;
import morrow.web.endpoint.loader.config.SpecLoader;
import morrow.web.path.UncategorisedSegment;
import morrow.web.request.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SpecLoaderTest {

    private SpecLoader specLoader;

    @BeforeEach
    void setUp() {
        SingletonStore singletonStore = new SingletonStore();
        singletonStore.put(new Validation(null));
        specLoader = new SpecLoader(singletonStore);
    }

    @Test
    void shouldMapMainResource() {
        var endpointConfig = new EndpointSpec("my-prefix/my_resource", "org.other.controller.EggsController", Set.of("getById", "create"), null);

        List<EndpointDescriptor> eds = specLoader.map(endpointConfig);
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
        var endpointConfig = new EndpointSpec(null, "org.other.controller.EggsController", Set.of("getById"), null);
        assertThrows(RuntimeException.class, () -> specLoader.map(endpointConfig));
    }

    @Test
    void shouldFailToMapIncorrectResource() {
        var endpointConfig = new EndpointSpec("/56$/", "org.other.controller.EggsController", Set.of("getById"), null);
        assertThrows(RuntimeException.class, () -> specLoader.map(endpointConfig));
    }

    @Test
    void shouldMapSubResource() {

        var subResource = new EndpointSpec(
                "ns2/child",
                "org.other.controller.YokesController",
                Set.of("updateById", "findMany"),
                null
        );

        var endpointConfig = new EndpointSpec(
                "ns1/parent",
                "org.other.controller.EggsController",
                Set.of("getById", "create"),
                List.of(subResource)
        );

        List<EndpointDescriptor> eds = specLoader.map(endpointConfig);
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