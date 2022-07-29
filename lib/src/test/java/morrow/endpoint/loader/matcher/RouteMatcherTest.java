package morrow.endpoint.loader.matcher;

import morrow.endpoint.ResourceSegment;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RouteMatcherTest {

    @Test
    void emptyInputsShouldBePointless() {
        assertThrows(MatcherException.class, () -> RouteMatcher.of(List.of(), Set.of()));
    }

    @Test
    void test2() {
        var routePrefix = List.of(new ResourceSegment("cars"));
        var matcher = RouteMatcher.of(routePrefix, Set.of());
    }
}