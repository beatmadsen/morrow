package morrow.endpoint.loader.matcher;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RouteMatcherTest {

    @Test
    void emptyInputsShouldBePointless() {
        assertThrows(MatcherException.class, () -> RouteMatcher.of(List.of(), Set.of()));
    }
}