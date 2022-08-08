package morrow.endpoint.loader.matcher;

import morrow.endpoint.Action;
import morrow.endpoint.PathSegment;
import morrow.endpoint.ResourceSegment;
import morrow.endpoint.UncategorisedSegment;
import morrow.rest.Method;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EndpointMatcherTest {

    @Test
    void emptyInputsShouldBePointless() {
        assertThrows(MatcherException.class, () -> EndpointMatcher.from(List.of(), Set.of()));
    }

    @Test
    void shouldMatchCreateAction() {
        List<PathSegment> routePrefix = List.of(new ResourceSegment("cars"));
        var matcher = EndpointMatcher.from(routePrefix, Set.of(Action.CREATE, Action.FIND_MANY));
        var result = matcher.matches(List.of(new UncategorisedSegment("cars")), Method.POST);
        assertTrue(result);
    }
}