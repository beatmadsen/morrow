package morrow.endpoint.loader.matcher;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RouteMatcherTest {

    @Test
    void of() {
        RouteMatcher.of(List.of(), Set.of());
    }
}